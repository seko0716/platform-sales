package net.sergey.kosov.statistic.companions.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.api.java.Optional
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.State
import org.apache.spark.streaming.StateSpec
import org.apache.spark.streaming.api.java.JavaInputDStream
import org.apache.spark.streaming.api.java.JavaStreamingContext
import scala.Serializable
import scala.Tuple2
import java.util.*

class ProductRecommendation constructor(var kafkaSink: Broadcast<KafkaSink>,
                                        var mappingFunc: Broadcast<Function3<Any, Optional<List<Tuple2<Any, Int>>>, State<List<Tuple2<Any, Int>>>, Tuple2<Any, List<Tuple2<Any, Int>>>>>) : Serializable {

    fun calculate(stream: JavaInputDStream<ConsumerRecord<String, String>>, jssc: JavaStreamingContext) {
        val dstream = stream
                .mapToPair { parseEvent(it.value()) }
                .reduceByKey { list1, list2 -> list1.union(list2).distinct().sorted() }
                .filter { it._2().size > 1 }
                .flatMap { createPairs(it) }
                .mapToPair { Tuple2(it, 1) }
                .reduceByKey { a, b -> Integer.sum(a, b) }
                .flatMapToPair { listOf(Tuple2(it._1._1, listOf(Tuple2(it._1._2, it._2))), Tuple2(it._1._2, listOf(Tuple2(it._1._1, it._2)))).iterator() }
                .reduceByKey { list1, list2 -> list1.union(list2).toList() }
                .mapWithState(StateSpec.function(mappingFunc.value).initialState(getInitialRDD(jssc)))

        dstream.foreachRDD { rdd ->
            rdd.foreachPartition { partitionOfRecords ->
                partitionOfRecords.forEach { message ->
                    kafkaSink.value.send(message)
                }
            }
        }

        // Start the computation
        jssc.start()
        jssc.awaitTermination()
    }

    private fun parseEvent(it: String): Tuple2<String, List<String>> {
        val split = it.split(" ")
        return Tuple2(split[0], listOf(split[1]))
    }

    private fun createPairs(it: Tuple2<String, List<String>>?): MutableIterator<Tuple2<*, *>> {
        val result = ArrayList<Tuple2<*, *>>()
        for (i in 0 until it!!._2().size - 1) {
            val key = it._2()[i]
            for (j in i + 1 until it._2().size) {
                result.add(Tuple2(key, it._2()[j]))
            }
        }
        return result.iterator()
    }

    private fun getInitialRDD(jssc: JavaStreamingContext): JavaPairRDD<Any, List<Tuple2<Any, Int>>> {
        return jssc.sparkContext().parallelizePairs(listOf())
    }
}

package net.sergey.kosov.prediction.jobs


import net.sergey.kosov.prediction.configuration.SparkProperties
import net.sergey.kosov.prediction.domains.Product
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.PipelineModel
import org.apache.spark.ml.PipelineStage
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.StructType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.io.File


@Service
class ChurnPrediction @Autowired constructor(private val classifier: PipelineStage,
                                             private val sparkProperties: SparkProperties,
                                             private val environment: Environment,
                                             private val schema: StructType,
                                             private val sqlContext: SQLContext) {

    private lateinit var model: PipelineModel
    private var auroc: Double = -1.0

    fun predict(product: Product): Double {
        val dataset = sqlContext.createDataFrame(listOf(product), Product::class.java)
        return model.transform(dataset).limit(1).select("prediction").javaRDD().map { it[0] }.collect()[0] as Double
    }

    fun train(refit: Boolean = false) {
        if (refit || !File(getModelStorage()).exists()) {
            fit()
        } else {
            model = PipelineModel.read().load("churn.all/saved")
        }
    }

    private fun fit() {
        val dataset = sqlContext.read()
                .format(CSV)
                .option("delimiter", ",")
                .option("header", true)
                .schema(schema)
                .load("churn.all/tmp.csv")

        dataset.show()

        val split = dataset.randomSplit(doubleArrayOf(sparkProperties.train, sparkProperties.test))
        val train = split[0]
        val test = split[1]

        val labelIndexer = StringIndexer().setInputCol("churned").setOutputCol("label")
        val stringIndexer = StringIndexer().setInputCol("intl_plan").setOutputCol("intl_plan_indexed")
        val reducedNumericCols = arrayOf("account_length", "number_vmail_messages", "total_day_calls",
                "total_day_charge", "total_eve_calls", "total_eve_charge",
                "total_night_calls", "total_intl_calls", "total_intl_charge")


        val assembler = VectorAssembler()
                .setInputCols(arrayOf("intl_plan_indexed") + reducedNumericCols)
                .setOutputCol("features")

        val pipeline = Pipeline().setStages(arrayOf(stringIndexer, labelIndexer, assembler, classifier))

        model = pipeline.fit(train)
        model.write().overwrite().save(getModelStorage())

        test.show()

        val predictions = model.transform(test)
        predictions.show()
        val evaluator = BinaryClassificationEvaluator()
        auroc = evaluator.evaluate(predictions)
//    val auroc = evaluator.evaluate(predictions, {evaluator.metricName: "areaUnderROC"})
    }

    private fun getModelStorage() = "${sparkProperties.modelStorage}/${getClassifierType()}"

    private fun getClassifierType() = environment.activeProfiles

    companion object {
        const val CSV = "csv"
    }
}
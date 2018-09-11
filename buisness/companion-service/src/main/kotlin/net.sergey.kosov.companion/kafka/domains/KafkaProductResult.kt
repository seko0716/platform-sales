package net.sergey.kosov.companion.kafka.domains


import net.sergey.kosov.companion.domains.Product
import scala.Serializable

data class KafkaProductResult(var product: Product, var companions: List<ProductCount>) : Serializable
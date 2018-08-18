package net.sergey.kosov.statistic.companions.kafka.domains

import net.sergey.kosov.statistic.domains.Product
import scala.Serializable

data class KafkaProductResult(var product: Product, var companions: List<ProductCount>) : Serializable
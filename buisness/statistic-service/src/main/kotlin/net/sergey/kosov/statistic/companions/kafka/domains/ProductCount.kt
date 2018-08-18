package net.sergey.kosov.statistic.companions.kafka.domains

import net.sergey.kosov.statistic.domains.Product

data class ProductCount(var product: Product, var count: Int)
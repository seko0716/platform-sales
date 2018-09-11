package net.sergey.kosov.companion.kafka.domains

import net.sergey.kosov.companion.domains.Product


data class ProductCount(var product: Product, var count: Int)
package net.sergey.kosov.statistic.domains

import java.time.LocalDateTime

data class Product(val id: String,
                   val title: String,
                   val date: LocalDateTime)

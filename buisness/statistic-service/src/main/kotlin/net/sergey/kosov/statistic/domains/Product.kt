package net.sergey.kosov.statistic.domains

import com.fasterxml.jackson.annotation.JsonFormat
import net.sergey.kosov.common.annotations.NoArgs
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@NoArgs
data class Product(var id: String,
                   var title: String,
                   var description: String,
                   var account: String,
                   var price: BigDecimal,
                   var category: Category,
                   var characteristic: List<Characteristic> = category.characteristics,
                   var tags: List<String> = listOf(),
                   var productInfo: String = "",
                   var enabled: Boolean = false,
                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                   var date: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)) : Serializable

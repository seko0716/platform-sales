package net.sergey.kosov.prediction.domains

import com.fasterxml.jackson.annotation.JsonFormat
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.utils.DateTimeUtils.Companion.dateTimeUtc
import java.io.Serializable
import java.math.BigDecimal
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
                   var date: String = dateTimeUtc().format(DateTimeFormatter.ISO_DATE_TIME)) : Serializable

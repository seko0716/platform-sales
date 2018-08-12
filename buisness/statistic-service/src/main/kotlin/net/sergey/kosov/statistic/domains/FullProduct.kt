package net.sergey.kosov.statistic.domains

import java.math.BigDecimal
import javax.validation.constraints.Size

data class FullProduct(var title: String,
                       @Size(min = 10, max = 500)
                       var description: String,
                       var price: BigDecimal,
                       var category: Category,
                       var characteristic: List<Characteristic> = category.characteristics,
                       var tags: List<String> = listOf(),
                       var productInfo: String = "",
                       var enabled: Boolean = false)
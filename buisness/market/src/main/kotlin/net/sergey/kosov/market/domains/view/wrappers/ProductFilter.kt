package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.common.annotations.NoArgs
import java.math.BigDecimal

@NoArgs
data class ProductFilter(val priceRight: BigDecimal, val priceLeft: BigDecimal, val title: String)

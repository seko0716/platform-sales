package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class ProductFilter(val priceRight: Int, val priceLeft: Int, val title: String) {

}

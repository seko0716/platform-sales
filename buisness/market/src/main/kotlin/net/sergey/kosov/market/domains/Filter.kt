package net.sergey.kosov.market.domains

import net.sergey.kosov.market.configuration.NoArgs

@NoArgs
data class Filter(val priceRight: Int, val priceLeft: Int, val title: String) {

}

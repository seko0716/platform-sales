package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class OrderViewCreation(var productId: String, var count: Int = 0)
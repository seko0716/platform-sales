package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.common.annotations.NoArgs
import java.math.BigDecimal

@NoArgs
data class ProductViewCreation(var title: String, var description: String, var price: BigDecimal, var categoryId: String, val productInfo: String)
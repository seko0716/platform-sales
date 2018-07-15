package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.market.domains.entity.Characteristic

data class CategoryViewCreation(var title: String,
                                var description: String = "",
                                var characteristics: List<Characteristic> = listOf(),
                                var parentId: String? = null,
                                var accountId: String)

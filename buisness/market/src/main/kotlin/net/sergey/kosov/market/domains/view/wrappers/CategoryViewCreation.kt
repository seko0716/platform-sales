package net.sergey.kosov.market.domains.view.wrappers

data class CategoryViewCreation(var title: String,
                                var description: String = "",
                                var parentId: String? = null)

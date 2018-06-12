package net.sergey.kosov.market.domains.entity

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class Characteristic(var name: String, var value: String? = null)

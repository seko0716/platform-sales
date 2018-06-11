package net.sergey.kosov.market.domains

import net.sergey.kosov.market.configuration.NoArgs

@NoArgs
data class Characteristic(var name: String, var value: String? = null)
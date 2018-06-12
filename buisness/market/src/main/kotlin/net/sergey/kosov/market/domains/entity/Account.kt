package net.sergey.kosov.market.domains.entity

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class Account(var marketName: String,
                   var description: String)

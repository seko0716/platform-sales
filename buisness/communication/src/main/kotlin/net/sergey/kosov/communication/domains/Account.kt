package net.sergey.kosov.communication.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class Account(var marketName: String,
                   var description: String = "",
                   var users: List<String> = listOf())

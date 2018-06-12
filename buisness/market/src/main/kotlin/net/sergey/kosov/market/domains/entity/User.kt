package net.sergey.kosov.market.domains.entity

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class User(var name: String,
                var family: String,
                var fullName: String = "$name $family")
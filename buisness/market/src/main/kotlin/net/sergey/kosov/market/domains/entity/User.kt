package net.sergey.kosov.market.domains.entity

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class User(var firstName: String,
                var lastName: String,
                var email: String,
                var fullName: String = "$firstName $lastName")
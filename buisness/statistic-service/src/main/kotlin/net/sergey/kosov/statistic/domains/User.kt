package net.sergey.kosov.statistic.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class User(var firstName: String,
                var lastName: String,
                var email: String)
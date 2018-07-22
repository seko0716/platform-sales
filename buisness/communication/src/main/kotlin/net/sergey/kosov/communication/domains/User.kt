package net.sergey.kosov.communication.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class User(var fullName: String,
                var firstName: String,
                var lastName: String,
                var email: String) {
}


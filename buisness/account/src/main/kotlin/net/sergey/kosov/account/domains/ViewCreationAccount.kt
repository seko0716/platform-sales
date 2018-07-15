package net.sergey.kosov.account.domains

import net.sergey.kosov.common.annotations.NoArgs
import java.time.LocalDate

@NoArgs
data class ViewCreationAccount(var fullName: String,
                               var firstName: String,
                               var lastName: String,
                               var email: String = "",
                               var password: String = "",
                               var birthDay: LocalDate,
                               var country: String,
                               var gender: Gender,
                               var marketName: String = "")

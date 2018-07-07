package net.sergey.kosov.account.domains

import net.sergey.kosov.common.annotations.NoArgs
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDate

@NoArgs
data class ViewCreationAccount(var fullName: String,
                               var firstName: String,
                               var lastName: String,
                               @Indexed(unique = true, name = "email_index")
                               var email: String,
                               var password: String,
                               var birthDay: LocalDate,
                               var country: String,
                               var gender: Gender,
                               var marketName: String)

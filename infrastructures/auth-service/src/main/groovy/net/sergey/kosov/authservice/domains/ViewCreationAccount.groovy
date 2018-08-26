package net.sergey.kosov.authservice.domains

import groovy.transform.builder.Builder
import net.sergey.kosov.common.utils.DateTimeUtils

import java.time.LocalDate

@Builder
class ViewCreationAccount {
    String fullName
    String firstName
    String lastName
    String email = ""
    String password = ""
    LocalDate birthDay = DateTimeUtils.Companion.dateUtc()
    String country = "N/A"
    String gender = "UNKNOWN"
    String marketName
}

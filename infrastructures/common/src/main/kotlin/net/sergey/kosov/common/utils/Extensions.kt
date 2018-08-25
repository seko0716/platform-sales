package net.sergey.kosov.common.utils

import java.io.Serializable
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime


class DateTimeUtils : Serializable {
    companion object {
        fun dateTimeUtc() = LocalDateTime.now(Clock.systemUTC())!!
        fun dateUtc() = LocalDate.now(Clock.systemUTC())!!
    }
}
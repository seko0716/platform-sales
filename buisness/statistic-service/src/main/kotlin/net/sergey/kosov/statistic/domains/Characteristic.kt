package net.sergey.kosov.statistic.domains

import java.io.Serializable


data class Characteristic(var name: String, var value: String? = null) : Serializable

package net.sergey.kosov.prediction.domains

import net.sergey.kosov.common.annotations.NoArgs
import java.io.Serializable


@NoArgs
data class Characteristic(var name: String, var value: String? = null) : Serializable

package net.sergey.kosov.companion.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class KafkaData(val sessionId: String, val product: Product)
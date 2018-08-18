package net.sergey.kosov.statistic.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class KafkaData(val sessionId: String, val product: Product)
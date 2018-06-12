package net.sergey.kosov.market.domains.view.wrappers

import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.market.domains.entity.Status

@NoArgs
data class OrderFilter(var customerName: String, var status: Status?)
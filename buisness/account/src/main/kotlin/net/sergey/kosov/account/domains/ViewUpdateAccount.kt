package net.sergey.kosov.account.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class ViewUpdateAccount(var marketName: String,
                             var marketDescription: String,
                             var marketId: String,
                             var marketImages: List<String>)

package net.sergey.kosov.communication.domains

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class ViewMessageCreation(var mess: String,
                               var type: MessageType,
                               var to: String = "",
                               var protocol: String = "internal",
                               var entityId: String)
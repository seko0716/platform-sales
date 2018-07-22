package net.sergey.kosov.market.infrastracture

annotation class NotifyEvent(val eventTo: EventTo) {
}

enum class EventTo{
    CUSTOMER, SELLER
}

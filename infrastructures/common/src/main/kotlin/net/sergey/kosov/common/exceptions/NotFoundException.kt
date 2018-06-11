package net.sergey.kosov.common.exceptions

class NotFoundException(override val message: String?) : RuntimeException(message) {
}
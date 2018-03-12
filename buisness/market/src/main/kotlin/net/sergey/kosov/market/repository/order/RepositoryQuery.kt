package net.sergey.kosov.market.repository.order

import org.springframework.data.mongodb.core.query.Query

interface RepositoryQuery<T, Id> {
    fun findByTitle(query: Query, clazz: Class<T>): List<T>
}
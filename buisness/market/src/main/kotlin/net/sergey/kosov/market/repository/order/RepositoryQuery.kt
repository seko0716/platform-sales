package net.sergey.kosov.market.repository.order

import org.springframework.data.mongodb.core.query.Query

interface RepositoryQuery<T> {
    fun findByTitle(query: Query): List<T>
}
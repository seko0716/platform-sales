package net.sergey.kosov.common.repositories

import org.springframework.data.mongodb.core.query.Query

interface RepositoryQuery<T> {
    fun findByQuery(query: Query): List<T>
}
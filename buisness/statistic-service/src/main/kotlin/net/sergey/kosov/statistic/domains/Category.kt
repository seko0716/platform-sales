package net.sergey.kosov.statistic.domains

import net.sergey.kosov.common.annotations.NoArgs
import java.io.Serializable

@NoArgs
data class Category(var title: String,
                    var description: String = "",
                    var parent: Category? = null,
                    var characteristics: List<Characteristic> = ArrayList()) : Iterable<Category>, Serializable {

    override fun iterator(): Iterator<Category> {
        return CategoryIterator(this)

    }

    class CategoryIterator(var category: Category?) : Iterator<Category> {
        override fun hasNext(): Boolean {
            return category != null
        }

        override fun next(): Category {
            if (!hasNext()) throw NoSuchElementException()
            val returned = category!! // checked in previous line. hasNext()
            category = returned.parent
            return returned
        }

    }
}

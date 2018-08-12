package net.sergey.kosov.market.domains.entity

import org.junit.Assert
import org.junit.Test

internal class CategoryTest {

    @Test
    operator fun iterator() {
        val category1 = Category(title = "root1", characteristics = listOf(Characteristic("1")))
        val category2 = Category(title = "root2", parent = category1, characteristics = listOf(Characteristic("2"), Characteristic("22")))
        val category3 = Category(title = "root3", parent = category2, characteristics = listOf(Characteristic("3")))
        val category4 = Category(title = "root4", parent = category3, characteristics = listOf(Characteristic("4")))
        val flatMap = category4.flatMap { c -> c.characteristics.map { it.name } }
        Assert.assertEquals(listOf("4", "3", "2", "22", "1"), flatMap)
    }
}
package com.liwei.androidproject

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val oldList = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16)
        val newList = ArrayList<Int>()

        for (index in oldList.indices) {

            val mul = index / 10
            var start = (index - mul * 10) / 2
            val indexJ = start  + 5 + 10 * mul
            if (indexJ + 5 < oldList.size) {
                if (index % 2 == 0) {
                    newList.add(oldList[start + 10 * mul])
                } else {
                    newList.add(oldList[start  + 5 + 10 * mul])
                }
            } else {
                if (index + 5 < oldList.size) {
                    if (index % 2 == 0) {
                        newList.add(oldList[start + 10 * mul])
                    } else {
                        newList.add(oldList[start + 10 * mul + 5])
                    }
                } else {
                    newList.add(oldList[start + 10 * mul])
                }
            }
        }
        println("================")
        println(newList)
        println("================")
    }


}
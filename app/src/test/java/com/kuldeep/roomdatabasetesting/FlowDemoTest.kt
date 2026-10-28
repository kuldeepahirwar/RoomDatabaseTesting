package com.kuldeep.roomdatabasetesting

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class FlowDemoTest {

    @Test
    fun getflowTest()=runTest{
        val sut = FlowDemo()
        val flow = sut.getFlow().toList()
        Assert.assertEquals(listOf(1,2),flow)

    }

}
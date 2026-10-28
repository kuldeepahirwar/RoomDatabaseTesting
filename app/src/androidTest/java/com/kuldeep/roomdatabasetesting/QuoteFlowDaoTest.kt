package com.kuldeep.roomdatabasetesting

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteFlowDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var quoteDatabase: QuoteDatabase
    private lateinit var quoteDao: QuoteDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Room.inMemoryDatabaseBuilder returns a Builder<QuoteDatabase>
        // .build() returns an instance of QuoteDatabase
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            context,
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()

        quoteDao = quoteDatabase.quoteDao()
    }

    @Test
    fun insertQuote_expectedStringQuote() = runBlocking {
        val quote = Quote(1, "Be yourself; everyone else is already taken.", "Oscar Wilde")
        val quote2 = Quote(2, "So many books, so little time.", "Frank Zappa")
        quoteDao.insertQuote(quote)
        quoteDao.insertQuote(quote2)

        val result = quoteDao.getQuotes().test{
            val quoteList = awaitItem()
            Assert.assertEquals(2, quoteList.size)
            cancel()
        }
    }

    @After
    fun tearDown() {
        quoteDatabase.close()
    }
}
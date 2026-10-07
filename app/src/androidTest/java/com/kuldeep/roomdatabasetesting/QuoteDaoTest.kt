package com.kuldeep.roomdatabasetesting

// FIX: Changed from androidx.room3 to androidx.room
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

class QuoteDaoTest {

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
        quoteDao.insertQuote(quote)

        // Ensure you have the LiveData extension function defined in your project
        val result = quoteDao.getQuotes().getOrAwaitValue()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Be yourself; everyone else is already taken.", result[0].text)
    }

    @Test
    fun deleteQuote_expectedNoResult() = runBlocking {
        val quote = Quote(1, "Be yourself; everyone else is already taken.", "Oscar Wilde")
        quoteDao.insertQuote(quote)

        quoteDao.delete()

        val result = quoteDao.getQuotes().getOrAwaitValue()
        Assert.assertTrue(result.isEmpty())
    }

    @After
    fun tearDown() {
        quoteDatabase.close()
    }
}
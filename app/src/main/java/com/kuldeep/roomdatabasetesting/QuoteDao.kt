package com.kuldeep.roomdatabasetesting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kuldeep.roomdatabasetesting.Quote

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertQuote(quote: Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Query("DELETE FROM Quote")
    suspend fun delete()

    @Query("SELECT * FROM Quote")
    fun getQuotes(): LiveData<List<Quote>>

    @Query("SELECT * FROM Quote WHERE id = :id")
    suspend fun getQuoteById(id: Int): Quote
}
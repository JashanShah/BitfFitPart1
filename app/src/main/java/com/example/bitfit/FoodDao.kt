package com.example.bitfit

import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert


@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Insert
    fun insertAll(food: List<FoodEntity>)

    @Insert
    fun insert(food: FoodEntity)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}
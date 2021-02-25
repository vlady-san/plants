package ru.startandroid.plantapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface CategoryDao {

    @Query("SELECT * from category")
    fun getAll(): List<Category>

    @Insert(onConflict = REPLACE)
    fun insert(category: Category)

    @Query("DELETE from category")
    fun deleteAll()
}
package ru.startandroid.plantapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface PlantDao {

    @Query("SELECT * from plant")
    fun getAll(): List<Plant>

    @Query("SELECT * from plant WHERE is_my = 1")
    fun getMyPlant(): List<Plant>

    @Query("SELECT * from plant WHERE id=:id")
    fun getPlantById(id : Long): Plant

    @Query("SELECT * from plant WHERE family = :family")
    fun getAllByFamily(family: String): List<Plant>

    @Insert(onConflict = REPLACE)
    fun insert(plant: Plant)

    @Query("DELETE from plant")
    fun deleteAll()

    @Query("DELETE from plant WHERE id = :id")
    fun deleteById(id: Long)
}
package ru.startandroid.plantapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    val title : String,
    val image_little : Int,
    val image_middle : Int,
    val image_large : Int,
    val height : String,
    //влага
    val moisture : Double,
    val family : String,
    val size : String,
    var is_my : Boolean
)
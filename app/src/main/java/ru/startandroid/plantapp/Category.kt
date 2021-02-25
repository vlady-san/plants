package ru.startandroid.plantapp
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    val title : String,
    val image : Int
)
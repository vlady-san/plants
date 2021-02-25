package ru.startandroid.plantapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Category::class, Plant::class), version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao
    abstract fun getPlantDao(): PlantDao

    companion object {
        private var INSTANCE: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase? {
            if (INSTANCE == null) {
                synchronized(MyDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MyDataBase::class.java, "plants.db")
                        .allowMainThreadQueries()
                        .build()

                    initData(INSTANCE!!)

                }
            }
            return INSTANCE
        }

        private fun initData(instance: MyDataBase) {
            var categoryDao = instance.getCategoryDao()
            var plantDao = instance.getPlantDao()

            categoryDao.deleteAll()
            plantDao.deleteAll()

            categoryDao.insert(Category(title = "Цветущие", image = R.drawable.blooming_middle))
            categoryDao.insert(Category(title = "Пальмы", image = R.drawable.palm_middle))
            categoryDao.insert(Category(title = "Орхидеи", image = R.drawable.orchid_middle))
            categoryDao.insert(Category(title = "Папоротники", image = R.drawable.fern_middle))

            plantDao.insert(Plant(title = "Драцена", height = "100-120 см", moisture = 0.4, family = "Цветущие",
                size = "Крупномер", image_little = R.drawable.dracaena_little, image_middle = R.drawable.draceana_middle,
                image_large = R.drawable.draceana_large, is_my = false))

            plantDao.insert(Plant(title = "Дипладемия", height = "60-70 см", moisture = 0.6, family = "Орхидеи",
                size = "Крупномер", image_little = R.drawable.diplomas_little, image_middle = R.drawable.diplomas_middle,
                image_large = R.drawable.diplomas_large, is_my = false))

            plantDao.insert(Plant(title = "Цикламен", height = "60-110 см", moisture = 0.1, family = "Пальмы",
                size = "Крупномер", image_little = R.drawable.cyclamen_little, image_middle = R.drawable.cyclamen_middle,
                image_large = R.drawable.cyclamen_large, is_my = false))

            plantDao.insert(Plant(title = "Каланхоэ", height = "100-120 см", moisture = 0.4, family = "Пальмы",
                size = "Крупномер", image_little = R.drawable.diplomas_little, image_middle = R.drawable.diplomas_middle,
                image_large = R.drawable.diplomas_large, is_my = false))

            plantDao.insert(Plant(title = "Нефролепис", height = "60-110 см", moisture = 0.1, family = "Пальмы",
                size = "Крупномер", image_little = R.drawable.nerpholepis_little, image_middle = R.drawable.nerpholepis_middle,
                image_large = R.drawable.nephrolepis_large, is_my = false))

        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
package ru.startandroid.plantapp

interface OnChooseCategoryItemClickListener {
    fun onItemClick(title: String)
}

interface OnLikeOrDeleteItemClickListener {
    fun onItemClick(id: Long)
}

interface OnGetInfoItemClickListener {
    fun getInfoPlant(id: Long)
}
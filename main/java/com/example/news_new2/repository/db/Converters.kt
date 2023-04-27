package com.example.news_new2.repository.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.news_new2.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source : Source) : String? {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name , name)
    }
}
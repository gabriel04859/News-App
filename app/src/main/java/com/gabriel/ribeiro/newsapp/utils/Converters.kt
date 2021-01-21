package com.gabriel.ribeiro.newsapp.utils

import androidx.room.TypeConverter
import com.gabriel.ribeiro.newsapp.models.responses.Source


class Converters {

    @TypeConverter
    fun fromSourceToString(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toStringFromSource(name : String) : Source{
        return Source(name, name)

    }
}
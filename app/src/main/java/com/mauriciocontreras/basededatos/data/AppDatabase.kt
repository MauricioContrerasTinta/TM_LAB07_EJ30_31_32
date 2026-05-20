package com.mauriciocontreras.basededatos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Articulo::class, Producto::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articuloDao(): ArticuloDao
    abstract fun productoDao(): ProductoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "administracion.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}
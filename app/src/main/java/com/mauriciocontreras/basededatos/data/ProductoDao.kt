package com.mauriciocontreras.basededatos.data

import androidx.room.*

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto): Long

    @Update
    suspend fun actualizar(producto: Producto): Int

    @Delete
    suspend fun eliminar(producto: Producto): Int

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Producto?

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    suspend fun listarTodosLosProductos(): List<Producto>
}
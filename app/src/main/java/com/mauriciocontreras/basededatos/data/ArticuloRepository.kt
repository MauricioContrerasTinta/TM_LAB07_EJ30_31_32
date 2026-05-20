package com.mauriciocontreras.basededatos.data

import kotlinx.coroutines.flow.Flow
class ArticuloRepository(private val articuloDao: ArticuloDao) {

    val todosLosArticulos: Flow<List<Articulo>> = articuloDao.listarTodos()

    suspend fun insertar(articulo: Articulo): Long {
        return articuloDao.insertar(articulo)
    }

    suspend fun actualizar(articulo: Articulo): Int {
        return articuloDao.actualizar(articulo)
    }

    suspend fun eliminarPorCodigo(codigo: Int): Int {
        return articuloDao.eliminarPorCodigo(codigo)
    }

    suspend fun buscarPorCodigo(codigo: Int): Articulo? {
        return articuloDao.buscarPorCodigo(codigo)
    }
}
package com.mauriciocontreras.basededatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mauriciocontreras.basededatos.data.AppDatabase
import com.mauriciocontreras.basededatos.data.Articulo
import com.mauriciocontreras.basededatos.data.ArticuloRepository
import com.mauriciocontreras.basededatos.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: ArticuloRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding =
            ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val dao = AppDatabase.getInstance(this).articuloDao()
        repository = ArticuloRepository(dao)

        binding.btnRegistrar.setOnClickListener {
            registrar()
        }

        binding.btnBuscar.setOnClickListener {
            buscar()
        }

        binding.btnModificar.setOnClickListener {
            modificar()
        }

        binding.btnEliminar.setOnClickListener {
            eliminar()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repository.todosLosArticulos.collect { listaArticulos ->
                    println("Cambio en BD detectado. Total artículos: ${listaArticulos.size}")
                }
            }
        }
    }

    private fun toast(
        mensaje:String
    ){
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun limpiarCampos(){
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
    }

    private fun registrar(){

        val codigo =
            binding.txtCodigo.text.toString()

        val descripcion =
            binding.txtDescripcion.text.toString()

        val precio =
            binding.txtPrecio.text.toString()

        if(
            codigo.isEmpty()
            || descripcion.isEmpty()
            || precio.isEmpty()
        ){

            toast("Complete todos los campos")

            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            descripcion,
            precio.toDouble()
        )

        lifecycleScope.launch {

            try {
                val id = repository.insertar(articulo)

                limpiarCampos()

                if(id > 0){
                    toast("Registrado correctamente")
                }

            }catch (e:Exception){
                toast("Ya existe el código")
            }
        }
    }

    private fun buscar(){

        val codigo =
            binding.txtCodigo.text.toString()

        if(codigo.isEmpty()){

            toast("Ingrese código")

            return
        }

        lifecycleScope.launch {

            val articulo =
                repository.buscarPorCodigo(
                    codigo.toInt()
                )

            if(articulo != null){

                binding.txtDescripcion.setText(
                    articulo.descripcion
                )

                binding.txtPrecio.setText(
                    articulo.precio.toString()
                )

            }else{
                toast("No existe")
            }
        }
    }

    private fun eliminar(){

        val codigo =
            binding.txtCodigo.text.toString()

        if(codigo.isEmpty()){

            toast("Ingrese código")

            return
        }

        lifecycleScope.launch {

            val filas =
                repository.eliminarPorCodigo(
                    codigo.toInt()
                )

            limpiarCampos()

            if(filas == 1){

                toast("Eliminado correctamente")

            }else{

                toast("No existe")
            }
        }
    }

    private fun modificar(){

        val codigo =
            binding.txtCodigo.text.toString()

        val descripcion =
            binding.txtDescripcion.text.toString()

        val precio =
            binding.txtPrecio.text.toString()

        if(
            codigo.isEmpty()
            || descripcion.isEmpty()
            || precio.isEmpty()
        ){

            toast("Complete todos los campos")

            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            descripcion,
            precio.toDouble()
        )

        lifecycleScope.launch {

            val filas =
                repository.actualizar(articulo)

            if(filas == 1){

                toast("Modificado correctamente")

            }else{

                toast("No existe")
            }
        }
    }
}
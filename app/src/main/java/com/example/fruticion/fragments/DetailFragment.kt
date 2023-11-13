package com.example.fruticion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.FragmentDetailBinding
import com.example.fruticion.model.Fruit
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!//Esto es de Roberto

    private lateinit var db: FruticionDatabase

    private val args: DetailFragmentArgs by navArgs()//Esto tiene que ser val porque si no, peta

    //Se crean los elementos de la pantalla (sin valores o con aquellos valores que no corran de nuestra cuenta)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Se obtiene la instancia de la BD
        db = FruticionDatabase.getInstance(requireActivity().applicationContext)!!

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //El codigo de este metodo podria estar en onCreateView() pero es tecnicamente mas correcto si esta en onViewCreated()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fruitId = args.fruitId
        setUpUI(fruitId)
    }

    private fun setUpUI(fruitId: Long) {

        var fruit: Fruit?

        //llamada la a bd
        lifecycleScope.launch {
            fruit = db.fruitDao().getFruitById(fruitId)

            with(binding) {//Recordar que with es para no poner binding delante de todas las lineas que tiene with dentro
                textDetailName.text = fruit?.name

                //Familia, genero y orden
                valueDetailFamily.text = fruit?.family
                valueDetailOrder.text = fruit?.order
                valueDetailGenus.text = fruit?.genus
                //Informacion nutricional
                valueDetailCalories.text = fruit?.calories.toString()
                valueDetailFat.text = fruit?.fat.toString()
                valueDetailSugar.text = fruit?.sugar.toString()
                valueDetailCarbo.text = fruit?.carbohydrates.toString()
                valueDetailProtein.text = fruit?.protein.toString()
            }

        }
    }

    //Este metodo es SOLO para evitar posibles fugas de memoria.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
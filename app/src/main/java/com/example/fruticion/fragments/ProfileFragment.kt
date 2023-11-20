package com.example.fruticion.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fruticion.activity.LoginActivity
import com.example.fruticion.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FruticionDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Se obtiene la instancia de la BD
        db = FruticionDatabase.getInstance(requireContext())!!

        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            //se recupera el nombre y la contraseña del usuario desde la BD por primera vez
            lifecycleScope.launch {
                val user = db.userDao().getUserById(currentUserId)
                valueProfileName.text = user.username
                valueProfilePassword.text = user.password
            }

            //boton de Editar Perfil
            editProfileButton.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
                findNavController().navigate(action)
                // navigateToEditProfileActivity()
            }
            //boton de Logout
            logoutButton.setOnClickListener {
                logout()
            }
            //boton de borrar Usuario
            deleteUserButton?.setOnClickListener{
                lifecycleScope.launch {
                    db.userDao().deleteUserById(currentUserId!!)

                    logout()
                }
            }


            //boton de Ajustes
            settingButton.setOnClickListener {
                val action = ProfileFragmentDirections.settingButton()
                findNavController().navigate(action)
            }
        }

        var fechaActual = LocalDate.now()
        Log.i("Fecha Actual","La fecha actual es $fechaActual")
        var semanaSiguiente = fechaActual.plusWeeks(-1)
        Log.i("Semana siguiente", "La fecha actual mas una semana es $semanaSiguiente")
        var numeroSemanaSiguiente = semanaSiguiente.get(WeekFields.of(Locale.getDefault()).weekOfYear())
        Log.i("Numero de la semana siguiente de hoy","$numeroSemanaSiguiente")

        // Obtén el número de semana del mes utilizando WeekFields
        val numeroSemana = fechaActual.get(WeekFields.of(Locale.getDefault()).weekOfYear())
        Log.i("Numero de la semana del ano","Numero de la semana del ano: $numeroSemana")

        if(numeroSemana > numeroSemanaSiguiente)
            Log.i("numeroSemana es mayor", "Es mayor")
        else
            Log.i("numeroSemana es menor","Es menor")



        var fechaFalsa= LocalDate.of(fechaActual.year, 1, 1)
        val numeroSemanaFechaFalsa = fechaFalsa.get(WeekFields.of(Locale.getDefault()).weekOfYear())
        Log.i("numeroSemanaFechaFalsa","$numeroSemanaFechaFalsa")


    }

    //Este metodo sirve para que los campos estén actualizados al volver desde EditProfileFragment. No funciona usando onViewStateRestored()
    override fun onStart() {
        super.onStart()
        //se recupera el nombre y la contraseña del usuario desde la BD
        lifecycleScope.launch {
            val user = db.userDao().getUserById(currentUserId)
            binding.valueProfileName.text = user.username
            binding.valueProfilePassword.text = user.password
        }
    }

    // finaliza la Activity de la que cuelga este Fragment (invoca por detras a onDestroy())
    private fun logout() {
        Log.i(
            "valor de currentUserId",
            "El valor de currentUserId justo antes de cerrar sesión es: $currentUserId"
        )
        currentUserId =
            null // Lo pongo a null para asegurarme de que no sigue cargado cuando se cierra sesión. currentUserId es un companion object que esta cargado desde la invocacion del login
        requireActivity().finish()
    }


    //Este metodo es SOLO para evitar posibles fugas de memoria.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}

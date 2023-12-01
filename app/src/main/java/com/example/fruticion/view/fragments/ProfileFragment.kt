package com.example.fruticion.view.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fruticion.view.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FruticionDatabase

    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Se obtiene la instancia de la BD
        db = FruticionDatabase.getInstance(requireContext())!!

        repository = Repository.getInstance(getNetworkService(), db)

        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            //se recupera el nombre y la contraseña del usuario desde la BD por primera vez
            lifecycleScope.launch {
                val user = repository.getUserById()
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
            deleteUserButton.setOnClickListener{
                lifecycleScope.launch {
                    repository.deleteUserById()

                    logout()
                }
            }


            //boton de Ajustes
            settingButton.setOnClickListener {
                val action = ProfileFragmentDirections.settingButton()
                findNavController().navigate(action)
            }
        }
    }

    //Este metodo sirve para que los campos estén actualizados al volver desde EditProfileFragment. No funciona usando onViewStateRestored()
    override fun onStart() {
        super.onStart()
        //se recupera el nombre y la contraseña del usuario desde la BD
        lifecycleScope.launch {
            val user = repository.getUserById()
            binding.valueProfileName.text = user.username
            binding.valueProfilePassword.text = user.password
        }
    }

    // finaliza la Activity de la que cuelga este Fragment (invoca por detras a onDestroy())
    private fun logout() {
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

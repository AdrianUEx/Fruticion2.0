package com.example.fruticion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.fruticion.activity.EditProfileActivity
import com.example.fruticion.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.FragmentProfileBinding
import com.example.fruticion.model.User
import kotlinx.coroutines.launch

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

        //se recupera el nombre y la contraseña del usuario desde la BD
        lifecycleScope.launch{
            var user = db.userDao().getUserById(currentUserId)
            binding.valueProfileName.text = user.username
            binding.valueProfilePassword.text = user.password
        }

        //boton de Editar Perfil
        binding.editProfileButton.setOnClickListener{
            navigateToEditProfileActivity()
        }
        //boton de Logout
        binding.logoutButton.setOnClickListener {
            logout()
        }
        //boton de Ajustes
        binding.settingButton.setOnClickListener {
            val action = SettingsFragmentDirections.settingButton()
            findNavController().navigate(action)
        }
    }

    //Este metodo sirve para que los campos estén actualizados al volver desde EditProfileActivity. No funciona usando onViewStateRestored()
    override fun onStart() {
        super.onStart()
        //se recupera el nombre y la contraseña del usuario desde la BD
        lifecycleScope.launch{
            var user = db.userDao().getUserById(currentUserId)
            binding.valueProfileName.text = user.username
            binding.valueProfilePassword.text = user.password
        }
    }
    private fun navigateToEditProfileActivity() {
        EditProfileActivity.start(requireContext())
    }

    // finaliza la Activity de la que cuelga este Fragment (invoca por detras a onDestroy())
    private fun logout(){
       // currentUserId = null // Lo pongo a null para asegurarme de que no sigue cargado cuando se cierra sesión. currentUserId es un companion object que esta cargado desde la invocacion del login
        requireActivity().finish()
    }
}
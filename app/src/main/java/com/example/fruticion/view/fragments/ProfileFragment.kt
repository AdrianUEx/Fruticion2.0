package com.example.fruticion.view.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fruticion.FruticionApplication
import com.example.fruticion.view.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.FragmentProfileBinding
import com.example.fruticion.view.viewModel.ProfileViewModel
import com.example.fruticion.view.viewModel.SearchViewModel
import com.example.fruticion.model.User
import android.util.Log
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

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
            deleteUserButton.setOnClickListener {
                profileViewModel.onDeleteUserButtonClick()

                logout()

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



        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.valueProfileName.text = user?.username
            binding.valueProfilePassword.text = user?.password
        }

        profileViewModel.update()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy() de onStart()", "profile destruido")
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

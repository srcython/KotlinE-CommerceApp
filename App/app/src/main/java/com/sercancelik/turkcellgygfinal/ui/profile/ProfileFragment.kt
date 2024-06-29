package com.sercancelik.turkcellgygfinal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sercancelik.turkcellgygfinal.databinding.BottomSheetEditProfileBinding
import com.sercancelik.turkcellgygfinal.databinding.FragmentProfileBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide
import com.sercancelik.turkcellgygfinal.models.response.User
import com.sercancelik.turkcellgygfinal.util.JwtUtils
import com.sercancelik.turkcellgygfinal.util.KeystoreManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var keystoreManager: KeystoreManager

    @Inject
    lateinit var jwtUtils: JwtUtils

    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contentLayout.visibility = View.GONE

        val userId = getUserIdFromJwt()
        profileViewModel.getUser(userId)

        profileViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                bindUserData(it)
                binding.contentLayout.visibility = View.VISIBLE
            }
        })

        profileViewModel.updateStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                profileViewModel.user.value?.let { bindUserData(it) }
            } else {
                // Handle update failure
            }
        })

        binding.btnEdit.setOnClickListener {
            showEditProfileBottomSheet(profileViewModel.user.value)
        }
    }

    private fun bindUserData(user: User) {
        binding.textViewName.text = "${user.firstName} ${user.lastName}"
        binding.textViewUserMail.text = user.email
        binding.textViewUserName.text = user.username
        binding.textViewUserPhone.text = user.phone
        binding.imageViewProfile.loadImageWithGlide(user.image)
    }

    private fun getUserIdFromJwt(): Long {
        val token = keystoreManager.getToken()
        return if (token != null) {
            jwtUtils.getUserIdFromToken(token) ?: -1L
        } else {
            -1L
        }
    }

    private fun showEditProfileBottomSheet(user: User?) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetEditProfileBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)

        user?.let {
            bottomSheetBinding.editTextFirstName.setText(it.firstName)
            bottomSheetBinding.editTextLastName.setText(it.lastName)
            bottomSheetBinding.editTextEmail.setText(it.email)
            bottomSheetBinding.editTextPass.setText(it.phone)
        }

        bottomSheetBinding.buttonSave.setOnClickListener {
            if (user != null) {
                val updatedFirstName = bottomSheetBinding.editTextFirstName.text.toString()
                val updatedLastName = bottomSheetBinding.editTextLastName.text.toString()
                val updatedEmail = bottomSheetBinding.editTextEmail.text.toString()
                val updatedPass = bottomSheetBinding.editTextPass.text.toString()

                val updatedUser = user.copy(
                    firstName = if (updatedFirstName != user.firstName) updatedFirstName else user.firstName,
                    lastName = if (updatedLastName != user.lastName) updatedLastName else user.lastName,
                    email = if (updatedEmail != user.email) updatedEmail else user.email,
                    phone = if (updatedPass != user.password) updatedPass else user.password
                )

                profileViewModel.updateUser(user.id, updatedUser)
                dialog.dismiss()
            }
        }

        bottomSheetBinding.imageViewClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}

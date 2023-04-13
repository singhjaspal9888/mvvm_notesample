package com.cheezycode.notesample.ui.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cheezycode.notesample.R
import com.cheezycode.notesample.databinding.FragmentRegisterBinding
import com.cheezycode.notesample.models.UserRequest
import com.cheezycode.notesample.models.users.UserRequestRoot
import com.cheezycode.notesample.utils.Constants
import com.cheezycode.notesample.utils.Helper.Companion.hideKeyboard
import com.cheezycode.notesample.utils.LiveDataInternetConnections
import com.cheezycode.notesample.utils.NetworkResult
import com.cheezycode.notesample.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        if (tokenManager.getToken() != null) {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
//                val userRequest = getUserRequest()
//                authViewModel.registerUser(userRequest)

                val userRequestRoot = getUserRequestFake();
                authViewModel.createUser(userRequestRoot)
            } else {
                showValidationErrors(validationResult.second)
            }
        }
        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val userName = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, userName, password, false)
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }


    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                txtEmail.text.toString(),
                txtPassword.text.toString(),
                txtUsername.text.toString()
            )
        }
    }

    private fun getUserRequestFake(): UserRequestRoot {
        return binding.run {
            UserRequestRoot(
                txtEmail.text.toString(),
                txtPassword.text.toString()
            )
        }
    }

    private fun bindObservers() {
//        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
//            when (it) {
//                is NetworkResult.Success -> {
//                    tokenManager.saveToken(it.data!!.token)
//                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
//                }
//                is NetworkResult.Error -> {
//                    showValidationErrors(it.message.toString())
//                }
//                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//        })

        //fake api

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("MAINACTIVITY", "success: " + it.data.toString())
                    findNavController().navigate(R.id.action_registerFragment_to_usersFragment)
                }
                is NetworkResult.Error -> {
                    Log.d("MAINACTIVITY", "error: " + it.message)
                }
                is NetworkResult.Loading -> {
                    Log.d("MAINACTIVITY", "loading: " + it.message)

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
@file:JvmName("HomeFragmentKt")

package com.example.cabo.tp4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cabo.tp4.R
import com.example.cabo.tp4.utils.FieldValidation
import com.example.cabo.tp4.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var login: Button
    private lateinit var buttRegister: Button
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var loadingBar: ProgressBar
    private lateinit var loginVM: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setOnClickListeners()
        setObservers()
    }

    private fun initialize() {
        login = view?.findViewById(R.id.frhome_b_login)!!
        buttRegister = view?.findViewById(R.id.frhome_b_register)!!
        editEmail = view?.findViewById(R.id.frhome_t_email)!!
        editPassword = view?.findViewById(R.id.frhome_t_password)!!
        loadingBar = view?.findViewById(R.id.frhome_loading)!!
        loginVM = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun setOnClickListeners() {

        login.setOnClickListener {
            loginVM.signIn(editEmail.text.toString(), editPassword.text.toString())
        }
        buttRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun setObservers() {
        //Observer loading
        loginVM.loading.observe(viewLifecycleOwner, {
            if (it) {
                loadingBar.visibility = ProgressBar.VISIBLE
            } else {
                loadingBar.visibility = ProgressBar.GONE
            }
        })

        //Observer signin status

        loginVM.signInStatus.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(this.context, "Error en las credenciales", Toast.LENGTH_SHORT).show()
            }
        })


//observer mail validation
        loginVM.mailValidation.observe(viewLifecycleOwner,
            {
                when (it) {
                    FieldValidation.EMAIL_BAD_FORMAT -> editEmail.error =
                        "El formato de mail es invalido."
                    FieldValidation.EMPTY -> editEmail.error =
                        "El campo email no puede estar vacio."
                }
            })

//observer password validator
        loginVM.passwordValidation.observe(viewLifecycleOwner,
            {
                when (it) {
                    FieldValidation.PASSWORD_LENGHT_FAIL -> editPassword.error =
                        "El password ingresado tiene menos de 6 caracteres."
                }
            })
    }
}
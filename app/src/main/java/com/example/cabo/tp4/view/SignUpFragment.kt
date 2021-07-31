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
import com.example.cabo.tp4.viewmodel.SignUpViewModel
import com.google.android.material.textfield.TextInputEditText


class SignUpFragment : Fragment() {

    private lateinit var register: Button
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var passwordConfirmation: TextInputEditText
    private lateinit var loading: ProgressBar
    private lateinit var firebaseSignUpVM: SignUpViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setOnClickListeners()
        setUpObservers()
    }

    private fun setOnClickListeners() {
        register.setOnClickListener {
            firebaseSignUpVM.signUp(
                email.text.toString(),
                password.text.toString(),
                passwordConfirmation.text.toString()
            )
        }
    }


    private fun initialize() {
        register = view?.findViewById(R.id.frs_b_registrar)!!
        email = view?.findViewById(R.id.frs_e_mail)!!
        password = view?.findViewById(R.id.frs_e_pass)!!
        passwordConfirmation = view?.findViewById(R.id.frs_e_passconfirm)!!
        loading = view?.findViewById(R.id.frs_p_loading)!!
        firebaseSignUpVM = ViewModelProvider(this).get(SignUpViewModel::class.java)

    }

    private fun setUpObservers() {
        //loading bar
        firebaseSignUpVM.loading.observe(viewLifecycleOwner, {
            if (it) {
                loading.visibility = ProgressBar.VISIBLE
            } else {
                loading.visibility = ProgressBar.GONE
            }
        })

        //Si el registro es correcto va a fragmento de user logueado
        firebaseSignUpVM.registrationStatus.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(
                    R.id.action_signUpFragment_to_profileFragment
                )
                Toast.makeText(view?.context, "Por favor complete sus datos", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    view?.context,
                    "No se ha podido registrar el usuario. Intente nuevamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        //set errors of mail field
        firebaseSignUpVM.mailValidation.observe(viewLifecycleOwner, {
            when (it) {
                FieldValidation.EMPTY -> email.error =
                    "Este campo no puede estar vacio."
                FieldValidation.EMAIL_BAD_FORMAT -> email.error =
                    "Formato Invalido"
            }
        })
        //set errors of password fields
        firebaseSignUpVM.passwordValidation.observe(viewLifecycleOwner, {
            when (it) {
                FieldValidation.PASSWORD_LENGHT_FAIL -> password.error =
                    "El password debe tener 6 caracteres como minimo"
                FieldValidation.PASSWORD_NOT_EQUAL -> {
                    password.error = "Los password deben ser iguales"
                    passwordConfirmation.error = password.error
                }
            }
        })
    }
}

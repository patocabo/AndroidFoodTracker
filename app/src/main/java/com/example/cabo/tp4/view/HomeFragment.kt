package com.example.cabo.tp4.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cabo.tp4.R
import com.example.cabo.tp4.viewmodel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout

class HomeFragment : Fragment() {

    private lateinit var nombre: TextInputLayout
    private lateinit var dni: TextInputLayout
    private lateinit var sexo: TextInputLayout
    private lateinit var fechNac: TextInputLayout
    private lateinit var localidad: TextInputLayout
    private lateinit var tratamiento: TextInputLayout
    private lateinit var homeVm: HomeViewModel
    private lateinit var edit: Button
    private lateinit var delete: Button
    private lateinit var logout: Button
    private lateinit var loading: LinearProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setOnClickListeners()
        setUpObservers()
        homeVm.loadUserData()
    }

    private fun initialize() {
        nombre = view?.findViewById(R.id.card_nombre)!!
        dni = view?.findViewById(R.id.card_dni)!!
        fechNac = view?.findViewById(R.id.card_fechnac)!!
        sexo = view?.findViewById(R.id.card_sexo)!!
        localidad = view?.findViewById(R.id.card_localidad)!!
        tratamiento = view?.findViewById(R.id.tratamiento)!!
        homeVm = ViewModelProvider(this).get(HomeViewModel::class.java)
        logout = view?.findViewById(R.id.frh_b_logout)!!
        edit = view?.findViewById(R.id.card_edit)!!
        delete = view?.findViewById(R.id.card_delete)!!
        loading = view?.findViewById(R.id.frh_loading)!!

    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setUpObservers() {
        homeVm.loading.observe(viewLifecycleOwner, {
            if (it) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }
        })
        homeVm.signOutStatus.observe(viewLifecycleOwner, {
            when (it) {
                Result.success(true) -> findNavController().navigate(
                    R.id.action_homeFragment_to_loginFragment
                )
            }
        })
        homeVm.usuario.observe(viewLifecycleOwner, {
            it?.let {
                nombre.editText?.setText(it.nombre + " " + it.apellido)
                dni.editText?.setText(it.dni.toString())
                fechNac.editText?.setText(it.fechNac)
                sexo.editText?.setText(it.sexo.toString())
                localidad.editText?.setText(it.localidad)
                tratamiento.editText?.setText(it.tratamiento.toString())
            } ?: run {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Por favor ingrese sus datos para continuar.")
                    .setPositiveButton("completar perfil") { dialog, which ->
                        findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    }
                    .setCancelable(false)
                    .show()
            }

        })
        homeVm.deleted.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(context, "Los datos han sido borrados.", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
        })
    }

    private fun setOnClickListeners() {
        logout.setOnClickListener {
            homeVm.signOut()
        }
        edit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        delete.setOnClickListener {
            setAlert()
        }
    }

    private fun setAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Borrar datos")
            .setMessage("Esta seguro que desea borrar todos los datos?. No se borraran los registros.")
            .setNeutralButton("Cancelar") { dialog, which ->
                // Respond to neutral button press
            }
            .setNegativeButton("Borrar") { dialog, which ->
                homeVm.deleteUserDate()
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
            .show()
    }


}
package com.example.cabo.tp4.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cabo.tp4.R
import com.example.cabo.tp4.model.Sexo
import com.example.cabo.tp4.model.Tratamiento
import com.example.cabo.tp4.viewmodel.ProfileViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {

    private lateinit var nombre: TextInputLayout
    private lateinit var apellido: TextInputLayout
    private lateinit var dni: TextInputLayout
    private lateinit var sexo: TextInputLayout
    private lateinit var fechNac: TextInputLayout
    private lateinit var localidad: TextInputLayout
    private lateinit var tratamiento: TextInputLayout
    private lateinit var profileVM: ProfileViewModel
    private lateinit var edit: MaterialButton
    private lateinit var save: MaterialButton
    private lateinit var loading: LinearProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setOnClickListeners()
        setUpObservers()
        setTextWatches()
        profileVM.loadUserData()

    }

    private fun initialize() {
        nombre = view?.findViewById(R.id.frc_t_tipo)!!
        apellido = view?.findViewById(R.id.frc_t_principal)!!
        dni = view?.findViewById(R.id.frc_t_secundaria)!!
        sexo = view?.findViewById(R.id.frc_t_bebida)!!
        fechNac = view?.findViewById(R.id.frc_t_tiene_postre)!!
        localidad = view?.findViewById(R.id.frc_t_postre)!!
        tratamiento = view?.findViewById(R.id.frc_e_tiene_tentacion)!!
        profileVM = ViewModelProvider(this).get(ProfileViewModel::class.java)
        edit = view?.findViewById(R.id.frp_b_edit)!!
        save = view?.findViewById(R.id.frc_b_save)!!
        loading = view?.findViewById(R.id.frp_loading)!!

        val adapterSexo = ArrayAdapter(requireContext(), R.layout.dropdowns, Sexo.values())
        (sexo.editText as? AutoCompleteTextView)?.setAdapter(adapterSexo)

        val adapterTratamiento =
            ArrayAdapter(requireContext(), R.layout.dropdowns, Tratamiento.values())
        (tratamiento.editText as? AutoCompleteTextView)?.setAdapter(adapterTratamiento)

    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpObservers() {
        profileVM.usuario.observe(viewLifecycleOwner, {
            it?.let {
                nombre.editText?.setText(it.nombre)
                apellido.editText?.setText(it.apellido)
                dni.editText?.setText(it.dni.toString())
                val formatter = SimpleDateFormat("dd/MM/yyy")
                fechNac.editText?.setText(formatter.format(it.fechNac))
                sexo.editText?.setText(it.sexo.toString())
                localidad.editText?.setText(it.localidad)
                tratamiento.editText?.setText(it.tratamiento.toString())

            }
        })
        profileVM.dataInserted.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                Toast.makeText(
                    this.context,
                    "Los datos han sido guardados correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        profileVM.loading.observe(viewLifecycleOwner, {
            if (it) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }
        })
    }

    private fun setOnClickListeners() {
        save.setOnClickListener {
            profileVM.saveData(
                nombre.editText?.text.toString(),
                apellido.editText?.text.toString(),
                dni.editText?.text.toString().toLong(),
                sexo.editText?.text.toString(),
                fechNac.editText?.text.toString(),
                localidad.editText?.text.toString(),
                tratamiento.editText?.text.toString()
            )
        }
    }

    private fun setTextWatches() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                enableSave()

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                enableSave()
            }
        }
        nombre.editText?.addTextChangedListener(textWatcher)
        apellido.editText?.addTextChangedListener(textWatcher)
        dni.editText?.addTextChangedListener(textWatcher)
        sexo.editText?.addTextChangedListener(textWatcher)
        localidad.editText?.addTextChangedListener(textWatcher)
        tratamiento.editText?.addTextChangedListener(textWatcher)
    }

    private fun enableSave() {
        save.isEnabled = dni.editText?.text?.isNotEmpty() == true &&
                nombre.editText?.text?.isNotEmpty() == true &&
                apellido.editText?.text?.isNotEmpty() == true &&
                sexo.editText?.text?.isNotEmpty() == true &&
                localidad.editText?.text?.isNotEmpty() == true &&
                tratamiento.editText?.text?.isNotEmpty() == true &&
                fechNac.editText?.text?.isNotEmpty() == true
        edit.isEnabled = save.isEnabled
    }

}
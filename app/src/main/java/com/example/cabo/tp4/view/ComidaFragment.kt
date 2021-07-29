package com.example.cabo.tp4.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cabo.tp4.R
import com.example.cabo.tp4.model.Bebida
import com.example.cabo.tp4.model.TipoComida
import com.example.cabo.tp4.viewmodel.ComidaViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat


class ComidaFragment : Fragment() {

    private lateinit var tipo: TextInputLayout
    private lateinit var principal: TextInputLayout
    private lateinit var secundaria: TextInputLayout
    private lateinit var bebida: TextInputLayout
    private lateinit var tienePostre: MaterialCheckBox
    private lateinit var postre: TextInputLayout
    private lateinit var tieneTentacion: MaterialCheckBox
    private lateinit var tentacion: TextInputLayout
    private lateinit var tieneHambre: MaterialCheckBox
    private lateinit var save: MaterialButton
    private lateinit var comidaVM: ComidaViewModel
    private lateinit var fecha: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comida, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setOnClickListeners()
        setUpObservers()
        setTextWatches()

    }

    private fun initialize() {
        tipo = view?.findViewById(R.id.frc_t_tipo)!!
        principal = view?.findViewById(R.id.frc_t_principal)!!
        secundaria = view?.findViewById(R.id.frc_t_secundaria)!!
        bebida = view?.findViewById(R.id.frc_t_bebida)!!
        tienePostre = view?.findViewById(R.id.frc_t_tiene_postre)!!
        postre = view?.findViewById(R.id.frc_t_postre)!!
        tieneTentacion = view?.findViewById(R.id.frc_e_tiene_tentacion)!!
        tentacion = view?.findViewById(R.id.frc_t_tentacion)!!
        tieneHambre = view?.findViewById(R.id.frc_e_tiene_hambre)!!
        comidaVM = ViewModelProvider(this).get(ComidaViewModel::class.java)
        save = view?.findViewById(R.id.frc_b_save)!!
        fecha = view?.findViewById(R.id.frc_fecha)!!

        val adapterTipoComida =
            ArrayAdapter(requireContext(), R.layout.dropdowns, TipoComida.values())
        (tipo.editText as? AutoCompleteTextView)?.setAdapter(adapterTipoComida)


    }

    private fun setUpObservers() {
        comidaVM.dataInserted.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    context,
                    "Los datos de la comida han sido guardados.",
                    Toast.LENGTH_LONG
                ).show()
                comidaVM.getBebidaFromApi(requireContext())
            }
        })
        comidaVM.bebida.observe(viewLifecycleOwner, {
            it?.let {
                setBebidaDialog(it)
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun setOnClickListeners() {
        fecha.setOnClickListener {
            val format = "dd/MM/yyyy"
            val x = SimpleDateFormat(format)
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.show(childFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                fecha.setText(x.format(it))
            }
        }
        save.setOnClickListener {
            comidaVM.saveComidaData(
                tipo.editText?.text.toString(),
                principal.editText?.text.toString(),
                secundaria.editText?.text.toString(),
                bebida.editText?.text.toString(),
                tienePostre.isChecked,
                postre.editText?.text.toString(),
                tieneTentacion.isChecked,
                tentacion.editText?.text.toString(),
                tieneHambre.isChecked,
                fecha.text.toString()
            )
        }
        tienePostre.setOnClickListener {
            if (tienePostre.isChecked) {
                postre.visibility = View.VISIBLE
            } else {
                postre.visibility = View.GONE
            }
        }
        tieneTentacion.setOnClickListener {
            if (tieneTentacion.isChecked) {
                tentacion.visibility = View.VISIBLE
            } else {
                tentacion.visibility = View.GONE
            }
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
        fecha.addTextChangedListener(textWatcher)
        principal.editText?.addTextChangedListener(textWatcher)
        secundaria.editText?.addTextChangedListener(textWatcher)
        bebida.editText?.addTextChangedListener(textWatcher)

    }

    private fun enableSave() {
        save.isEnabled = fecha.text.isNotEmpty() == true &&
                principal.editText?.text?.isNotEmpty() == true &&
                secundaria.editText?.text?.isNotEmpty() == true &&
                bebida.editText?.text?.isNotEmpty() == true &&
                tipo.editText?.text?.isNotEmpty() == true
    }

    @SuppressLint("SetTextI18n")
    private fun setBebidaDialog(bebida: Bebida) {

        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.bebida_layout, null)
        val imgView = view.findViewById<ImageView>(R.id.bebida_draw)
        view.findViewById<TextView>(R.id.bebida_nombre).text =
            "Nombre: " + bebida.drinks[0].strDrink
        view.findViewById<TextView>(R.id.bebida_categoria).text =
            "Categoria: " + bebida.drinks[0].strCategory

        Glide.with(requireActivity())
            .load(bebida.drinks[0].strDrinkThumb)
            .centerCrop()
            .into(imgView)

        val bebidaDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        bebidaDialog.setMessage(
            "Felicitaciones! " +
                    "Tenes el siguiente permitido: "
        )
        bebidaDialog.setView(view)
        bebidaDialog.show().window?.setLayout(1300, 2100)

    }

}
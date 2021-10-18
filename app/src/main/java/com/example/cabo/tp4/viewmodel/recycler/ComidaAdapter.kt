package com.example.cabo.tp4.viewmodel.recycler

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cabo.tp4.R
import com.example.cabo.tp4.model.Comida

class ComidaAdapter(var lista: List<Comida>) : RecyclerView.Adapter<ComidaAdapter.ViewHolder>() {

    val formatter = SimpleDateFormat("dd/MM/yyy HH:mm:ss")

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fechaCarga: TextView = view.findViewById(R.id.card_fecha_carga)
        var fechaComida: TextView = view.findViewById(R.id.card_fecha_comida)
        var principal: TextView = view.findViewById(R.id.card_principal)
        var checkPostre: CheckBox = view.findViewById(R.id.card_postre)
        var checkHambre: CheckBox = view.findViewById(R.id.card_hambriento)
        var checkTento: CheckBox = view.findViewById(R.id.card_tento)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_comida_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.principal.text = lista[position].principal
        holder.fechaCarga.text = "Fecha de carga: " + formatter.format(lista[position].created)
        holder.fechaComida.text = "Fecha de comida : " + lista[position].fecha
        holder.checkPostre.isChecked = lista[position].tienePostre == true
        holder.checkHambre.isChecked = lista[position].tieneHambre == true
        holder.checkTento.isChecked = lista[position].tieneTentacion == true
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
package com.example.cabo.tp4.viewmodel.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cabo.tp4.R
import com.example.cabo.tp4.model.Comida

class ComidaAdapter(var lista: List<Comida>) : RecyclerView.Adapter<ComidaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fecha: TextView = view.findViewById(R.id.card_fecha)
        var tipo: TextView = view.findViewById(R.id.card_tipo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_comida_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tipo.text = lista[position].tipo?.name
            holder.fecha.text = lista[position].fecha
    }

    override fun getItemCount(): Int {
        return lista.size
    }


}
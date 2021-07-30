package com.example.cabo.tp4.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cabo.tp4.R
import com.example.cabo.tp4.model.Comida
import com.example.cabo.tp4.viewmodel.RegistroViewModel
import com.example.cabo.tp4.viewmodel.recycler.ComidaAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.tabs.TabLayout

class RegistroFragment : Fragment() {

    lateinit var registroVM : RegistroViewModel
    lateinit var recyclerComida : RecyclerView
    lateinit var tabLayout : TabLayout
    lateinit var loading : LinearProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setUpObservers()

        setUpTabChange()

    }

    private fun initialize(){
        registroVM = ViewModelProvider(this).get(RegistroViewModel::class.java)
        recyclerComida = view?.findViewById(R.id.rv_comida)!!
        tabLayout = view?.findViewById(R.id.frr_tab)!!
        loading = view?.findViewById(R.id.frr_loading)!!

    }

    private fun setUpObservers(){
        registroVM.listaComida.observe(viewLifecycleOwner,{
            setRecycler(it)
        })
        registroVM.loading.observe(viewLifecycleOwner,{
            if (it) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setRecycler(lista: List<Comida>){
        recyclerComida.layoutManager =LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        recyclerComida.adapter = ComidaAdapter(lista)
    }

    private fun setUpTabChange(){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->registroVM.getComidas("DESAYUNO")
                    1->registroVM.getComidas("ALMUERZO")
                    2->registroVM.getComidas("MERIENDA")
                    3->registroVM.getComidas("CENA")
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }
}
package com.example.peddlerapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animaly.activities.ui.search.MesProduitsViewModel
import com.example.peddlerapp.AjouterProduitActivity
//import com.example.peddler.screens.AjouterProduitActivity
import com.example.peddlerapp.R
import com.example.peddlerapp.adapters.MesProduitsAdapter
import com.example.peddlerapp.models.Produit
import com.example.peddlerapp.util.ApiClient
import com.example.peddlerapp.util.UserSession
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MesProduitsFragment : Fragment() {

    private lateinit var mesProduitsViewModel: MesProduitsViewModel

    lateinit var produitArrayOrig : ArrayList<Produit>
    lateinit var produitArray : ArrayList<Produit>
    lateinit var produitAdapter : MesProduitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mesProduitsViewModel =
            ViewModelProvider(this).get(MesProduitsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mes_produits, container, false)



        produitArrayOrig = arrayListOf<Produit>()
        produitArray = arrayListOf<Produit>()
        produitAdapter = MesProduitsAdapter(produitArray, root.context)

        val produitRv = root.findViewById<RecyclerView>(R.id.produit_rv)

        val produitLayoutManager = GridLayoutManager(root.context, 2)

        produitRv.layoutManager = produitLayoutManager
        produitRv.adapter = produitAdapter


        val ajouter_produit_btn = root.findViewById<Button>(R.id.ajouter_produit_btn)
        ajouter_produit_btn.setOnClickListener(View.OnClickListener {

            val intent = Intent(root.context, AjouterProduitActivity::class.java)
            startActivity(intent)

        })


        return root
    }
    override fun onResume() {  // After a pause OR at startup
        super.onResume()
        //Refresh your stuff here

        println("mes produits resume called")


        ApiClient.apiService.getUserProduits(UserSession.id).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    t.printStackTrace()

                }
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()

                        if(response.code() == 200)
                        {
                            produitArray.clear()
                            produitArrayOrig.clear()

                            val produits = content!!.getAsJsonArray("produits")

                            produits.forEach {
                                val item = it.asJsonObject

                                val produit = Produit(
                                    item.get("_id").asString,
                                    item.get("name")?.asString ?:"null",
                                    item.get("imagePath")?.asString ?:"null",
                                    item.get("price")?.asDouble ?: 0.0,
                                    item.get("information")?.asString ?: "null",
                                    1
                                )

                                produitArrayOrig.add(produit)
                                produitArray.add(produit)
                            }

                            produitAdapter.notifyDataSetChanged()


                        }


                    }
                    else {
                        val content = response.body()

                        println(content)


                    }
                }
            }
        )

    }
}
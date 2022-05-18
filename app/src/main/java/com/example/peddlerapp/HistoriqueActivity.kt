package com.example.peddlerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peddlerapp.adapters.HistoriqueCommandeAdapter
import com.example.peddlerapp.models.CommandeItem
import com.example.peddlerapp.util.ApiClient
import com.example.peddlerapp.util.UserSession
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoriqueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        supportActionBar?.hide()

        val historiqueArr =  ArrayList<CommandeItem>()

        val historiqueRv = findViewById<RecyclerView>(R.id.historique_rv)
        val historiqueAdapter = HistoriqueCommandeAdapter(historiqueArr)


        ApiClient.apiService.getUserCommandes(UserSession.id).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    t.printStackTrace()

                }
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()

                        if(response.code() == 200)
                        {

                            if (content != null) {
                                if(!content.get("Commandes").isJsonNull) {
                                    val commandes = content?.getAsJsonArray("Commandes")

                                    var i=1;
                                    commandes.forEach {
                                        val item = it.asJsonObject

                                        val commande = CommandeItem(
                                            item.get("_id").asString,
                                            "Commande $i",
                                            item.get("total").asDouble
                                        )

                                        historiqueArr.add(commande)
                                        i += 1
                                    }

                                    historiqueAdapter.notifyDataSetChanged()


                                }
                            }


                        }


                    }
                    else {
                        val content = response.body()

                        println(content)


                    }
                }
            }
        )


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        historiqueRv.layoutManager = layoutManager
        historiqueRv.adapter = historiqueAdapter

        historiqueAdapter.notifyDataSetChanged()


        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener {
            onBackPressed()

        }




    }

}
package com.example.demo_instaforfood.Fragments.ClientFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.Client
import com.example.demo_instaforfood.R

class ClientAdapter(private var clientList: List<Client>) : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {

    fun setList(list : List<Client>){
        clientList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       val client = clientList[position]

        holder.apply {
            tvName.text = client.name
            tvNameFirstLetter.text = client.name.get(0).toString()
            if (client.client_phones.size > 0) {
                tvPhoneNumber.text = client.client_phones[0].number
            } else {
                tvPhoneNumber.text = ""
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return clientList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ClientView: View) : RecyclerView.ViewHolder(ClientView) {
        val tvName: TextView = ClientView.findViewById(R.id.tvName)
        val tvPhoneNumber: TextView = ClientView.findViewById(R.id.tvPhoneNum)
        val tvNameFirstLetter:TextView = ClientView.findViewById(R.id.tvNameLetter)
    }
}
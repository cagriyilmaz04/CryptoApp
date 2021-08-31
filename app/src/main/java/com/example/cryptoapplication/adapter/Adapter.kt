package com.example.cryptoapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapplication.databinding.RecyclerRowBinding
import com.example.cryptoapplication.model.CryptoModel

class Adapter:RecyclerView.Adapter<Adapter.TutucuVH>() {
    var emptyList=emptyList<CryptoModel>()
    class TutucuVH(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutucuVH {
        return TutucuVH(RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TutucuVH, position: Int) {
        holder.binding.textCryptoName.text= emptyList.get(position).currency
        holder.binding.textCryptoPrice.text=emptyList.get(position).price.toString()
    }

    override fun getItemCount(): Int {
       return emptyList.size
    }
    fun refreshData(data:List<CryptoModel>){
        this.emptyList=data
        notifyDataSetChanged()
    }

}
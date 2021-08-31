package com.example.cryptoapplication.view

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapplication.R
import com.example.cryptoapplication.adapter.Adapter
import com.example.cryptoapplication.databinding.FragmentFirstBinding
import com.example.cryptoapplication.model.CryptoModel
import com.example.cryptoapplication.viewmodel.ViewModel
import java.util.*
import kotlin.collections.ArrayList


class FirstFragment : Fragment(R.layout.fragment_first) {
   private var _binding :FragmentFirstBinding?=null
    val binding  get() = _binding!!
    var diziler=ArrayList<CryptoModel>()
    var temp_array=ArrayList<CryptoModel>()
    val adapter=Adapter()
    var temp_dizi=ArrayList<CryptoModel>()
    var flag=-1
    lateinit var viewModel:ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFirstBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        val spinner_dizi=resources.getStringArray(R.array.spinner)
        val adapter_spinner=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,spinner_dizi)
        binding.spinnerForFragment.adapter=adapter_spinner
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        observeData(flag)
        binding.spinnerForFragment.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                   if(position>0){
                       flag=position
                       observeData(position)
                   }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return binding.root
    }
    private fun observeData(flag:Int){
        viewModel.takeData()
        viewModel.data.observe(viewLifecycleOwner){
                diziler= it as ArrayList<CryptoModel>
            temp_array=it
            temp_dizi.addAll(diziler)
            try {
                DecideWhichOne(flag)
            }catch (e:Exception){
            }
        }
        viewModel.loading.observe(viewLifecycleOwner){
            if(it==true){
                binding.progressBar.visibility=View.VISIBLE
            }else if(it==false){
                binding.progressBar.visibility=View.INVISIBLE
            }
        }
    }
    fun DecideWhichOne(flag:Int):List<CryptoModel>{
        binding.recylcerView.layoutManager=LinearLayoutManager(requireContext())
        val returnedArray=ArrayList<CryptoModel>()
        when(flag){
            -1->{
                returnedArray.addAll(diziler)
                adapter.refreshData(diziler)
            }
            1->{
                val a_z=diziler.sortedWith(compareBy( { it.currency }))
                    adapter.refreshData(a_z)
                returnedArray.addAll(a_z)
            }
            2->{
                val z_a=diziler.sortedWith(compareByDescending {
                    it.currency
                })
                adapter.refreshData(z_a)
              returnedArray.addAll(z_a)
            }
            3->{
                val low_to_high=diziler.sortedWith(compareBy {
                    it.price
                })
                adapter.refreshData(low_to_high)
                returnedArray.addAll(low_to_high)
            }
            4->{
              val high_to_low=diziler.sortedWith(compareByDescending { it.price })
                adapter.refreshData(high_to_low)
                returnedArray.addAll(high_to_low)
            }

        }
        binding.recylcerView.adapter=adapter
        return returnedArray
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_row,menu)
        val item=menu.findItem(R.id.search_view)
        val searchView=(item.actionView) as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText=newText!!.toLowerCase(Locale.getDefault())
                val our_new_array=DecideWhichOne(flag)
                if(searchText.isNotEmpty()){
                    for(i in 0 until temp_dizi.size){
                        if(temp_dizi.get(i).currency.toLowerCase(Locale.getDefault()).contains(searchText)){
                            diziler.clear()
                            try {
                                diziler.add(our_new_array.get(i))
                                binding.recylcerView.adapter?.notifyDataSetChanged()
                            }catch (e:Exception){

                            }
                        }
                    }
                }else{
                    diziler.clear()
                    diziler.addAll(temp_dizi)
                    adapter.refreshData(temp_dizi)
                    binding.recylcerView.adapter?.notifyDataSetChanged()
                }
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
package com.example.countries.adapter

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.ItemCountryBinding
import com.example.countries.model.Country
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import com.example.countries.view.FeedFragmentDirections


class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(var binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(
            inflater,
            R.layout.item_country,
            parent,
            false
        )


        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country = countryList[position]
        holder.binding.listener = this
        /*
        // bu kodlar yerine Data binding çalışıyor
            holder.binding.apply {
                countryName.text = countryList[position].countryName
                countryRegion.text = countryList[position].countryRegion
                ivCountryFlag.downloadFromUrl(
                    countryList[position].countryFlag, placeHolderProgressBar(holder.itemView.context)
                )
            }
            holder.itemView.setOnClickListener {
                val action =
                    FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
                Navigation.findNavController(it).navigate(action)
            }
        */
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)

        notifyDataSetChanged()
    }

    override fun onItemClicked(view: View) {

        val _binding = DataBindingUtil.getBinding<ItemCountryBinding>(view)
        val uuid = _binding?.tvCountryUuid?.text.toString().toInt()


        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }
}
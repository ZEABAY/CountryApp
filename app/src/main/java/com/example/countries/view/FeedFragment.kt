package com.example.countries.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentFeedBinding
import com.example.countries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private lateinit var _binding: FragmentFeedBinding
    private val binding get() = _binding

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()

        binding.apply {
            countryList.layoutManager = LinearLayoutManager(context)
            countryList.adapter = countryAdapter
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->

            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

            viewModel.countryError.observe(viewLifecycleOwner) { error ->
                error?.let {
                    binding.countryError.visibility = if (it) View.VISIBLE else View.GONE
                }

            }

            viewModel.countryLoading.observe(viewLifecycleOwner) { loading ->
                loading?.let {
                    if (it) {
                        binding.apply {
                            countryLoading.visibility = View.VISIBLE
                            countryList.visibility = View.GONE
                            countryError.visibility = View.GONE
                        }
                    } else {
                        binding.countryLoading.visibility = View.GONE
                    }
                }

            }

        }
    }
}
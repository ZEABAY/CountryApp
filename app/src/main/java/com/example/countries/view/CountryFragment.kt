package com.example.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.countries.databinding.FragmentCountryBinding
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import com.example.countries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var _binding: FragmentCountryBinding
    private val binding get() = _binding


    private lateinit var viewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountryBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner) { country ->
            country?.let {
                binding.apply {
                    tvCountryName.text = country.countryName
                    tvCountryCapital.text = country.countryCapital
                    tvCountryRegion.text = country.countryRegion
                    tvCountryCurrency.text = country.countryCurrency
                    tvCountryLanguage.text = country.countryLanguage
                    context?.let {
                        ivCountryFlagImg.downloadFromUrl(
                            country.countryFlag,
                            placeHolderProgressBar(it)
                        )
                    }
                }

            }

        }
    }

}
package com.weather.jpmc.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.weather.jpmc.R
import com.weather.jpmc.databinding.WeatherFragmentBinding
import com.weather.jpmc.mvvm.MainViewModel

class WeatherFragment : Fragment() {

    private lateinit var binding: WeatherFragmentBinding

    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        mainViewModel.weatherData.observe(viewLifecycleOwner) { uiState ->
            binding.progressBar.visibility = View.GONE
            when (uiState) {
                is UIState.Success -> {
                    binding.weatherData = uiState.weatherData
                    binding.weatherData?.name?.let { it1 -> mainViewModel.updateToolBarTitle(it1) }
                }
                is UIState.Error -> {
                    ErrorDialogFragment.newInstance(uiState.title, uiState.message)
                        .show(childFragmentManager, "")
                }
                else -> {

                }
            }
        }

        return binding.root
    }
}
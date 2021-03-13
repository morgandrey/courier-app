package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentAdditionalBinding
import com.example.courierapp.util.hideApp
import moxy.MvpAppCompatFragment


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class AdditionalFragment : MvpAppCompatFragment(R.layout.fragment_additional) {

    private val binding: FragmentAdditionalBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_additional, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
        with(binding) {
            profileButton.setOnClickListener {
                findNavController().navigate(R.id.action_additionalFragment_to_profileFragment)
            }
            settingsButton.setOnClickListener {
                findNavController().navigate(R.id.action_additionalFragment_to_settingsFragment)
            }
        }
    }
}
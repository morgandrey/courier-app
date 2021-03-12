package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentHistoryBinding
import com.example.courierapp.util.hideApp
import moxy.MvpAppCompatFragment


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class HistoryFragment : MvpAppCompatFragment(R.layout.fragment_history) {

    private val binding: FragmentHistoryBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
    }
}
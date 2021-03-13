package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentActiveOrdersBinding
import com.example.courierapp.util.hideApp
import moxy.MvpAppCompatFragment


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class ActiveOrdersFragment : MvpAppCompatFragment(R.layout.fragment_active_orders) {

    private val binding: FragmentActiveOrdersBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_active_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
    }
}
package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.adapters.OrderAdapter
import com.example.courierapp.databinding.FragmentAvailableOrdersBinding
import com.example.courierapp.models.Courier
import com.example.courierapp.models.Order
import com.example.courierapp.presentation.AvailableOrdersPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.hideApp
import com.example.courierapp.util.showToast
import com.example.courierapp.views.AvailableOrdersView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class AvailableOrdersFragment : MvpAppCompatFragment(R.layout.fragment_available_orders), AvailableOrdersView {

    private val presenter: AvailableOrdersPresenter by moxyPresenter { AvailableOrdersPresenter() }
    private val binding: FragmentAvailableOrdersBinding by viewBinding()
    private lateinit var courier: Courier
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_available_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
        preferencesManager = PreferencesManager(requireContext())
        courier = preferencesManager.getCourier()!!
        presenter.getAvailableOrders(courier.CourierId)
    }

    override fun onSuccessGetAvailableOrders(orderList: List<Order>) {
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = OrderAdapter(orderList)
    }

    override fun switchLoading(show: Boolean) {
        when (show) {
            true -> {
                binding.orderProgressBar.visibility = View.VISIBLE
                binding.orderRecyclerView.visibility = View.GONE
            }
            false -> {
                binding.orderProgressBar.visibility = View.GONE
                binding.orderRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun showError(message: String) {
        showToast(requireContext(), message)
    }

    override fun showError(message: Int) {
        showToast(requireContext(), message)
    }
}
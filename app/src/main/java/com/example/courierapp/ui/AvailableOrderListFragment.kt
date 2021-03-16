package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.MyApplication
import com.example.courierapp.R
import com.example.courierapp.adapters.AdapterType
import com.example.courierapp.adapters.OrderAdapter
import com.example.courierapp.databinding.FragmentAvailableOrderListBinding
import com.example.courierapp.models.Order
import com.example.courierapp.presentation.OrderListPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.hideApp
import com.example.courierapp.util.showToast
import com.example.courierapp.views.OrderListView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class AvailableOrderListFragment : MvpAppCompatFragment(R.layout.fragment_available_order_list), OrderListView {

    private val presenter: OrderListPresenter by moxyPresenter { OrderListPresenter() }
    private val binding: FragmentAvailableOrderListBinding by viewBinding()

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_available_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        presenter.getAvailableOrders(preferencesManager.getCourier()!!.CourierId)
    }

    override fun onSuccessGetOrders(orderList: List<Order>) {
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = OrderAdapter(orderList, AdapterType.Available)
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
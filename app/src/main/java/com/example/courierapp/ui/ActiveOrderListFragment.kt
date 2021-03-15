package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.MyApplication
import com.example.courierapp.R
import com.example.courierapp.adapters.OrderAdapter
import com.example.courierapp.databinding.FragmentActiveOrderListBinding
import com.example.courierapp.models.Order
import com.example.courierapp.presentation.ActiveOrderListPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.hideApp
import com.example.courierapp.util.showToast
import com.example.courierapp.views.ActiveOrderListView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class ActiveOrderListFragment : MvpAppCompatFragment(R.layout.fragment_active_order_list), ActiveOrderListView {

    private val presenter: ActiveOrderListPresenter by moxyPresenter { ActiveOrderListPresenter() }
    private val binding: FragmentActiveOrderListBinding by viewBinding()

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_active_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        presenter.getActiveOrders(preferencesManager.getCourier()!!.CourierId)
    }

    override fun switchLoading(show: Boolean) {
        when (show) {
            true -> {
                binding.activeOrdersProgressBar.visibility = View.VISIBLE
                binding.activeOrdersRecyclerView.visibility = View.GONE
            }
            false -> {
                binding.activeOrdersProgressBar.visibility = View.GONE
                binding.activeOrdersRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSuccessGetActiveOrders(orderList: List<Order>) {
        binding.activeOrdersRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.activeOrdersRecyclerView.adapter = OrderAdapter(orderList, true)
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
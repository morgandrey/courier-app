package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.MyApplication
import com.example.courierapp.R
import com.example.courierapp.adapters.AdapterType
import com.example.courierapp.adapters.OrderAdapter
import com.example.courierapp.databinding.FragmentHistoryOrderListBinding
import com.example.courierapp.models.CourierAnalysis
import com.example.courierapp.models.Order
import com.example.courierapp.presentation.OrderListPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.hideApp
import com.example.courierapp.util.showToast
import com.example.courierapp.views.OrderListView
import com.google.gson.Gson
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class HistoryOrderListFragment : MvpAppCompatFragment(R.layout.fragment_history_order_list),
    OrderListView {

    private val presenter: OrderListPresenter by moxyPresenter { OrderListPresenter() }
    private val binding: FragmentHistoryOrderListBinding by viewBinding()
    private lateinit var courierAnalysis: CourierAnalysis

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideApp(requireActivity(), viewLifecycleOwner)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        binding.analysisButton.setOnClickListener {
            val bundle = bundleOf("courierAnalysis" to Gson().toJson(courierAnalysis))
            findNavController().navigate(R.id.action_historyFragment_to_analysisFragment, bundle)
        }
        presenter.getHistoryOrders(preferencesManager.getCourier()!!.CourierId)
    }

    override fun onSuccessGetOrders(orderList: List<Order>) {
        binding.historyOrdersRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.historyOrdersRecyclerView.adapter = OrderAdapter(orderList, AdapterType.History)

        val map = mutableMapOf<Long, Long>()
        var i = 0L
        orderList.forEach { x -> map.put(i++, x.OrderRating) }
        courierAnalysis = CourierAnalysis(
            countOfOrders = orderList.size.toLong(),
            totalSum = orderList.sumByDouble { x -> x.CourierReward },
            ratingList = map
        )
    }

    override fun switchLoading(show: Boolean) {
        when (show) {
            true -> {
                binding.historyProgressBar.visibility = View.VISIBLE
                binding.historyOrdersRecyclerView.visibility = View.GONE
            }
            false -> {
                binding.historyProgressBar.visibility = View.GONE
                binding.historyOrdersRecyclerView.visibility = View.VISIBLE
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
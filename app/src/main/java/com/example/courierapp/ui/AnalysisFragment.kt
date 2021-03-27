package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentAnalysisBinding
import com.example.courierapp.models.CourierAnalysis
import com.example.courierapp.presentation.AnalysisPresenter
import com.example.courierapp.views.AnalysisView
import com.google.gson.Gson
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 16/03/2021.
 */

class AnalysisFragment : MvpAppCompatFragment(R.layout.fragment_analysis), AnalysisView {

    private val presenter: AnalysisPresenter by moxyPresenter { AnalysisPresenter() }
    private val binding: FragmentAnalysisBinding by viewBinding()
    private lateinit var courierAnalysis: CourierAnalysis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courierAnalysis = Gson().fromJson(
            requireArguments().getString("courierAnalysis"),
            CourierAnalysis::class.java
        )
        binding.analysisCompletedOrdersTextView.text =
            getString(R.string.orders_completed_for_all_time, courierAnalysis.countOfOrders.toString())
        binding.analysisEarnedTextView.text =
            getString(R.string.earned_for_all_time, courierAnalysis.totalSum.toString())
        presenter.setDataChart(binding.chart, courierAnalysis)
    }

    override fun onSuccessSetData() {
        TODO("Not yet implemented")
    }
}
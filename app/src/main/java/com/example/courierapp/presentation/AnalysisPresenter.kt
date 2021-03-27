package com.example.courierapp.presentation

import android.graphics.Color
import android.graphics.DashPathEffect
import com.example.courierapp.models.CourierAnalysis
import com.example.courierapp.views.AnalysisView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import moxy.MvpPresenter
import java.util.*


/**
 * Created by Andrey Morgunov on 17/03/2021.
 */

class AnalysisPresenter : MvpPresenter<AnalysisView>() {

    fun setDataChart(chart: LineChart, data: CourierAnalysis) {
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        // chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        // create marker to display box when values are selected
        //val mv = MyMarkerView(this, R.layout.custom_marker_view)
        // Set the marker to the chart
        //mv.setChartView(chart)
        // chart.marker = mv
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val countOfOrders = data.ratingList.maxOf { x -> x.key }
        val xAxis = chart.xAxis
        val yAxis = chart.axisLeft
        xAxis.axisMaximum = countOfOrders.toFloat() + 1
        yAxis.axisMinimum = 0f

        xAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        chart.axisRight.isEnabled = false

        if (countOfOrders > 5) {
            chart.animateX(2500)
        }
        val l = chart.legend
        l.form = Legend.LegendForm.LINE

        val values = ArrayList<Entry>()

        data.ratingList.forEach { x -> values.add(Entry(x.key.toFloat(), x.value.toFloat())) }
        val dataSet: LineDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            dataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            dataSet.values = values
            dataSet.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            dataSet = LineDataSet(values, "Rating")
            with(dataSet) {
                setDrawIcons(false)
                enableDashedHighlightLine(10f, 5f, 0f)
                color = Color.BLACK
                setCircleColor(Color.RED)
                lineWidth = 2f
                circleRadius = 3f
                setDrawCircleHole(false)
                //customize legend entry
                formLineWidth = 1f
                formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
                formSize = 15f

                valueTextSize = 12f

                //setDrawFilled(false)
                fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
                fillColor = Color.BLACK
            }
            chart.data = LineData(dataSet)
        }
    }
}
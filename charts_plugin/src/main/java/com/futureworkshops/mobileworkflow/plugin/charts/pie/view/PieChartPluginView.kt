/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import android.graphics.Color
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.model.result.EmptyAnswerResult
import com.futureworkshops.mobileworkflow.model.result.FragmentStepResult
import com.futureworkshops.mobileworkflow.plugin.charts.R
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mattyork.colours.Colour

internal class PieChartPluginView(
    fragmentStepConfiguration: FragmentStepConfiguration,
    val itemsProvider: ItemsProvider
) : FragmentStep(fragmentStepConfiguration),
    StyleListener {

    private lateinit var pieChartPluginPart: PieChartPluginPart

    override fun getStepOutput(): AnswerResult = EmptyAnswerResult()

    override fun isValidInput(): Boolean = true

    override fun setupViews() {
        super.setupViews()
        context?.let { safeContext ->
            pieChartPluginPart = PieChartPluginPart(safeContext, this)
            content.add(pieChartPluginPart)
        }
    }

    override fun onTintColorReady(color: Int) {
        itemsProvider.onItemsReady { setupPie(it) }
    }

    private fun setupPie(items: List<PieChartItem>) {
        val map = items.map { item ->
            PieEntry(item.value, item.label)
        }

        val pieDataSet = PieDataSet(map, "").apply {
            val complementaryColors: IntArray =
                Colour.colorSchemeOfType(color, Colour.ColorScheme.ColorSchemeComplementary)
            colors = complementaryColors.toMutableList()

            valueTextColor = Color.WHITE
            valueTextSize = 12f
        }

        val pieData = PieData(pieDataSet)

        pieChartPluginPart.view.findViewById<PieChart>(R.id.pieChart).apply {
            data = pieData
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            description = Description().apply {
                text = ""
            }
            isDrawHoleEnabled = false
            legend.isEnabled = false
            isRotationEnabled = false
            invalidate()
        }
    }
}

internal interface StyleListener {

    fun onTintColorReady(color: Int)

}
/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import android.graphics.Color
import com.futureworkshops.mobileworkflow.plugin.charts.R
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.surveykit.StepIdentifier
import com.futureworkshops.mobileworkflow.surveykit.backend.views.step.QuestionView
import com.futureworkshops.mobileworkflow.surveykit.result.QuestionResult
import com.futureworkshops.mobileworkflow.surveykit.result.question_results.EmptyQuestionResult
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mattyork.colours.Colour

internal class PieChartPluginView(
    id: StepIdentifier,
    isOptional: Boolean,
    title: String,
    nextButton: String,
    val itemsProvider: ItemsProvider
) : QuestionView(id, isOptional, title, null, nextButton),
    StyleListener {

    private lateinit var pieChartPluginPart: PieChartPluginPart

    override fun createResults(): QuestionResult = EmptyQuestionResult(id, startDate)

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
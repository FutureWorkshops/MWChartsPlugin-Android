/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import android.content.Context
import android.graphics.Color
import com.futureworkshops.mobileworkflow.plugin.charts.R
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmptyQuestionResult

internal class PieChartPluginView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String,
    val items: List<PieChartItem>
) : QuestionView(context, id, isOptional, title, null, "Next") {

    private lateinit var pieChartPluginPart: PieChartPluginPart

    override fun createResults(): QuestionResult = EmptyQuestionResult(id, startDate)

    override fun isValidInput(): Boolean = true

    override fun setupViews() {
        super.setupViews()
        pieChartPluginPart = PieChartPluginPart(context)
        content.add(pieChartPluginPart)

        val map = items.map { item ->
            PieEntry(item.value, item.label)
        }

        val pieDataSet = PieDataSet(map, "")
        pieDataSet.setColors(
            intArrayOf(
                R.color.chart_color_1,
                R.color.chart_color_2,
                R.color.chart_color_3,
                R.color.chart_color_4,
                R.color.chart_color_5
            ), context
        )
        pieDataSet.valueTextColor = Color.BLACK
        val pieData = PieData(pieDataSet)
        val pieChart = pieChartPluginPart.view.findViewById<PieChart>(R.id.pieChart)
        pieChart.data = pieData
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.description = Description().apply {
            text = context.getString(R.string.quantities)
        }
        pieChart.invalidate()
    }

}
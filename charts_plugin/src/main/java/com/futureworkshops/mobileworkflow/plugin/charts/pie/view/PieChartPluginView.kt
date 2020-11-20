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
import com.mattyork.colours.Colour
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.DictionaryHelper
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmptyQuestionResult

internal class PieChartPluginView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String,
    val items: List<PieChartItem>
) : QuestionView(context, id, isOptional, title, null, DictionaryHelper.getTranslation("Next")) {

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

        val pieDataSet = PieDataSet(map, "").apply {
            // TODO: Replace Color.RED for tintColor
            val complementaryColors: IntArray =
                Colour.colorSchemeOfType(Color.RED, Colour.ColorScheme.ColorSchemeComplementary)
            colors = complementaryColors.toMutableList()

            valueTextColor = Color.WHITE
            valueTextSize = context.resources.getDimension(R.dimen.small_text)
        }

        val pieData = PieData(pieDataSet)

        pieChartPluginPart.view.findViewById<PieChart>(R.id.pieChart).apply {
            data = pieData
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(context.resources.getDimension(R.dimen.small_text))
            description = Description().apply {
                text = null
            }
            isDrawHoleEnabled = false
            legend.isEnabled = false
            isRotationEnabled = false
            invalidate()
        }
    }

}
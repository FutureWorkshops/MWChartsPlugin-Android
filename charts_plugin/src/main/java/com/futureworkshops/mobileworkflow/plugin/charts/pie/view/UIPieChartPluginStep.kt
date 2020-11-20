/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import android.content.Context
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.DictionaryHelper
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.steps.Step

internal class UIPieChartPluginStep(
    private val title: String,
    override val uuid: String,
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier(),
    val items: List<PieChartItem>
) : Step {

    override fun createView(context: Context, stepResult: StepResult?): StepView {
        items.forEach { DictionaryHelper.getTranslation(it.label) }

        return PieChartPluginView(
            context = context,
            id = id,
            isOptional = isOptional,
            title = DictionaryHelper.getTranslation(title),
            items = items
        )
    }

}
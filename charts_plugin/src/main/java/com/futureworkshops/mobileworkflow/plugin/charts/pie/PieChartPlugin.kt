/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.view.UIPieChartPluginStep
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.steps.Step

internal class PieChartPlugin : DeserializeStep<PieChartPluginStep>(
    type = "chartsPieChart",
    classT = PieChartPluginStep::class.java
) {

    override fun createUIStep(step: PieChartPluginStep): Step = UIPieChartPluginStep(
        title = step.title,
        uuid = step.uuid,
        isOptional = step.optional,
        id = StepIdentifier(step.identifier),
        items = step.items
    )

}
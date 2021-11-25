/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.view.UIPieChartPluginStep

import com.futureworkshops.mobileworkflow.steps.Step

internal class PieChartPlugin : DeserializeStep<PieChartPluginStep>(
    type = "chartsPieChart",
    classT = PieChartPluginStep::class.java
) {

    override fun createUIStep(step: PieChartPluginStep): Step = UIPieChartPluginStep(
        title = step.title,
        id = step.id,
        items = step.items
    )

}
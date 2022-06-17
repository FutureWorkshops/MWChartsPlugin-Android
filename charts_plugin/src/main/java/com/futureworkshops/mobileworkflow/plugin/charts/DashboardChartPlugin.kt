/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.view.UIChartPluginStep
import com.futureworkshops.mobileworkflow.steps.Step

internal class DashboardChartPlugin: DeserializeStep<DashboardChartPluginStep>(
    type = "io.mobileworkflow.Dashboard",
    classT = DashboardChartPluginStep::class.java
) {
    override fun createUIStep(step: DashboardChartPluginStep): Step {
        return UIChartPluginStep(
            title = step.title,
            id = step.id,
            items = step.items,
            numberOfColumns = step.numberOfColumns.toInt(),
        )
    }
}
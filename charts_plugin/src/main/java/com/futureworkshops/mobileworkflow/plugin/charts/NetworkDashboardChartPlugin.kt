/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.step.NetworkDashboardChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.view.UINetworkChartPluginStep
import com.futureworkshops.mobileworkflow.steps.Step

class NetworkDashboardChartPlugin: DeserializeStep<NetworkDashboardChartPluginStep>(
    type = "io.app-rail.charts.network-dashboard",
    classT = NetworkDashboardChartPluginStep::class.java
) {
    override fun createUIStep(step: NetworkDashboardChartPluginStep): Step {
        return UINetworkChartPluginStep(
            title = step.title,
            id = step.id,
            numberOfColumns = step.numberOfColumns.toInt(),
            url = step.url
        )
    }
}
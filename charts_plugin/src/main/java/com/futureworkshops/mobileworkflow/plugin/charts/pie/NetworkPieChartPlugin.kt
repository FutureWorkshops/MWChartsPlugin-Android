package com.futureworkshops.mobileworkflow.plugin.charts.pie

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.NetworkPieChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.view.UINetworkPieChartPluginStep

import com.futureworkshops.mobileworkflow.steps.Step

class NetworkPieChartPlugin : DeserializeStep<NetworkPieChartPluginStep>(
    type = "io.mobileworkflow.NetworkPieChart",
    classT = NetworkPieChartPluginStep::class.java
) {
    override fun createUIStep(step: NetworkPieChartPluginStep): Step = UINetworkPieChartPluginStep(
        title = step.title,

        isOptional = step.optional,
        id = step.id,
        url = step.url
    )
}

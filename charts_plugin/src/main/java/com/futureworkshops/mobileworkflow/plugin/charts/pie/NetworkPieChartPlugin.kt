package com.futureworkshops.mobileworkflow.plugin.charts.pie

import com.futureworkshops.mobileworkflow.domain.DeserializeStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.NetworkPieChartPluginStep
import com.futureworkshops.mobileworkflow.plugin.charts.pie.view.UINetworkPieChartPluginStep
import com.futureworkshops.mobileworkflow.surveykit.StepIdentifier
import com.futureworkshops.mobileworkflow.surveykit.steps.Step

class NetworkPieChartPlugin : DeserializeStep<NetworkPieChartPluginStep>(
    type = "io.mobileworkflow.NetworkPieChart",
    classT = NetworkPieChartPluginStep::class.java
) {
    override fun createUIStep(step: NetworkPieChartPluginStep): Step = UINetworkPieChartPluginStep(
        title = step.title,
        uuid = step.uuid,
        isOptional = step.optional,
        id = StepIdentifier(step.identifier),
        url = step.url
    )
}

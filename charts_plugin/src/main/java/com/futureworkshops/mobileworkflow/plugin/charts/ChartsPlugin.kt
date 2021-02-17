package com.futureworkshops.mobileworkflow.plugin.charts

import com.futureworkshops.mobileworkflow.domain.PluginFactory
import com.futureworkshops.mobileworkflow.plugin.charts.pie.NetworkPieChartPlugin
import com.futureworkshops.mobileworkflow.plugin.charts.pie.PieChartPlugin

class ChartsPlugin : PluginFactory(
    listOf(
        PieChartPlugin(),
        NetworkPieChartPlugin()
    )
)
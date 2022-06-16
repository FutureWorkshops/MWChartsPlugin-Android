package com.futureworkshops.mobileworkflow.plugin.charts

import com.futureworkshops.mobileworkflow.domain.PluginFactory
import com.futureworkshops.mobileworkflow.model.plugin.PluginInformation
import com.futureworkshops.mobileworkflow.plugin.charts.pie.NetworkPieChartPlugin
import com.futureworkshops.mobileworkflow.plugin.charts.pie.PieChartPlugin

class ChartsPluginFactory : PluginFactory(
    listOf(
        PieChartPlugin(),
        NetworkPieChartPlugin()
    )
) {
    override fun getInformation(): PluginInformation = PluginInformation(
        name = BuildConfig.LIBRARY_PACKAGE_NAME,
        version = BuildConfig.PLUGIN_VERSION
    )
}
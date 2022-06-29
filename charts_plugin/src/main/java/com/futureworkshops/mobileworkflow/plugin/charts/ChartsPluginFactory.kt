package com.futureworkshops.mobileworkflow.plugin.charts

import com.futureworkshops.mobileworkflow.domain.PluginFactory
import com.futureworkshops.mobileworkflow.model.plugin.PluginInformation

class ChartsPluginFactory : PluginFactory(
    listOf(
        DashboardChartPlugin(),
        NetworkDashboardChartPlugin()
    )
) {
    override fun getInformation(): PluginInformation = PluginInformation(
        name = BuildConfig.LIBRARY_PACKAGE_NAME,
        version = BuildConfig.PLUGIN_VERSION
    )
}
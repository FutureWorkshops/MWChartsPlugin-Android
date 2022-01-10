package com.futureworkshops.mobileworkflow.plugin.charts.pie.step

import android.os.Parcelable
import com.futureworkshops.mobileworkflow.model.step.PluginStep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class NetworkPieChartPluginStep(
    override val id: String,
    override val title: String,
    override val type: String,
    val optional: Boolean,
    val url: String
) : PluginStep(), Parcelable
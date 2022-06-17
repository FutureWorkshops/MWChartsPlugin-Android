package com.futureworkshops.mobileworkflow.plugin.charts.step

import android.os.Parcelable
import com.futureworkshops.mobileworkflow.model.configuration.NavigationItem
import com.futureworkshops.mobileworkflow.model.configuration.StepLink
import com.futureworkshops.mobileworkflow.model.step.PluginStep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class NetworkChartPluginStep(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("links") override val links: List<StepLink>,
    @SerializedName("navigationItems") override val navigationItems: List<NavigationItem>,
    @SerializedName("type") override val type: String,
    @SerializedName("url") val url: String
) : PluginStep(), Parcelable
/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.step

import android.os.Parcelable
import com.futureworkshops.mobileworkflow.model.configuration.NavigationItem
import com.futureworkshops.mobileworkflow.model.configuration.StepLink
import com.futureworkshops.mobileworkflow.model.step.PluginStep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PieChartPluginStep(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("links") override val links: List<StepLink>,
    @SerializedName("navigationItems") override val navigationItems: List<NavigationItem>,
    @SerializedName("type") override val type: String,
    @SerializedName("optional") val optional: Boolean,
    @SerializedName("items") val items: List<PieChartItem>
) : PluginStep(), Parcelable

@Parcelize
data class PieChartItem(
    @SerializedName("id") val id: String,
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: Float,
    @SerializedName("listItemId") val listItemId: Int
) : Parcelable
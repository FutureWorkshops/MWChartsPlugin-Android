/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.step

import android.os.Parcelable
import com.futureworkshops.mobileworkflow.model.configuration.NavigationItem
import com.futureworkshops.mobileworkflow.model.configuration.StepLink
import com.futureworkshops.mobileworkflow.model.step.PluginStep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardChartPluginStep(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("links") override val links: List<StepLink>,
    @SerializedName("navigationItems") override val navigationItems: List<NavigationItem>,
    @SerializedName("type") override val type: String,
    @SerializedName("items") val items: List<DashboardChartItem>,
    @SerializedName("numberOfColumns") val numberOfColumns: String
): PluginStep(), Parcelable

@Parcelize
data class DashboardBaseChartItem(
    val id: String,
    val listItemId: String,
    val chartType: String,
    val title: String,
    val text: String?,
    val subtitle: String? = null,
    val footer: String? = null,
    val values: List<String>,
    val colors: List<String>? = null,
    val colorsDark: List<String>? = null
): Parcelable

@Parcelize
data class DashboardChartItem(
    @SerializedName("id") val id: String,
    @SerializedName("chartType") val chartType: String,
    @SerializedName("listItemId") val listItemId: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("footer") val footer: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("values") val values: String? = null,
    @SerializedName("chartValues") val chartValues: String? = null,
    @SerializedName("chartColors") val chartColors: String? = null,
    @SerializedName("chartColorsDark") val chartColorsDark: String? = null,
): Parcelable

@Parcelize
data class DashboardNetworkChartItem(
    @SerializedName("id") val id: String,
    @SerializedName("chartType") val chartType: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("footer") val footer: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("chartValues") val chartValues: List<String>? = null,
    @SerializedName("chartColors") val chartColors: List<String>? = null,
    @SerializedName("chartColorsDark") val chartColorsDark: List<String>? = null,
): Parcelable


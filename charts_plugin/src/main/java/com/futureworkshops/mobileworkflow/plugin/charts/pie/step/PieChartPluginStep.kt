/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.step

import android.os.Parcelable
import com.futureworkshops.mobileworkflow.model.step.PluginStep
import kotlinx.parcelize.Parcelize

@Parcelize
data class PieChartPluginStep(
    override val title: String,
    override val type: String,
    val optional: Boolean,
    val items: List<PieChartItem>
) : PluginStep(), Parcelable

@Parcelize
data class PieChartItem(
    val id: String,
    val label: String,
    val value: Float,
    val listItemId: Int
) : Parcelable
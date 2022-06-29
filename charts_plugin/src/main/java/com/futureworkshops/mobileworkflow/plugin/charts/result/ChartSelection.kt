/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.result

import com.futureworkshops.mobileworkflow.model.result.PropertyResultComponent
import com.futureworkshops.mobileworkflow.model.result.ResultComponent
import com.futureworkshops.mobileworkflow.model.result.SelectedResult
import com.futureworkshops.mobileworkflow.model.step.Item
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardBaseChartItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChartSelection(
    val chartItem: DashboardBaseChartItem
) : SelectedResult(chartItem.toItem()) {

    override fun getResultValue(): String {
        return StringBuilder().apply {
            append("ID: ${chartItem.id}")
            append("Title: ${chartItem.title}")
        }.toString()
    }

    override fun getUploadRepresentation(): Any = chartItem
    override fun toString(): String = getResultValue()

    override fun getComponent(component: String): ResultComponent? {
        return when (component) {
            "id" -> PropertyResultComponent(chartItem.id)
            "title" -> PropertyResultComponent(chartItem.title)
            else -> super.getComponent(component)
        }
    }
}

fun DashboardBaseChartItem.toItem(): Item =
    Item(id = id, text = title, detailText = null, imageUrl = null)
/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.futureworkshops.mobileworkflow.plugin.charts.R

class ChartPart(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleRes) {

    val viewChartStep: View = View.inflate(context, R.layout.chart_step, this)
}
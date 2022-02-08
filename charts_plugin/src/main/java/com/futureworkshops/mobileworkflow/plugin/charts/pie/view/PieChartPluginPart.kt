/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.futureworkshops.mobileworkflow.SurveyTheme
import com.futureworkshops.mobileworkflow.backend.views.main_parts.StyleablePart
import com.futureworkshops.mobileworkflow.plugin.charts.R

internal class PieChartPluginPart @JvmOverloads constructor(
    context: Context,
    private val styleListener: StyleListener,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleRes), StyleablePart {

    val view: View = View.inflate(context, R.layout.pie_chart_plugin_step, this)

    override fun style(surveyTheme: SurveyTheme) {
        styleListener.onTintColorReady(surveyTheme.themeColor)
    }

}
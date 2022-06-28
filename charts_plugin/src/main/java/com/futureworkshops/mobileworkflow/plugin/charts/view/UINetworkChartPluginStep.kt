/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view

import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.data.network.task.URLMethod
import com.futureworkshops.mobileworkflow.model.AppServiceResponse
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardNetworkChartItem
import com.futureworkshops.mobileworkflow.services.ServiceBox
import com.futureworkshops.mobileworkflow.services.localization.LocalizationService
import com.futureworkshops.mobileworkflow.steps.Step
import com.google.gson.reflect.TypeToken

data class UINetworkChartPluginStep(
    override val id: String,
    val title: String,
    val numberOfColumns: Int,
    val url: String
): Step {

    override fun createView(
        stepResult: AnswerResult?,
        services: ServiceBox,
        appServiceResponse: AppServiceResponse
    ): FragmentStep {
        val resolvedURL = services.urlTaskBuilder.urlHelper.resolveUrl(
            appServiceResponse.server,
            url,
            appServiceResponse.session
        )

        val task = URLIAsyncTask<Nothing, List<DashboardNetworkChartItem>>(
            resolvedURL ?: url,
            URLMethod.GET,
            null,
            emptyMap(),
            object : TypeToken<List<DashboardNetworkChartItem>>() {}.type
        )

        return ChartPluginView(
            FragmentStepConfiguration(
                title = services.localizationService.getTranslation(title),
                text = null,
                nextButtonText = services.localizationService.getTranslation(LocalizationService.PredefinedText.NEXT),
                services = services
            ),
            itemsProvider = ItemProvider.AsyncItemsProvider(
                services.serviceContainer,
                task,
                services.localizationService
            ),
            numberOfColumns = numberOfColumns
        )
    }

}

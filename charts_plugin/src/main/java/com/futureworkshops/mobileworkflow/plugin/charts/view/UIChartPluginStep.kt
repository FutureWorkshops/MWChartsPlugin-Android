/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view

import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.model.AppServiceResponse
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardChartItem
import com.futureworkshops.mobileworkflow.services.ServiceBox
import com.futureworkshops.mobileworkflow.services.localization.LocalizationService
import com.futureworkshops.mobileworkflow.steps.Step

internal data class UIChartPluginStep(
    override val id: String,
    val title: String,
    val items: List<DashboardChartItem>,
    val numberOfColumns: Int
) : Step {

    override fun createView(
        stepResult: AnswerResult?,
        services: ServiceBox,
        appServiceResponse: AppServiceResponse
    ): FragmentStep {

        return ChartPluginView(
            FragmentStepConfiguration(
                title = services.localizationService.getTranslation(title),
                text = null,
                nextButtonText = services.localizationService.getTranslation(LocalizationService.PredefinedText.NEXT),
                services = services
            ),
            itemsProvider = ItemProvider.SyncItemsProvider(items, services.localizationService),
            numberOfColumns = numberOfColumns
        )
    }
}
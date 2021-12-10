/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view


import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.model.WorkflowServiceResponse
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.services.ServiceBox
import com.futureworkshops.mobileworkflow.steps.Step

internal data class UIPieChartPluginStep(
    val title: String,
    override val id: String,
    val items: List<PieChartItem>
) : Step {

    override fun createView(
        stepResult: AnswerResult?,
        services: ServiceBox,
        workflowServiceResponse: WorkflowServiceResponse
    ): FragmentStep {
        items.forEach { services.localizationService.getTranslation(it.label) }

        return PieChartPluginView(
            FragmentStepConfiguration(
                        title = services.localizationService.getTranslation(title),
            text = null,
            nextButtonText = services.localizationService.getTranslation("Next"),
            services = services),
            itemsProvider = ItemsProvider.SyncItemsProvider(items)
        )
    }
}
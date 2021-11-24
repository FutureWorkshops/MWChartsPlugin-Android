/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view


import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.model.WorkflowServiceResponse
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.services.MobileWorkflowServices
import com.futureworkshops.mobileworkflow.steps.Step

internal data class UIPieChartPluginStep(
    val title: String,
    override val uuid: String,
    override var isOptional: Boolean = false,
    override val id: String,
    val items: List<PieChartItem>
) : Step {

    override fun createView(
        stepResult: AnswerResult?,
        mobileWorkflowServices: MobileWorkflowServices,
        workflowServiceResponse: WorkflowServiceResponse,
        selectedWorkflowId: String
    ): FragmentStep {
        items.forEach { mobileWorkflowServices.localizationService.getTranslation(it.label) }

        return PieChartPluginView(
            FragmentStepConfiguration(
            isOptional = isOptional,
            title = mobileWorkflowServices.localizationService.getTranslation(title),
            text = null,
            nextButtonText = mobileWorkflowServices.localizationService.getTranslation("Next"),
            mobileWorkflowServices = mobileWorkflowServices),
            itemsProvider = ItemsProvider.SyncItemsProvider(items)
        )
    }
}
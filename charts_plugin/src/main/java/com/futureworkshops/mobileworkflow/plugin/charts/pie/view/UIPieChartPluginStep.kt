/*
 * Copyright (c) 2020 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import com.futureworkshops.mobileworkflow.model.WorkflowServiceResponse
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.StepIdentifier
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.result.StepResult
import com.futureworkshops.mobileworkflow.services.MobileWorkflowServices
import com.futureworkshops.mobileworkflow.steps.Step

internal class UIPieChartPluginStep(
    private val title: String,
    override val uuid: String,
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier(),
    val items: List<PieChartItem>
) : Step {

    override fun createView(
        stepResult: StepResult?,
        mobileWorkflowServices: MobileWorkflowServices,
        workflowServiceResponse: WorkflowServiceResponse,
        selectedWorkflowId: String
    ): FragmentStep {
        items.forEach { mobileWorkflowServices.localizationService.getTranslation(it.label) }

        return PieChartPluginView(
            id = id,
            isOptional = isOptional,
            title = mobileWorkflowServices.localizationService.getTranslation(title),
            nextButton = mobileWorkflowServices.localizationService.getTranslation("Next"),
            itemsProvider = ItemsProvider.SyncItemsProvider(items),
            viewFactory = mobileWorkflowServices.viewFactory
        )
    }

}
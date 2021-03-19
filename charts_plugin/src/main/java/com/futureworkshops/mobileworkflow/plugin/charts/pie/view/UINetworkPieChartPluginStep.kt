package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.data.network.task.URLMethod
import com.futureworkshops.mobileworkflow.model.WorkflowServiceResponse
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.StepIdentifier
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.result.StepResult
import com.futureworkshops.mobileworkflow.services.MobileWorkflowServices
import com.futureworkshops.mobileworkflow.steps.Step
import com.google.gson.reflect.TypeToken

class UINetworkPieChartPluginStep(
    private val title: String,
    override val uuid: String,
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier(),
    private val url: String
) : Step {
    override fun createView(
        stepResult: StepResult?,
        mobileWorkflowServices: MobileWorkflowServices,
        workflowServiceResponse: WorkflowServiceResponse,
        selectedWorkflowId: String
    ): FragmentStep {
        val fullUrl = "${workflowServiceResponse.server?.url}/${url}"

        // TODO: would be nice to replace with a call to URLIAsyncTask.build(url, method)
        val task = URLIAsyncTask<Unit, List<PieChartItem>>(
            fullUrl,
            URLMethod.GET,
            null,
            emptyMap(),
            object : TypeToken<List<PieChartItem>>() {}.type
        )

        return PieChartPluginView(
            id = id,
            isOptional = isOptional,
            title = mobileWorkflowServices.localizationService.getTranslation(title),
            nextButton = mobileWorkflowServices.localizationService.getTranslation("Next"),
            itemsProvider = ItemsProvider.AsyncItemsProvider(
                mobileWorkflowServices.serviceContainer,
                task
            ),
            viewFactory = mobileWorkflowServices.viewFactory
        )
    }
}
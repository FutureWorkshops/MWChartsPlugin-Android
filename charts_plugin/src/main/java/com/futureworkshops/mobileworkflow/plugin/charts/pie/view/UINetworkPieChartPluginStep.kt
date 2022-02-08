package com.futureworkshops.mobileworkflow.plugin.charts.pie.view


import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.data.network.task.URLMethod
import com.futureworkshops.mobileworkflow.model.AppServiceResponse
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.services.ServiceBox
import com.futureworkshops.mobileworkflow.services.localization.LocalizationService.PredefinedText
import com.futureworkshops.mobileworkflow.steps.Step
import com.google.gson.reflect.TypeToken

data class UINetworkPieChartPluginStep(
    val title: String,
    override val id: String,
    private val url: String
) : Step {
    override fun createView(
        stepResult: AnswerResult?,
        services: ServiceBox,
        appServiceResponse: AppServiceResponse
    ): FragmentStep {
        val fullUrl = "${appServiceResponse.server?.url}/${url}"
        
        val task = URLIAsyncTask<Nothing, List<PieChartItem>>(
            fullUrl,
            URLMethod.GET,
            null,
            emptyMap(),
            object : TypeToken<List<PieChartItem>>() {}.type
        )

        return PieChartPluginView(
            FragmentStepConfiguration(
                title = services.localizationService.getTranslation(title),
                text = null,
                nextButtonText = services.localizationService.getTranslation(PredefinedText.NEXT),
                services = services
            ),
            itemsProvider = ItemsProvider.AsyncItemsProvider(
                services.serviceContainer,
                task
            )
        )
    }
}
/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view

import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.domain.service.ServiceContainer
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardBaseChartItem
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardChartItem
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardNetworkChartItem
import com.futureworkshops.mobileworkflow.services.localization.LocalizationService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class ItemProvider {

    abstract fun onItemsReady(
        provider: (List<DashboardBaseChartItem>) -> Unit,
        onLoading: () -> Unit,
        onError: () -> Unit
    )

    open fun retry() {}

    class SyncItemsProvider(
        private val items: List<DashboardChartItem>,
        private val localizationService: LocalizationService
    ) : ItemProvider() {
        override fun onItemsReady(
            provider: (List<DashboardBaseChartItem>) -> Unit,
            onLoading: () -> Unit,
            onError: () -> Unit
        ) {
            val formalisedItems = items.map {
                DashboardBaseChartItem(
                    id = it.id,
                    listItemId = it.listItemId,
                    chartType = it.chartType,
                    title = localizationService.getTranslation(it.title),
                    text = it.text,
                    subtitle = localizationService.getTranslationOrNull(it.subtitle),
                    footer = localizationService.getTranslationOrNull(it.footer),
                    values = it.chartValues?.split(",") ?: emptyList(),
                    colors = it.chartColors?.split(","),
                    colorsDark = it.chartColorsDark?.split(",")
                )
            }
            provider(formalisedItems)
        }
    }

    class AsyncItemsProvider(
        private val serviceContainer: ServiceContainer,
        private val task: URLIAsyncTask<Nothing, List<DashboardNetworkChartItem>>,
        private val localizationService: LocalizationService
    ) : ItemProvider() {
        private lateinit var success: (List<DashboardBaseChartItem>) -> Unit
        private lateinit var loading: () -> Unit
        private lateinit var error: () -> Unit

        override fun retry() {
            super.retry()
            load()
        }

        override fun onItemsReady(
            provider: (List<DashboardBaseChartItem>) -> Unit,
            onLoading: () -> Unit,
            onError: () -> Unit
        ) {
            success = provider
            loading = onLoading
            error = onError
            load()
        }

        private fun load() {
            loading()
            serviceContainer.performSingle<URLIAsyncTask<Nothing, List<DashboardNetworkChartItem>>, List<DashboardNetworkChartItem>>(
                task
            ).subscribeOn(Schedulers.io())
                .map { items ->
                    items.map {
                        DashboardBaseChartItem(
                            id = it.id,
                            listItemId = it.id,
                            chartType = it.chartType,
                            title = localizationService.getTranslation(it.title),
                            text = it.text,
                            subtitle = localizationService.getTranslationOrNull(it.subtitle),
                            footer = localizationService.getTranslationOrNull(it.footer),
                            values = it.chartValues
                            ?: emptyList(),
                            colors = it.chartColors,
                            colorsDark = it.chartColorsDark
                        )
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { success(it) },
                    { error ->
                        error()
                        error.printStackTrace()
                    }
                )
        }
    }
}
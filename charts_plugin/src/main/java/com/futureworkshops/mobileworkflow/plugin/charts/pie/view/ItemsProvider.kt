package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.domain.service.ServiceContainer
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class ItemsProvider {

    abstract fun onItemsReady(provider: (List<PieChartItem>) -> Unit)

    class SyncItemsProvider(private val items: List<PieChartItem>) : ItemsProvider() {
        override fun onItemsReady(provider: (List<PieChartItem>) -> Unit) {
            provider(items)
        }
    }

    class AsyncItemsProvider(
        private val serviceContainer: ServiceContainer,
        private val task: URLIAsyncTask<Nothing, List<PieChartItem>>
    ) : ItemsProvider() {
        override fun onItemsReady(provider: (List<PieChartItem>) -> Unit) {
            serviceContainer.performSingle<URLIAsyncTask<Nothing, List<PieChartItem>>, List<PieChartItem>>(
                task
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { items -> provider(items) },
                    { error -> error.printStackTrace() })
        }
    }
}
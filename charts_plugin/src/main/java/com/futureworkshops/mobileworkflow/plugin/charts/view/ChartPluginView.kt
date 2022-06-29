/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.model.result.AnswerResult
import com.futureworkshops.mobileworkflow.model.result.EmptyAnswerResult
import com.futureworkshops.mobileworkflow.plugin.charts.R
import com.futureworkshops.mobileworkflow.plugin.charts.result.ChartSelection
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardBaseChartItem
import com.futureworkshops.mobileworkflow.plugin.charts.view.adapter.ChartsAdapter
import com.futureworkshops.mobileworkflow.ui.base.ErrorView
import com.futureworkshops.mobileworkflow.ui.base.LoadingView

class ChartPluginView(
    fragmentStepConfiguration: FragmentStepConfiguration,
    private val itemsProvider: ItemProvider,
    private val numberOfColumns: Int
) : FragmentStep(fragmentStepConfiguration) {

    private lateinit var chartPart: ChartPart
    private var result: DashboardBaseChartItem? = null

    private val adapter: ChartsAdapter by lazy {
        ChartsAdapter(::onItemClick)
    }

    private fun onItemClick(item: DashboardBaseChartItem) {
        result = item
        footer.onContinue()
    }

    override fun isValidInput(): Boolean = result != null

    override fun getStepOutput(): AnswerResult =
        result?.let { ChartSelection(it) } ?: EmptyAnswerResult()

    override fun setupViews() {
        super.setupViews()
        content.makeContainerMatchParent()
        loadStack()
    }

    private fun loadStack() {
        val loadingView = LoadingView(context = requireContext())
        val errorView = ErrorView(context = requireContext())

        itemsProvider.onItemsReady(
            onLoading = { content.add(loadingView) },
            onError = {
                content.clear()
                content.add(errorView)
                errorView.setRetryFun { itemsProvider.retry() }
            },
            provider = { items ->
                content.clear()
                context?.let { safeContext ->
                    chartPart = ChartPart(safeContext).apply {
                        viewChartStep.findViewById<RecyclerView>(R.id.chartRV).apply {
                            layoutManager =
                                GridLayoutManager(safeContext, numberOfColumns)
                            adapter = this@ChartPluginView.adapter
                        }
                        setupChartView(items)
                    }.let(content::add)
                }
            }
        )
    }

    private fun setupChartView(items: List<DashboardBaseChartItem>) {
        adapter.submitList(items)
    }
}
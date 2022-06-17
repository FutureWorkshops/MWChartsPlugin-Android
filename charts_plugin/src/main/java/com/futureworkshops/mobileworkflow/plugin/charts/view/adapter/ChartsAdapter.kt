/*
 * Copyright (c) 2022 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.mobileworkflow.plugin.charts.view.adapter

import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futureworkshops.mobileworkflow.model.step.Item
import com.futureworkshops.mobileworkflow.plugin.charts.R
import com.futureworkshops.mobileworkflow.plugin.charts.databinding.ChartItemBinding
import com.futureworkshops.mobileworkflow.plugin.charts.databinding.StatisticItemBinding
import com.futureworkshops.mobileworkflow.plugin.charts.step.DashboardBaseChartItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.mattyork.colours.Colour

internal class ChartsAdapter(
    private val onItemClick: (DashboardBaseChartItem) -> Unit
) : RecyclerView.Adapter<ChartsAdapter.ChartItemViewHolder>() {

    private var _items: MutableList<DashboardBaseChartItem> = mutableListOf()

    fun submitList(items: List<DashboardBaseChartItem>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartItemViewHolder {
        return when (val itemType = ChartItemViewType.getByType(viewType)) {
            ChartItemViewType.LINE,
            ChartItemViewType.BAR,
            ChartItemViewType.PIE -> {
                val binding = ChartItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChartViewHolder(binding, itemType)
            }
            ChartItemViewType.STATISTIC -> {
                val binding = StatisticItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChartStaticViewHolder(binding)
            }
            null -> TODO()
        }
    }

    override fun onBindViewHolder(holder: ChartItemViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int = _items.size

    override fun getItemViewType(position: Int): Int {
        return when (_items[position].chartType) {
            "line" -> ChartItemViewType.LINE.type
            "bar" -> ChartItemViewType.BAR.type
            "pie" -> ChartItemViewType.PIE.type
            "statistic" -> ChartItemViewType.STATISTIC.type
            else -> -1
        }
    }

    abstract inner class ChartItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: DashboardBaseChartItem)
    }

    inner class ChartViewHolder(
        private val binding: ChartItemBinding,
        private val itemType: ChartItemViewType
    ) : ChartItemViewHolder(binding.root) {

        override fun bind(item: DashboardBaseChartItem) {

            binding.titleTv.text = item.title
            item.subtitle?.let { subtitle ->
                binding.subtitleTv.apply {
                    text = subtitle
                    visibility = View.VISIBLE
                }
            }
            item.footer?.let { footer ->
                binding.footerTv.apply {
                    text = footer
                    visibility = View.VISIBLE
                }
            }

            when (itemType) {
                ChartItemViewType.LINE -> getLineView(item, binding)
                ChartItemViewType.BAR -> getBarView(item, binding)
                ChartItemViewType.PIE -> getPieView(item, binding)
                else -> null
            }?.let(binding.chartContainer::addView)

            binding.touchOverlay.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    inner class ChartStaticViewHolder(
        private val binding: StatisticItemBinding
    ) : ChartItemViewHolder(binding.root) {
        override fun bind(item: DashboardBaseChartItem) {
            binding.titleTv.text = item.title
            binding.valueTxv.text = item.values.first()
            binding.container.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    enum class ChartItemViewType(val type: Int) {
        LINE(0), BAR(1), PIE(2), STATISTIC(3);

        companion object {
            fun getByType(type: Int) = values().groupBy { it.type }[type]?.first()
        }
    }

    private fun getLineView(item: DashboardBaseChartItem, binding: ChartItemBinding): View {

        val entries = item.values.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value.toFloat())
        }

        val currentNightMode =
            binding.root.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        val lineDataSet = LineDataSet(entries, "").apply {
            setDrawValues(false)
            setDrawCircles(false)
            colors = getChartColors(isNightTheme, item.colors, item.colorsDark, color)
            valueTextColor = if (isNightTheme) Color.WHITE else Color.BLACK
            valueTextSize = 12f
        }

        val chart = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.line_bar_item, null)
        val lineData = LineData(lineDataSet)

        chart.findViewById<LineChart>(R.id.lineChart).apply {
            setBorderColor(Color.RED)
            description = Description().apply { text = "" }
            legend.isEnabled = false
            data = lineData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            notifyDataSetChanged()
            invalidate()
        }
        return chart
    }

    private fun getBarView(item: DashboardBaseChartItem, binding: ChartItemBinding): View {

        val map = item.values.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value.toFloat())
        }

        val currentNightMode =
            binding.root.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        val barDataSet = BarDataSet(map, "").apply {
            setDrawValues(false)
            colors = getChartColors(isNightTheme, item.colors, item.colorsDark, color)
            valueTextColor = if (isNightTheme) Color.WHITE else Color.BLACK
            valueTextSize = 12f
        }

        val chart = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.bar_chart_item, null)
        val barData = BarData(barDataSet)

        chart.findViewById<BarChart>(R.id.barChart).apply {
            description = Description().apply {
                text = ""
            }
            legend.isEnabled = false
            data = barData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            notifyDataSetChanged()
            invalidate()
        }

        return chart
    }

    private fun getPieView(item: DashboardBaseChartItem, binding: ChartItemBinding): View {

        val map = item.values.map { value ->
            PieEntry(value.toFloat())
        }
        val currentNightMode =
            binding.root.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        val pieDataSet = PieDataSet(map, "").apply {
            setDrawValues(false)
            colors = getChartColors(isNightTheme, item.colors, item.colorsDark, color)
            valueTextColor = if (isNightTheme) Color.WHITE else Color.BLACK
            valueTextSize = 12f
        }

        val chart = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.pie_chart_item, null)

        val pieData = PieData(pieDataSet)

        chart.findViewById<PieChart>(R.id.pieChart).apply {
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            description = Description().apply {
                text = ""
            }
            isDrawHoleEnabled = false
            legend.isEnabled = false
            isRotationEnabled = false
            data = pieData
            notifyDataSetChanged()
            invalidate()
        }
        return chart
    }

    private fun getChartColors(
        isNightMode: Boolean,
        colors: List<String>?,
        colorsDark: List<String>?,
        defaultColor: Int
    ): MutableList<Int> {

        return when {
            isNightMode && !colorsDark.isNullOrEmpty() ->
                colorsDark.map { Color.parseColor(it) }.toMutableList()
            !colors.isNullOrEmpty() -> colors.map { Color.parseColor(it) }.toMutableList()
            else -> Colour.colorSchemeOfType(
                defaultColor,
                Colour.ColorScheme.ColorSchemeComplementary
            ).toMutableList()
        }
    }
}
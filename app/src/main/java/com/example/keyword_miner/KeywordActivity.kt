package com.example.keyword_miner

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.keyword_miner.Model.ItemPeriod
import com.example.keyword_miner.Model.blogData
import com.example.keyword_miner.databinding.ActivityKeywordBinding
import com.example.keyword_miner.databinding.ActivityMainBinding
import com.example.keyword_miner.utils.constant.TAG
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class KeywordActivity : AppCompatActivity() {
    var keywordList = ArrayList<KeywordInfo>()
    var PeriodList =ArrayList<ItemPeriod>()
    var BlogCntList=ArrayList<blogData>()
    private var kbinding  : ActivityKeywordBinding? = null
    private val binding get() = kbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        kbinding = ActivityKeywordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "KeywordActivity-onCreate() called")
        val bundle = intent.getBundleExtra("bundle_array")
       // val bundle_data = intent.getBundleExtra("bundle_array_data")
        val bundle_blogcnt = intent.getBundleExtra("bundle_array_blogcnt")


        if (bundle != null) {
            keywordList= bundle.getSerializable("array_list") as ArrayList<KeywordInfo>
            Log.d(TAG, "KeywordActivity-onCreate()-Rel called ${keywordList.get(0)}")

            binding.keyword.text=keywordList.get(0).relKeyword
            binding.pcClick.text=keywordList.get(0).monthlyPcQcCnt
            binding.moClick.text=keywordList.get(0).monthlyMobileQcCnt

        }
        if (bundle_blogcnt!= null) {
            if (bundle != null) {
                BlogCntList= bundle.getSerializable("blogcnt_array_list") as ArrayList<blogData>
            }
            Log.d(TAG, "KeywordActivity-onCreate()-Rel called ${keywordList.get(0)}")
            binding.totalBlog.text=BlogCntList.get(0).total

        }


//        if (bundle_data != null) {
//            PeriodList= bundle_data.getSerializable("data_array_list") as ArrayList<ItemPeriod>
//            Log.d(TAG, "Recycler_view-onCreate()-graph called${PeriodList}")
//
//        }
//        setChartView(binding)
    }
    private fun setChartView(view: ActivityKeywordBinding?) {
        var chartWeek = binding.chartWeek
        setWeek(chartWeek)

    }
    private fun initBarDataSet(barDataSet: BarDataSet) {
        //Changing the color of the bar
        barDataSet.color = Color.parseColor("#304567")
        //Setting the size of the form in the legend
        barDataSet.formSize = 15f
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false)
        //setting the text size of the value of the bar
        barDataSet.valueTextSize = 8f
    }

    private fun setWeek(barChart: BarChart) {
        initBarChart(barChart)

        barChart.setScaleEnabled(false) //Zoom In/Out

        val valueList : ArrayList<Double> = PeriodList[0].rate
        //val entries: ArrayList<String> = PeriodList[0].period
        val entries: ArrayList<BarEntry> = ArrayList()
        val title = PeriodList[0].title

        //input data

        for (i in 0 until valueList.size) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }


        val barDataSet = BarDataSet(entries, title)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
    }

    private fun initBarChart(barChart: BarChart) {
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        barChart.setDescription(description)

        //X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)


        //바텀 좌표 값
        val xAxis: XAxis = barChart.getXAxis()
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        xAxis.textColor = Color.RED
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)


        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = Color.RED


        //우측 값 hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = barChart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.textColor = Color.RED


        //바차트의 타이틀
        val legend: Legend = barChart.getLegend()
        //setting the shape of the legend form to line, default square shape
        legend.form = Legend.LegendForm.LINE
        //setting the text size of the legend
        legend.textSize = 11f
        legend.textColor = Color.YELLOW
        //setting the alignment of legend toward the chart
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        //setting the stacking direction of legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false)
    }
}
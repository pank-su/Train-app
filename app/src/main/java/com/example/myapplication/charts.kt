package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlin.random.Random

class charts : AppCompatActivity() {
     lateinit var chart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charts)
        findViewById<RadioGroup>(R.id.radiogroup).setOnCheckedChangeListener{_, checkedId ->
            if (checkedId == R.id.radioButton){
                modifyData_first()
            } else{
                modifyData_second()
            }
        }
        chart = findViewById(R.id.chart)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        chart.axisLeft.setDrawLabels(false)
        chart.axisRight.setDrawLabels(false)
        chart.setFitBars(true)
        chart.setTouchEnabled(false)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        findViewById<RadioButton>(R.id.radioButton).isChecked = true
    }
    fun modifyData_first(){
        val data: ArrayList<BarEntry> = arrayListOf()
        val names: ArrayList<String> = arrayListOf()
        val numbers_for_labels: ArrayList<String> = arrayListOf()
        var index = 0
        for (el in intent.getIntArrayExtra("populations")?.toList()?.sortedBy { a -> -a }
            ?.slice(0..4)!!) { // Проходим по первым 5 большим странам заполняя список столбцов графа
            // и заполняя список их имён, всю информации берём из intent где её передали в прошлом activity
            data.add(BarEntry(index.toFloat(), el.toFloat()))
            intent.getIntArrayExtra("populations")?.toList()?.indexOf(el)?.let {
                intent.getStringArrayExtra("names")?.get(it)
                    ?.let { names.add(it) }
            }
            numbers_for_labels.add((index + 1).toString())
            index += 1
        }
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(numbers_for_labels)
        for (i in 0..4) {
            chart.description.text += i.toString() + " - " + names[i] + "\n"
        }
        //chart.description.setPosition(3f,-3f)
        val dataset = BarDataSet(data, "BarDataSet")
        dataset.valueTextSize = 13f
        chart.data = BarData(dataset)
        chart.invalidate()

    }
    fun modifyData_second(){
        val data: ArrayList<BarEntry> = arrayListOf()
        val names: ArrayList<String> = arrayListOf()
        val numbers_for_labels: ArrayList<String> = arrayListOf()
        var index = 0
        for (i in 0..4) {
            var el = intent.getIntArrayExtra("populations")?.toList()?.get(Random.nextInt(intent.getIntArrayExtra("populations")?.size!!))!!
            data.add(BarEntry(index.toFloat(), el.toFloat()))
            intent.getIntArrayExtra("populations")?.toList()?.indexOf(el)?.let {
                intent.getStringArrayExtra("names")?.get(it)
                    ?.let { names.add(it) }
            }
            numbers_for_labels.add((index + 1).toString())
            index += 1
        }
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(numbers_for_labels)
        for (i in 0..4) {
            chart.description.text += i.toString() + " - " + names[i] + "\n"
        }
        //chart.description.setPosition(3f,-3f)
        val dataset = BarDataSet(data, "BarDataSet")
        dataset.valueTextSize = 13f
        chart.data = BarData(dataset)
        chart.invalidate()
    }
}
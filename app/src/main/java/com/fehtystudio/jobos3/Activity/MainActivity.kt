package com.fehtystudio.jobos3.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import com.fehtystudio.jobos3.Adapter.RecyclerViewAdapter
import com.fehtystudio.jobos3.Data.ApiJobData
import com.fehtystudio.jobos3.Data.JobData
import com.fehtystudio.jobos3.Interface.MyInterface
import com.fehtystudio.jobos3.R
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        getJobData()
    }

    private fun getJobData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://78.140.221.46")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyInterface::class.java)

        retrofit.getJobData().enqueue(object : Callback<List<ApiJobData>> {
            override fun onResponse(call: Call<List<ApiJobData>>?, response: Response<List<ApiJobData>>?) {
                val adapter = RecyclerViewAdapter(this@MainActivity)
                for (i in 0 until response!!.body()!!.size) {
                    val response = response.body()!![i]
                    adapter.addItem(JobData(response.title, response.description, response.url, response.salary))
                }
                recyclerView.adapter = adapter

                var checkBoxState = false
                checkBox.setOnClickListener {
                    if (!checkBoxState) {
                        recyclerView.removeAllViewsInLayout()
                        adapter.salaryFilter()
                        checkBoxState = true
                    } else if (checkBoxState) {
                        getJobData()
                        checkBoxState = false
                    }
                }

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText!!.isEmpty()) getJobData()
                        else {
                            recyclerView.removeAllViewsInLayout()
                            adapter.wordsFilter(newText.toString())
                        }
                        return true
                    }
                })

                searchView.setOnCloseListener {
                    getJobData()
                    true
                }
            }

            override fun onFailure(call: Call<List<ApiJobData>>?, t: Throwable?) = Unit
        })
    }
}

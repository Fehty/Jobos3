package com.fehtystudio.jobos3.Adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fehtystudio.jobos3.Activity.MainActivity
import com.fehtystudio.jobos3.Data.JobData
import com.fehtystudio.jobos3.R

class RecyclerViewAdapter(var context: MainActivity?, var list: MutableList<JobData>? = mutableListOf()) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_for_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun salaryFilter() {
        val secondArray = mutableListOf<JobData>()
        list!!.forEach { if (!it.salary.equals("None")) secondArray.add(JobData(it.title, it.description, it.link, it.salary)) }
        list!!.clear()
        secondArray.forEach { addItem(JobData(it.title, it.description, it.link, it.salary)) }
    }

    fun wordsFilter(text: String) {
        val secondArray = mutableListOf<JobData>()
        list!!.forEach { if (it.title!!.toLowerCase().contains(text) or it.description!!.toLowerCase().contains(text) or it.salary!!.toLowerCase().contains(text) or it.title!!.contains(text) or it.description!!.contains(text) or it.salary!!.contains(text)) secondArray.add(JobData(it.title, it.description, it.link, it.salary)) }
        list!!.clear()
        secondArray.forEach { addItem(JobData(it.title, it.description, it.link, it.salary)) }
    }

    fun addItem(item: JobData) {
        list!!.add(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {

        private val title = view!!.findViewById<TextView>(R.id.title)
        private val description = view!!.findViewById<TextView>(R.id.description)
        private val salary = view!!.findViewById<TextView>(R.id.salary)

        fun bind(jobData: JobData) {

            title.text = jobData.title
            description.text = jobData.description
            salary.text = jobData.salary

            title.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(jobData.link)
                context!!.startActivity(intent)
            }
        }
    }
}

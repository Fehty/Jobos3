package com.fehtystudio.jobos3.Adapter

import android.annotation.SuppressLint
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

class RecyclerViewAdapter(var context: MainActivity?, private var list: MutableList<JobData>? = mutableListOf()) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var listCopy = mutableListOf<JobData>()

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

    fun salaryFilter(checkBoxState: Boolean, currentText: String) {
        list!!.clear()
        val text = currentText.toLowerCase()
        when (checkBoxState) {
            false -> {
                listCopy.forEach {
                    if (it.title!!.toLowerCase().contains(text) or it.description!!.toLowerCase().contains(text) or it.salary!!.toLowerCase().contains(text)) {
                        list!!.add(JobData(it.title, it.description, it.link, it.salary))
                    }
                }
            }
            true -> {
                listCopy.forEach {
                    if (!it.salary.equals("0") and (it.title!!.toLowerCase().contains(text) or it.description!!.toLowerCase().contains(text) or it.salary!!.toLowerCase().contains(text))) {
                        list!!.add(JobData(it.title, it.description, it.link, it.salary))
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    fun addItem(item: JobData) {
        list!!.add(item)
        listCopy.add(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {

        private val title = view!!.findViewById<TextView>(R.id.title)
        private val description = view!!.findViewById<TextView>(R.id.description)
        private val salary = view!!.findViewById<TextView>(R.id.salary)

        @SuppressLint("SetTextI18n")
        fun bind(jobData: JobData) {
            title.text = jobData.title
            if (jobData.salary != "0") salary.text = jobData.salary + " £"
            else salary.text = "Not specified"
            description.text = jobData.description

            title.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(jobData.link)
                context!!.startActivity(intent)
            }
        }
    }
}

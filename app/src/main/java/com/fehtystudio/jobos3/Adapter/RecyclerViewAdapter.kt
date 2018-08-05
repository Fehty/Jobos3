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

class RecyclerViewAdapter(var context: MainActivity?, var list: MutableList<JobData>? = mutableListOf()) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var listCopy = mutableListOf<JobData>()
    private var salaryFilterState = false

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

    fun setDefaultList() {
        list!!.clear()
        listCopy.forEach {
            list!!.add(JobData(it.title, it.description, it.link, it.salary))
        }
        notifyDataSetChanged()
        salaryFilterState = false
    }

    fun salaryFilter(currentText: String, checkBoxState: Boolean) {
        if (currentText.isNotEmpty()) {
            if (checkBoxState == true) {
                salaryFilterState = true
                wordsFilter(currentText)
            } else {
                salaryFilterState = false
                wordsFilter(currentText)
            }
        } else {
            list!!.clear()
            listCopy.forEach {
                if (!it.salary!!.contains("None")) {
                    list!!.add(JobData(it.title, it.description, it.link, it.salary))
                }
            }
            notifyDataSetChanged()
        }
    }

    fun wordsFilter(textItem: String) {
        if (salaryFilterState == true) {
            list!!.clear()
            val text = textItem.toLowerCase()
            for (i in 0 until listCopy.size) {
                if (!listCopy[i].salary!!.contains("None") and (listCopy[i].title!!.toLowerCase().contains(text) or listCopy[i].description!!.toLowerCase().contains(text) or listCopy[i].salary!!.toLowerCase().contains(text))) {
                    list!!.add(JobData(listCopy[i].title, listCopy[i].description, listCopy[i].link, listCopy[i].salary))
                }
            }
            notifyDataSetChanged()
        } else {
            list!!.clear()
            val text = textItem.toLowerCase()
            listCopy.forEach {
                if (it.title!!.toLowerCase().contains(text) or it.description!!.toLowerCase().contains(text) or it.salary!!.toLowerCase().contains(text)) {
                    list!!.add(JobData(it.title, it.description, it.link, it.salary))
                }
            }
            notifyDataSetChanged()
        }
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
            if (jobData.salary != "None") salary.text = jobData.salary + " £"
            else salary.text = jobData.salary
            description.text = jobData.description

            title.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(jobData.link)
                context!!.startActivity(intent)
            }
        }
    }
}

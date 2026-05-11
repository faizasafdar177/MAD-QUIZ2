package com.example.quizapplication2023cs189

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(private val complaints: List<Complaint>) :
    RecyclerView.Adapter<ComplaintAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvRoll: TextView = view.findViewById(R.id.tvRoll)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvPriority: TextView = view.findViewById(R.id.tvPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val complaint = complaints[position]
        holder.tvTitle.text = complaint.title
        holder.tvName.text = complaint.studentName
        holder.tvRoll.text = complaint.rollNumber
        holder.tvCategory.text = complaint.category
        holder.tvPriority.text = complaint.priority

        // Priority color
        val color = when (complaint.priority) {
            "Low"    -> "#4CAF50"
            "Medium" -> "#FF9800"
            "High"   -> "#F44336"
            "Urgent" -> "#9C27B0"
            else     -> "#888888"
        }
        holder.tvPriority.setBackgroundColor(Color.parseColor(color))

        // Click to open detail
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("id", complaint.id)
            intent.putExtra("title", complaint.title)
            intent.putExtra("name", complaint.studentName)
            intent.putExtra("roll", complaint.rollNumber)
            intent.putExtra("category", complaint.category)
            intent.putExtra("priority", complaint.priority)
            intent.putExtra("description", complaint.description)
            intent.putExtra("status", complaint.status)
            intent.putExtra("timestamp", complaint.timestamp)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = complaints.size
}
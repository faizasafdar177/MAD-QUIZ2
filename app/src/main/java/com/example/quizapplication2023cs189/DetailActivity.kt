
package com.example.quizapplication2023cs189
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        // Get data from intent
        val title       = intent.getStringExtra("title") ?: ""
        val name        = intent.getStringExtra("name") ?: ""
        val roll        = intent.getStringExtra("roll") ?: ""
        val category    = intent.getStringExtra("category") ?: ""
        val priority    = intent.getStringExtra("priority") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val status      = intent.getStringExtra("status") ?: "Pending"
        val timestamp   = intent.getLongExtra("timestamp", 0L)

        // Set data to views
        findViewById<TextView>(R.id.tvDetailTitle).text       = title
        findViewById<TextView>(R.id.tvDetailName).text        = name
        findViewById<TextView>(R.id.tvDetailRoll).text        = roll
        findViewById<TextView>(R.id.tvDetailCategory).text    = category
        findViewById<TextView>(R.id.tvDetailStatus).text      = status
        findViewById<TextView>(R.id.tvDetailDescription).text = description

        // Priority with color
        val priorityView = findViewById<TextView>(R.id.tvDetailPriority)
        priorityView.text = priority
        val color = when (priority) {
            "Low"    -> "#4CAF50"
            "Medium" -> "#FF9800"
            "High"   -> "#F44336"
            "Urgent" -> "#9C27B0"
            else     -> "#888888"
        }
        priorityView.setTextColor(Color.parseColor(color))

        // Format date
        val date = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(timestamp))
        findViewById<TextView>(R.id.tvDetailDate).text = date
    }
}
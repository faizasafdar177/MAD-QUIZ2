package com.example.quizapplication2023cs189

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private val db = FirebaseFirestore.getInstance()
    private val complaintList = mutableListOf<Complaint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        tvEmpty = findViewById(R.id.tvEmpty)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Test Firebase connection
        db.collection("test")
            .document("connection")
            .set(mapOf("status" to "connected"))
            .addOnSuccessListener {
                Log.d("FIREBASE", "✅ Firebase Connected!")
                Toast.makeText(this, "✅ Firebase Connected!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "❌ Failed: ${it.message}")
                Toast.makeText(this, "❌ Firebase Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }

        // Add Complaint button
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddComplaintActivity::class.java))
        }

        loadComplaints()
    }

    override fun onResume() {
        super.onResume()
        loadComplaints()
    }

    private fun loadComplaints() {
        tvEmpty.text = "Loading..."
        tvEmpty.visibility = View.VISIBLE

        db.collection("complaints")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                complaintList.clear()
                for (doc in documents) {
                    val complaint = doc.toObject(Complaint::class.java).copy(id = doc.id)
                    complaintList.add(complaint)
                }
                recyclerView.adapter = ComplaintAdapter(complaintList)

                if (complaintList.isEmpty()) {
                    tvEmpty.text = "No complaints yet. Tap + Add to submit one."
                    tvEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE", "Load failed: ${e.message}")
                tvEmpty.text = "❌ Failed to load: ${e.message}"
                tvEmpty.visibility = View.VISIBLE
            }
    }
}
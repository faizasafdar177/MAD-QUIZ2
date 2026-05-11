package com.example.quizapplication2023cs189

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddComplaintActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_complaint)

        val etName        = findViewById<EditText>(R.id.etName)
        val etRoll        = findViewById<EditText>(R.id.etRoll)
        val etTitle       = findViewById<EditText>(R.id.etTitle)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val spinnerCat    = findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerPri    = findViewById<Spinner>(R.id.spinnerPriority)
        val btnSubmit     = findViewById<Button>(R.id.btnSubmit)
        val btnBack       = findViewById<TextView>(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        // Category options
        val categories = listOf("IT", "Library", "Transport", "Hostel", "Accounts", "Examination", "Cafeteria", "Administration")
        spinnerCat.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        // Priority options
        val priorities = listOf("Low", "Medium", "High", "Urgent")
        spinnerPri.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)

        btnSubmit.setOnClickListener {
            val name        = etName.text.toString().trim()
            val roll        = etRoll.text.toString().trim()
            val title       = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val category    = spinnerCat.selectedItem.toString()
            val priority    = spinnerPri.selectedItem.toString()

            // Validate all fields
            if (name.isEmpty() || roll.isEmpty() || title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSubmit.isEnabled = false
            btnSubmit.text = "Submitting..."

            val complaint = Complaint(
                studentName = name,
                rollNumber  = roll,
                title       = title,
                category    = category,
                priority    = priority,
                description = description,
                status      = "Pending",
                timestamp   = System.currentTimeMillis()
            )

            // Save to Firestore
            db.collection("complaints")
                .add(complaint)
                .addOnSuccessListener {
                    Toast.makeText(this, "Complaint submitted successfully!", Toast.LENGTH_SHORT).show()
                    // Clear form
                    etName.setText("")
                    etRoll.setText("")
                    etTitle.setText("")
                    etDescription.setText("")
                    btnSubmit.isEnabled = true
                    btnSubmit.text = "Submit Complaint"
                    finish() // Go back to list
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnSubmit.isEnabled = true
                    btnSubmit.text = "Submit Complaint"
                }
        }
    }
}
package com.example.quizapplication2023cs189

data class Complaint(
    val id: String = "",
    val studentName: String = "",
    val rollNumber: String = "",
    val title: String = "",
    val category: String = "",
    val priority: String = "",
    val description: String = "",
    val status: String = "Pending",
    val timestamp: Long = System.currentTimeMillis()
)
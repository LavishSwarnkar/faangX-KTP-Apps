package com.faangx.ktp.basics

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.math.roundToInt

// Define the dark theme colors
private val DarkColorPalette = darkColors(
    primary = Color(0xFF6200EE),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

// Subject names
val subjects = listOf("Mathematics", "Science", "English", "History", "Computer")

// Data class for Student
data class Student(
    val name: String,
    val marks: MutableMap<String, Int> = mutableMapOf(),
    var total: Int = 0,
    var percentage: Double = 0.0,
    var grade: String = "",
    var rank: Int = 0
)

// Calculate grade based on percentage
fun calculateGrade(percentage: Double): String {
    return when {
        percentage >= 90 -> "A+"
        percentage >= 80 -> "A"
        percentage >= 70 -> "B+"
        percentage >= 60 -> "B"
        percentage >= 50 -> "C"
        percentage >= 40 -> "D"
        else -> "F"
    }
}

// Get rank emoji
fun getRankEmoji(rank: Int): String {
    return when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> ""
    }
}

// Application's main entry point
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Student Grade Calculator",
        state = rememberWindowState(width = 1200.dp, height = 800.dp)
    ) {
        App()
    }
}

@Composable
fun App() {
    // List of students
    val students = remember { mutableStateListOf<Student>() }

    // New student name input
    var newStudentName by remember { mutableStateOf("") }

    // Recalculate totals, percentages, grades, and ranks whenever students change
    LaunchedEffect(students.size) {
        if (students.isNotEmpty()) {
            // Calculate totals and percentages for each student
            students.forEach { student ->
                student.total = student.marks.values.sum()
                student.percentage = student.total / (subjects.size * 100.0) * 100
                student.percentage = (student.percentage * 100).roundToInt() / 100.0
                student.grade = calculateGrade(student.percentage)
            }

            // Sort students by total marks and assign ranks
            val sortedStudents = students.sortedByDescending { it.total }
            sortedStudents.forEachIndexed { index, student ->
                val studentIndex = students.indexOf(student)
                if (studentIndex != -1) {
                    students[studentIndex] = student.copy(rank = index + 1)
                }
            }
        }
    }

    MaterialTheme(
        colors = DarkColorPalette
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // App Header
                Text(
                    text = "Student Grade Calculator",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Add new student section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colors.surface)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = newStudentName,
                        onValueChange = { newStudentName = it },
                        label = { Text("Enter Student Name") },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.primary,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (newStudentName.trim().isNotEmpty()) {
                                val newStudent = Student(
                                    name = newStudentName.trim(),
                                    marks = subjects.associateWith { 0 }.toMutableMap()
                                )
                                students.add(newStudent)
                                newStudentName = ""
                            }
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Student",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }

                // Students Table Header
                if (students.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.primaryVariant)
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Student",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.weight(1.5f)
                        )

                        subjects.forEach { subject ->
                            Text(
                                text = subject,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Text(
                            text = "Total",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.7f)
                        )

                        Text(
                            text = "%",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.7f)
                        )

                        Text(
                            text = "Grade",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.7f)
                        )

                        Text(
                            text = "Rank",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.7f)
                        )

                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

                // Students List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(students) { student ->
                        val studentIndex = students.indexOf(student)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (studentIndex % 2 == 0) MaterialTheme.colors.surface
                                    else MaterialTheme.colors.surface.copy(alpha = 0.7f)
                                )
                                .padding(8.dp)
                        ) {
                            // Student Name
                            Text(
                                text = student.name,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1.5f)
                            )

                            // Subject Marks
                            subjects.forEach { subject ->
                                SubjectMarkInput(
                                    currentMark = student.marks[subject] ?: 0,
                                    onMarkChange = { newMark ->
                                        // Update the mark for this subject
                                        val updatedStudent = students[studentIndex]
                                        updatedStudent.marks[subject] = newMark
                                        students[studentIndex] = updatedStudent
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Total
                            Text(
                                text = student.total.toString(),
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(0.7f)
                            )

                            // Percentage
                            Text(
                                text = String.format("%.1f", student.percentage),
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(0.7f)
                            )

                            // Grade
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(0.7f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        when (student.grade) {
                                            "A+", "A" -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                            "B+", "B" -> Color(0xFF2196F3).copy(alpha = 0.2f)
                                            "C" -> Color(0xFFFFC107).copy(alpha = 0.2f)
                                            "D" -> Color(0xFFFF9800).copy(alpha = 0.2f)
                                            else -> Color(0xFFF44336).copy(alpha = 0.2f)
                                        }
                                    )
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = student.grade,
                                    fontWeight = FontWeight.Bold,
                                    color = when (student.grade) {
                                        "A+", "A" -> Color(0xFF4CAF50)
                                        "B+", "B" -> Color(0xFF2196F3)
                                        "C" -> Color(0xFFFFC107)
                                        "D" -> Color(0xFFFF9800)
                                        else -> Color(0xFFF44336)
                                    }
                                )
                            }

                            // Rank
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.weight(0.7f)
                            ) {
                                Text(
                                    text = if (student.rank <= 3) getRankEmoji(student.rank) else student.rank.toString(),
                                    fontSize = if (student.rank <= 3) 20.sp else 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Delete Button
                            IconButton(
                                onClick = { students.removeAt(studentIndex) },
                                modifier = Modifier.weight(0.5f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove Student",
                                    tint = MaterialTheme.colors.error
                                )
                            }
                        }
                    }
                }

                // Empty state
                if (students.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(32.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No students added yet",
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add a student using the input field above",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
                            )
                        }
                    }
                }

                // Footer with class statistics if there are students
                if (students.isNotEmpty()) {
                    val classAverage = students.map { it.percentage }.average()
                    val highestScore = students.maxOf { it.total }
                    val lowestScore = students.minOf { it.total }

                    Card(
                        backgroundColor = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            StatisticItem(title = "Total Students", value = students.size.toString())
                            StatisticItem(title = "Class Average", value = String.format("%.1f%%", classAverage))
                            StatisticItem(title = "Highest Score", value = "$highestScore/${subjects.size * 100}")
                            StatisticItem(title = "Lowest Score", value = "$lowestScore/${subjects.size * 100}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectMarkInput(
    currentMark: Int,
    onMarkChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var markText by remember { mutableStateOf(currentMark.toString()) }

    OutlinedTextField(
        value = markText,
        onValueChange = { text ->
            // Only allow numbers and ensure it's within 0-100 range
            val filteredText = text.filter { it.isDigit() }
            val newValue = filteredText.ifEmpty { "0" }.toInt().coerceIn(0, 100)
            markText = newValue.toString()
            onMarkChange(newValue)
        },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ),
        modifier = modifier
            .padding(horizontal = 4.dp)
            .height(56.dp)
    )
}

@Composable
fun StatisticItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
    }
}
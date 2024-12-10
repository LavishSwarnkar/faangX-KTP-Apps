package com.faangx.ktp

import com.faangx.ktp.util.getResFile
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration


class Data(
    val date: String,
    val map: Map<String, String>
)

data class Output(
    val date: String,
    val totalDuration: TotalDuration,
    val map: Map<String, String>
)

data class TotalDuration(
    val startTime: String,
    val endTime: String
)

data class Subject(
    val name: String,
    val startTime: String,
    val endTime: String
)

fun main() {
    val input = getResFile("in.txt").readText()
    val inputLine = input.split("\n---\n")
    val size = inputLine[0]
    val info = inputLine.drop(1).map {
        val newData = it.split("\n")
        Data(
            date = newData[0],
            map = newData.drop(1).associate {
                val index = it.indexOf(" ")
                it.substring(0, endIndex = index) to it.substring(index)
            }

        )
    }

    val output = info.map {
        val duration = getTotalDuration(it.map)
        Output(
            date = it.date,
            totalDuration = duration,
            map = getSubjectWiseTime(it.map)
        )
    }

    println(
        output
    )

}

fun getSubjectWiseTime(map: Map<String, String>): Map<String, String> {

    val subjects = map.entries.map {
        Subject(
            name = it.key,
            startTime = it.value,
            endTime = it.value
        )
    }
    val mapOfSubjects = subjects.map {
        Pair(first = it.name,
            second = it.startTime)
    }
    return mapOfSubjects.toMap()
}

fun getTotalDuration(map: Map<String, String>): TotalDuration {
    val startTime = map.values.first()
    val endTime = map.values.last()
    val formatter = DateTimeFormatter.ofPattern("h:mma")

    // Parse two time strings into LocalTime objects
    val time1 = LocalTime.parse(startTime, formatter)
    val time2 = LocalTime.parse(endTime, formatter)

    // Calculate the duration between the two times
    val duration = Duration.between(time1, time2)

    // Convert the duration to hours and minutes
    val hours = duration.toHours()
    val minutes = duration.minusHours(hours).toMinutes()

    return TotalDuration(
        startTime = startTime,
        endTime = endTime
    )
}

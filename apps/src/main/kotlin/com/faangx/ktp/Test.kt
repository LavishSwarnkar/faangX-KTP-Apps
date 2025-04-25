package com.faangx.ktp

val names = listOf(
    "Bear",
    "Cat",
    "Cow",
    "Crow",
    "Deer",
    "Dog",
    "Dolphin",
    "Eagle",
    "Elephant",
    "Fox",
    "Giraffe",
    "Hawk",
    "Horse",
    "Kangaroo",
    "Lion",
    "Monkey",
    "Owl",
    "Panda",
    "Parrot",
    "Peacock",
    "Penguin",
    "Pigeon",
    "Rabbit",
    "Seagull",
    "Sparrow",
    "Squirrel",
    "Swan",
    "Tiger",
    "Vulture",
    "Wolf",
    "Woodpecker",
)

fun main() {
    println(
        binarySearch(names, "Em")
    )
}

fun binarySearch(list: List<String>, x: String): Int {
    var start = 0;
    var end = list.lastIndex

    while (start <= end) {
        val midIndex = (start + end) / 2
        println("checking list[($start + $end)/2 = $midIndex]")
        when {
            // x found
            list[midIndex] == x -> return midIndex

            // x might be in first half
            list[midIndex] > x -> end = midIndex - 1

            // x might be in second half
            else -> start = midIndex + 1
        }
    }

    // x not found
    return -1
}
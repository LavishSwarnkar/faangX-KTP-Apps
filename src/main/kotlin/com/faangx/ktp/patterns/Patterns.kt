package com.faangx.ktp.patterns

val patterns = """
P1 :
*
**
***
****
*****

P2 :
1
12
123
1234
12345

P3 :
1
22
333
4444
55555

P4 :
5
44
333
2222
11111

P5 :
A
PP
PPP
LLLL
EEEEE

P6 :
A
AP
APP
APPL
APPLE

P7 :
A
BC
DEF
GHIJ
KLMNO

P8 :
    *
   ***
  *****
 *******
*********

P9 :
    2
   444
  66666
 8888888
AAAAAAAAA

P10 :
    *
   *-*
  *---*
 *-----*
*********

P11 :
    *
   *-*
  *-*-*
 *-*-*-*
*-*-*-*-*

P12 :
    A
   PPP
  PPPPP
 LLLLLLL
EEEEEEEEE

P13 :
*-----**********-----*
**-----********-----**
***-----******-----***
****-----****-----****
*****-----**-----*****
""".trimIndent()

fun getPatterns(): List<String> {
    val lines = patterns.split("\n")
        .filterIndexed { index, _ ->
            index != 0 && (index + 1) % 7 != 0 && index % 7 != 0
        }
        .chunked(5)
        .map { it.joinToString("\n") }
    return lines
}
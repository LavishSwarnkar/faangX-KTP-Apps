package com.faangx.ktp.patterns

val patterns = """
BL :
*
**
***
****
*****

BR :
    *
   **
  ***
 ****
*****

TLN :
55555
4444
333
22
1

TRSR :
54321
 5432
  543
   54
    5

PU1 :
    *
   ***
  *****
 *******
*********

PU2 :
    *
   *-*
  *---*
 *-----*
*********

PD3 :
*-*-*-*-*
 *-*-*-*
  *-*-*
   *-*
    *
""".trimIndent()

val other = """
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

fun getPatterns(): Map<String, String> {
    val map =  patterns.split("\n")
        .filterIndexed { index, _ ->
            (index + 1) % 7 != 0
        }
        .chunked(6)
        .associate { list ->
            list.first().replace(" :", "") to list.takeLast(5).joinToString("\n")
        }
    return map
}
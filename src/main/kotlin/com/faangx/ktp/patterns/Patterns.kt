package com.faangx.ktp.patterns

val patterns = """
BL.LC.* :
*
**
***
****
*****

BR.LC.* :
    *
   **
  ***
 ****
*****

TLN.L :
55555
4444
333
22
1

TRSR.L :
54321
 5432
  543
   54
    5

PU1.LC.* :
    *
   ***
  *****
 *******
*********

PU2.L2C.*- :
    *
   *-*
  *---*
 *-----*
*********

PD3.L2C.*- :
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



fun getPatterns(): List<Pattern> {
    val list =  patterns.split("\n")
        .filterIndexed { index, _ ->
            (index + 1) % 7 != 0
        }
        .chunked(6)
        .map { list ->
            val meta = list.first().replace(" :", "")
            val sample = list.takeLast(5).joinToString("\n")

            val parts = meta.split(".")

            val code = parts[0]
            val typeAbbr = parts[1]
            val defaultCustomization = parts.getOrNull(2)

            Pattern(
                code = code,
                type = Pattern.Type.entries.find { it.abbr == typeAbbr }
                    ?: error("Invalid pattern type : $typeAbbr"),
                sample = sample,
                defaultCustomization = defaultCustomization
            )
        }
    return list
}
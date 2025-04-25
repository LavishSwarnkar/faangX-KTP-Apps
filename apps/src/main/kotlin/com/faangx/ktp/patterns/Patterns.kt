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

P1.L2C.*- :
*----
-*---
--*--
---*-
----*

P2.L2C.*- :
----*
---*-
--*--
-*---
*----

P3.L2C.*- :
----*----
---*-*---
--*---*--
-*-----*-
*-------*

P4.L2C.*- :
*-----**********-----*
**-----********-----**
***-----******-----***
****-----****-----****
*****-----**-----*****

P5.L2C.*+ :
+  *  +
 + * +
  +*+
*******
  +*+
 + * +
+  *  +

P6.W.APPLE :
A
PP
PPP
LLLL
EEEEE

P7.W.APPLE :
A
AP
APP
APPL
APPLE

P8.W.APPLE :
    A
   PPP
  PPPPP
 LLLLLLL
EEEEEEEEE

P9.L :
A
BC
DEF
GHIJ
KLMNO

P10.L :
    1
   222
  33333
 4444444
555555555
""".trimIndent()



fun getPatterns(): List<Pattern> {
    val list =  patterns.split("\n\n")
        .map { it.split("\n") }
        .map { list ->
            val meta = list.first().replace(" :", "")
            val sample = list.drop(1).joinToString("\n")

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
package com.faangx.ktp.basics.intList.model

interface IntListOpsVariant {
    class Single(val op: IntListOp): IntListOpsVariant
    class All(val ops: List<IntListOp>): IntListOpsVariant
}
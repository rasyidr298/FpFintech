package id.fp.core.utils.extensions

import kotlin.math.ln
import kotlin.math.pow

fun Float.withNumberSuffix(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format(
            "%.1f %c",
            this / 1000.0.pow(exp.toDouble()),
            "kMBTPE"[exp - 1]
    )
}
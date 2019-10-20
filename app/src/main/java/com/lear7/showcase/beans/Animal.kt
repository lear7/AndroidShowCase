package com.lear7.showcase.beans

import android.content.Context
import org.jetbrains.anko.toast

class AnimalMain(context: Context, var name: String) {
    init {
        context.toast("This is an $name Animal.")
    }

    constructor(context: Context, name: String, gender: Int = 0) : this(context, name) {
        var sexName: String = if (gender == 0) "Female" else "Male"
        context.toast("This is an $gender $name")
    }

    companion object {
        val MALE = 1
        val FEMALE = 0
        val UNKNOWN = -1

        fun getSex(genderName: String): Int {
            var gender: Int = when (genderName) {
                "Male", "公", "雄" -> MALE
                "Female", "母", "雌" -> FEMALE
                else -> UNKNOWN
            }
            return gender
        }
    }
}
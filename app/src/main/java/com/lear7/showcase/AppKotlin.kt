package com.lear7.showcase

import android.app.Application
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AppKotlin : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    // 单例化的第三种方式，自定义一个非空且职能一次性赋值的委托属性
    companion object {
        // 单例化第二种
        // private var INSTANCE: AppKotlin by Delegates.notNull()
        private var INSTANCE: AppKotlin by NotNullSingleValueVar()

        fun instance() = INSTANCE
    }

    // 定义一个属性管理类，进行非空和重复赋值判断
    private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("Application not intilized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value else throw IllegalStateException("Application already initialized")
        }
    }
}
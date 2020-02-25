package com.lear7.showcase.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import java.io.Serializable

data class User(var firstName: ObservableField<String>, var lastName: ObservableField<String>) : BaseObservable(), Serializable {
}
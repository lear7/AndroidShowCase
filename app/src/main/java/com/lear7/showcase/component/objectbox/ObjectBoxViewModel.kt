package com.lear7.showcase.component.objectbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ObjectBoxViewModel : ViewModel() {

    private var db: Box<User> = ObjectBox.boxStore.boxFor()

    private val mUserList = MutableLiveData<List<User>>()

    fun getUserList(): LiveData<List<User>> = mUserList

    init {
        GlobalScope.launch {
            mUserList.postValue(db.all)
        }
    }

    fun addUser(user: User) =
        GlobalScope.launch {
            db.put(user)
            mUserList.postValue(db.all)
        }

}

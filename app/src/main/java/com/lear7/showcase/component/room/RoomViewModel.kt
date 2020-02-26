package com.lear7.showcase.component.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.lear7.showcase.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    private var db: AppDatabase = Room.databaseBuilder(
        App.getInstance(),
        AppDatabase::class.java, "UserDB"
    ).build()

    private val mUserList = MutableLiveData<List<User>>()

    fun getUserList(): LiveData<List<User>> = mUserList

    init {
        GlobalScope.launch {
            mUserList.postValue(db.userDao().all)
        }
    }

    fun addUser(user: User) =
        GlobalScope.launch {
            db.userDao().insert(user)
            mUserList.postValue(db.userDao().all)
        }

}

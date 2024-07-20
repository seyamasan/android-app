package com.example.seyamaapplication.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seyamaapplication.R
import com.example.seyamaapplication.main.model.MainActivityModel
import com.example.seyamaapplication.room.MainActivityDataBase
import com.example.seyamaapplication.room.MainDao
import com.example.seyamaapplication.room.MainEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: MainDao
    private val _mainActivityModel = MainActivityModel()
    private val _editTextInput = MutableLiveData<String>()
    val editTextInput: LiveData<String>
        get() = _editTextInput
    private val _saveMsg = MutableLiveData<String>()
    val saveMsg: LiveData<String>
        get() = _saveMsg

    init {
        val db = MainActivityDataBase.buildDatabase(application) // DBにアクセスするclassで一度だけDBをビルドする
        dao = db.mainDao() // 使用するDaoを指定
    }

    fun initEditTextSetValue() {
        viewModelScope.launch {
            _mainActivityModel.setEditTextInput(selectSaveText())
        }
    }

    fun setEditText(input: String) {
        _editTextInput.value = input
        _mainActivityModel.setEditTextInput(input)
    }

    fun setEditTextDropLast(str: String) {
        _mainActivityModel.setEditTextInput(str)
    }

    fun getModelEditText(): String {
        return _mainActivityModel.getEditTextInput()
    }

    fun saveBtnTapped() {
        if (_mainActivityModel.getEditTextInput() != "") {
            viewModelScope.launch(Dispatchers.IO) {
                val data = dao.selectAllData()
                if (data.isEmpty()) {
                    insertDataBase()
                    _saveMsg.postValue(getApplication<Application>().getString(R.string.main_activity_save_msg))
                } else {
                     // データが存在していたら上書き
                    dao.updateColumnValue(
                        MainEntity(
                            id = data[0].id, // 自動的にIDを入れるときは0を入れる
                            saveText = _mainActivityModel.getEditTextInput()
                        )
                    )
                    _saveMsg.postValue(getApplication<Application>().getString(R.string.main_activity_update_msg))
                }
            }
        }
    }

    // DBのデータ全部取得
    private suspend fun selectSaveText(): String {
        return withContext(Dispatchers.IO) {
            val data = dao.selectAllData()
            (if (data.isEmpty()) {
                ""
            } else {
                data[0].saveText // 最初のデータを返す
            })!!
        }
    }

    // DBにデータを保存
    private fun insertDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(
                MainEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    saveText = _mainActivityModel.getEditTextInput()
                )
            )
        }
    }

    // DBの内容を全て消す
    private fun deleteAllDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
}
package com.example.seyamaapplication.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){

    private val _editTextInput = MutableLiveData<String>()
    val editTextInput: LiveData<String>
        get() = _editTextInput


    fun setEditText(input: String) {
        _editTextInput.value = input
    }

    fun saveBtnTapped() {
        print("OK")
    }
}
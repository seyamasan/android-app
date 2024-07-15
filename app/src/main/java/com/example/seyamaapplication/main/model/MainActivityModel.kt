package com.example.seyamaapplication.main.model

class MainActivityModel {
    private var _editTextInput: String = ""

    fun setEditTextInput(value: String) {
        _editTextInput = value
    }

    fun getEditTextInput(): String {
        return _editTextInput
    }
}
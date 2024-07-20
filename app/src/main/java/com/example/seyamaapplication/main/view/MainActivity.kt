package com.example.seyamaapplication.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.seyamaapplication.R
import com.example.seyamaapplication.databinding.ActivityMainBinding
import com.example.seyamaapplication.main.validate.ValidateString
import com.example.seyamaapplication.main.viewmodel.MainActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var validateString: ValidateString

    private val _mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = _mainActivityViewModel

        validateString = ValidateString()
        setupListener()
        binding.viewModel?.initEditTextSetValue()
        setupSavedEditText()
    }

    private fun setupListener() {
        binding.editText.addTextChangedListener {
            binding.viewModel?.setEditText(it.toString())
        }

        // LiveDataを監視して、変更があった場合に処理を実行する
        binding.viewModel?.editTextInput?.observe(this, Observer { text ->
            if (validateString.validateSymbol(text)) {
                val strDropLast = binding.editText.text.dropLast(1).toString()
                binding.editText.setText(strDropLast)
                binding.viewModel?.setEditTextDropLast(strDropLast)
                showMessageText(getString(R.string.main_activity_edit_text_invalid))
            }
        })

        binding.viewModel?.saveMsg?.observe(this, Observer { text ->
            showMessageText(text)
        })
    }

    private fun setupSavedEditText() {
        binding.viewModel?.viewModelScope?.launch {
            delay(1000) // DBとの兼ね合いで無理やり遅らせている
            val value = binding.viewModel?.getModelEditText()
            if (value?.isNotEmpty() == true) {
                runOnUiThread {
                    binding.editText.setText(value)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showMessageText(message: String) {
        binding.messageText.text = message
        binding.messageText.visibility = View.VISIBLE
        binding.viewModel?.viewModelScope?.launch {
            delay(5000) // 5秒間の遅延
            runOnUiThread {
                binding.messageText.text = ""
                binding.messageText.visibility = View.INVISIBLE
            }
        }
    }
}
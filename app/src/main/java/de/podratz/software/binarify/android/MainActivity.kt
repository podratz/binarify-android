package de.podratz.software.binarify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import de.podratz.software.binarify.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val copyButton = binding.copyButton
        copyButton.setOnClickListener {
            val confirmation = resources.getString(R.string.copy_confirmation)
            val confirmationToast = Toast.makeText(this, confirmation, Toast.LENGTH_SHORT)
            confirmationToast.show()
        }

        val outputTextView = binding.outputTextView

        val inputEditText = binding.inputEditText
        inputEditText.doOnTextChanged { charSequence, _, _, _ ->
            val input = charSequence.toString()
            outputTextView.text = toBinary(input)
        }
    }

    private fun toBinary(text: String) : String {
        if (text.isEmpty()) return ""
        val byteArray = text.toByteArray(Charsets.UTF_8)
        val bitArray = byteArray.map { it.toString(2) }
        return bitArray.reduce { acc, char -> acc + char }
    }
}
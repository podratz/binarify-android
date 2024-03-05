package de.podratz.software.binarify.android

import android.R.attr
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import de.podratz.software.binarify.android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        val inputEditText = binding.inputEditText
        val outputTextView = binding.outputTextView
        val copyButton = binding.copyButton

        setContentView(view)

        inputEditText.doOnTextChanged { charSequence, _, _, _ ->
            val input = charSequence.toString()
            outputTextView.text = toBinary(input)
        }

        copyButton.setOnClickListener {
            val outputText = outputTextView.text.toString()
            copyToClipboard(outputText)

            val confirmation = resources.getString(R.string.copy_confirmation)
            val confirmationToast = Toast.makeText(this, confirmation, Toast.LENGTH_SHORT)
            confirmationToast.show()
        }
    }

    private fun toBinary(text: String): String {
        fun toPaddedBitString(byte: Byte): String {
            return byte
                .toString(2)
                .padStart(8, '0')
        }
        val bytes = text.toByteArray(Charsets.UTF_8)
        val bitStrings = bytes.map(::toPaddedBitString)
        return bitStrings.reduce(String::plus)
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(null, text)
        clipboardManager.setPrimaryClip(clipData)
    }
}
package de.podratz.software.binarify.android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import de.podratz.software.binarify.android.databinding.ActivityMainBinding
import kotlin.math.pow


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
            val isInputBinary = isBinary(input)
            outputTextView.text = when (isInputBinary) {
                false -> toBinary(input)
                true -> toUtf8(input)
            }
        }

        copyButton.setOnClickListener {
            val outputText = outputTextView.text.toString()
            copyToClipboard(outputText)

            val confirmation = resources.getString(R.string.copy_confirmation)
            val confirmationToast = Toast.makeText(this, confirmation, Toast.LENGTH_SHORT)
            confirmationToast.show()
        }
    }

    private fun isBinary(text: String) : Boolean {
        val isMultipleOfLength8 = text.length % 8 == 0
        val hasOnlyBoolChars = text.all { it in setOf<Char>('0', '1') }
        return isMultipleOfLength8 && hasOnlyBoolChars
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

    private fun toUtf8(text: String) : String {
        var input = text
        var output = ""
        while (input.isNotEmpty()) {
            val nextByte = input
                .take(8)
                .map { it.digitToInt(2) }
            var charValue = 0
            for ((index, bit) in nextByte.withIndex()) {
                charValue += bit * (2.0).pow(7 - index).toInt()
            }
            input = input.removeRange(0, 8)
            output += charValue.toChar()
        }
        return output
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(null, text)
        clipboardManager.setPrimaryClip(clipData)
    }
}
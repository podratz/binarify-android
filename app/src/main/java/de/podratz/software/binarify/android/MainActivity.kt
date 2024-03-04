package de.podratz.software.binarify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    }
}
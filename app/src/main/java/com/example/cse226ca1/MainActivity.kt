package com.example.cse226ca1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.net.URL
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
        editText = findViewById(R.id.url)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            val imageUrl = editText.text.toString()
            loadImageAsync(imageUrl)
        }
    }


    private fun loadImageAsync(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = fetchImage(url)
            withContext(Dispatchers.Main) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }


    private suspend fun fetchImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

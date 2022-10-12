package com.example.viewit.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.viewit.R
import com.example.viewit.data.WordsForModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class CameraActivity : AppCompatActivity() {
    lateinit var medittxt: EditText
    var listFromUri= ArrayList<WordsForModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        medittxt = findViewById(R.id.edittxt)
        var uriS: String = getIntent().getStringExtra("uri") as String
        var uri: Uri = Uri.parse(uriS)
        extractTextFromUri(this, uri)
    }

    private fun extractTextFromUri(context: Context, uri: Uri){
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        var image: InputImage = InputImage.fromFilePath(context, uri)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        for (element in line.elements) {
                            val elementText = element.text
                            Log.d("Words",elementText)
                            val obj = WordsForModel(elementText)
                            Log.d("Words from obj",obj.words)
                            listFromUri.add(obj)
                            Log.d("size of list:",listFromUri.size.toString())
                            Log.d("word from list 0",listFromUri.get(0).words)
                        }
                    }
                }
                var intent =Intent(this, TextForModel::class.java)
                intent.putExtra("words",listFromUri)
                Log.d("Camera ArrayList size",listFromUri.size.toString())
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
            }
    }
}
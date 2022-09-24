package com.example.viewit

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class CameraActivity : AppCompatActivity() {
    lateinit var medittxt: EditText
    var list = ArrayList<String>()
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
                    val blockText = block.text
                    val blockCornerPoints = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox
                        for (element in line.elements) {
                            val elementText = element.text
                            Log.d("words1",elementText.toString())
                            list.add(elementText.toString())
                            println("Size of list in loop "+list.size)
                            val elementCornerPoints = element.cornerPoints
                            val elementFrame = element.boundingBox
                        }
                    }
                }
                var intent: Intent =Intent(this,TextForModel::class.java)
                intent.putExtra("words",list)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    }
}
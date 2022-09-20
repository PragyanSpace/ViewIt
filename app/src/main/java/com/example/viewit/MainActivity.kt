package com.example.viewit

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var mgetText:CardView
    lateinit var mtoCamera:CardView
    lateinit var imageUri: Uri
    lateinit var mHistory:CardView
    private val contract=registerForActivityResult(ActivityResultContracts.TakePicture()){
        var intent: Intent =Intent(this,CameraActivity::class.java)
        var imageUriString:String=""+imageUri
        intent.putExtra("uri",imageUriString)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mtoCamera=findViewById(R.id.toCamera)
        mgetText=findViewById(R.id.getText)
        mHistory=findViewById(R.id.History)
        imageUri=createImageUri()

        mgetText.setOnClickListener {
            openDialog()
        }

        mtoCamera.setOnClickListener{
            contract.launch(imageUri)
        }
        mHistory.setOnClickListener {
            val intent=Intent(this,TextForModel::class.java)
            startActivity(intent)
        }
    }
    private fun createImageUri(): Uri {
        val image= File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,
            "com.example.viewit.fileProvider",
        image)
    }
    public fun openDialog()
    {
        val builder=AlertDialog.Builder(this)
        val inflater=layoutInflater
        val dialogLayout=inflater.inflate(R.layout.write_the_text,null)
        val text=dialogLayout.findViewById<EditText>(R.id.textForModel).text
        with(builder)
        {
            setTitle("Enter model name")
            setPositiveButton("Show"){dialog,which->
                var intent: Intent =Intent(this@MainActivity,ARActivity::class.java)
                intent.putExtra("model_name",text)
                startActivity(intent)
            }
            setNegativeButton("Cancel"){dialog,which->
                Log.d("Main","Cancelled")
            }
            setView(dialogLayout)
            show()
        }
    }
}
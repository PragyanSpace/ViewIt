package com.example.viewit

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
            val intent=Intent(this@MainActivity,TextForModel::class.java)
            val list:ArrayList<String> =getWordsInDb()
            intent.putExtra("words",list)
            startActivity(intent)
        }
    }
//get history from database
    private fun getWordsInDb():ArrayList<String> {
        val dbMan=DatabaseManager(this)
        var cursor:Cursor=dbMan.fetch()
        val list = arrayListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val data = cursor.getString(0)
                list.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
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
        with(builder)
        {
            setTitle("Enter model name")
            setPositiveButton("Show"){dialog,which->
                val editText:EditText=dialogLayout.findViewById<EditText>(R.id.textForModel) as EditText
                val text:String=editText.text.toString()
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
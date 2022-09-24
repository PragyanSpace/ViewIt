package com.example.viewit

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ARActivity : AppCompatActivity() {

    lateinit var arFragment:ArFragment
    lateinit var modelName:String
    lateinit var storageRef:StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)
        arFragment = (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment?)!!
        modelName=intent.getStringExtra("model_name").toString()
        modelName=modelName.trim().lowercase()

        storageRef=FirebaseStorage.getInstance().getReference().child(modelName+".glb")
        try
        {
            var file:File=File.createTempFile("model",".glb")
            storageRef.getFile(file).addOnSuccessListener {
                var dbMan=DatabaseManager(this)
                dbMan.insert(modelName)
                arFragment.setOnTapPlaneGlbModel(file.absolutePath)
            }.addOnFailureListener{
                finish()
                Toast.makeText(this,"Model not available",Toast.LENGTH_LONG).show()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
}
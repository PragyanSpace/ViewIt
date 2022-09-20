package com.example.viewit

import android.app.ProgressDialog
import android.os.Bundle
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
        modelName=intent.getStringExtra("model_name") as String
        modelName=modelName.trim().lowercase()

        storageRef=FirebaseStorage.getInstance().getReference().child("heart.glb")
        try
        {
            var file:File=File.createTempFile("model",".glb")
            storageRef.getFile(file).addOnSuccessListener {
                arFragment.setOnTapPlaneGlbModel(file.path)
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
}
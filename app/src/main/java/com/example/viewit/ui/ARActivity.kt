package com.example.viewit.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.viewit.R
import com.example.viewit.ViewModels.ARViewModel
import com.example.viewit.data.WordsForModel
import com.example.viewit.databinding.ActivityAractivityBinding
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ARActivity : AppCompatActivity() {

    lateinit var arFragment:ArFragment
    lateinit var binding:ActivityAractivityBinding
    lateinit var modelName:String
    lateinit var storageRef:StorageReference

    lateinit var mARViewModel: ARViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_aractivity)
        arFragment = (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment?)!!
        modelName=intent.getStringExtra("model_name").toString()
        modelName=modelName.trim().lowercase()

        mARViewModel=ViewModelProvider(this).get(ARViewModel::class.java)



        storageRef=FirebaseStorage.getInstance().getReference().child(modelName+".glb")
        try
        {
            var file:File=File.createTempFile("model",".glb")
            storageRef.getFile(file).addOnSuccessListener {


                Handler(Looper.getMainLooper()).postDelayed({
                    @Override
                    fun run()
                    {
                        binding.progressbar.setProgress(0)
                        binding.progressbar.visibility=View.VISIBLE
                    }
                }, 3000)

                binding.progressbar.visibility=View.GONE
                var model=WordsForModel(modelName)
                mARViewModel.addModel(model)
                arFragment.setOnTapPlaneGlbModel(file.absolutePath)
            }.addOnProgressListener {
                var progress_bar:Double=(100.0 * it.bytesTransferred) / it.totalByteCount
                binding.progressbar.setProgress(progress_bar.toInt())
                binding.progressbar.visibility=View.VISIBLE

            }.addOnFailureListener{
                finish()
                Toast.makeText(this,"Model not available: "+modelName,Toast.LENGTH_LONG).show()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
package com.example.viewit.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.viewit.*
import com.example.viewit.ViewModels.HistoryViewModel
import com.example.viewit.data.WordsForModel
import com.example.viewit.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var imageUri: Uri

    lateinit var binding:ActivityMainBinding
    lateinit var mHistoryViewModel:HistoryViewModel


    private val contract=registerForActivityResult(ActivityResultContracts.TakePicture()){
        if(it==true) {
            var intent: Intent = Intent(this, CameraActivity::class.java)
            var imageUriString: String = "" + imageUri
            intent.putExtra("uri", imageUriString)
            startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.arearth))
        imageList.add(SlideModel(R.drawable.arsun))
        imageList.add(SlideModel(R.drawable.arcar))
        val slider = binding.imgSli
        slider.setImageList(imageList)

        imageUri=createImageUri()

        binding.getText.setOnClickListener {
            openDialog()
        }

        binding.toCamera.setOnClickListener{
            contract.launch(imageUri)
        }
        binding.History.setOnClickListener {
            mHistoryViewModel= ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HistoryViewModel::class.java)
            mHistoryViewModel.readAllData.observe(this, Observer {
                    list ->
                val ArList=list as ArrayList<WordsForModel>
                val intent=Intent(this@MainActivity, TextForModel::class.java)
                intent.putExtra("words",ArList)
                startActivity(intent)
        })
        }

        binding.Qmark.setOnClickListener{
            val intent=Intent(this@MainActivity, Help::class.java)
            startActivity(intent)
        }
    }


    private fun createImageUri(): Uri {
        val image= File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,
            "com.example.viewit.fileProvider",
        image)
    }


    private fun openDialog()
    {
        val dialog:Dialog=Dialog(this)
        dialog.setContentView(R.layout.write_the_text)
        dialog.setCancelable(true)
        dialog.show()
        val btn: Button=dialog.findViewById(R.id.showMe)
        btn.setOnClickListener {
            val editText:EditText=dialog.findViewById<EditText>(R.id.textForModel)
            val modelname:String=editText.text.toString()
            if(modelname!="")
            {var intent: Intent =Intent(this@MainActivity, ARActivity::class.java)
            intent.putExtra("model_name",modelname)
            startActivity(intent)
            dialog.hide()}
        }
    }
}
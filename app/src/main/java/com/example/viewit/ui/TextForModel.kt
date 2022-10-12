package com.example.viewit.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewit.MyAdapter
import com.example.viewit.R
import com.example.viewit.data.WordsForModel

class TextForModel() : AppCompatActivity(){
    lateinit var recView:RecyclerView
    var Arlist=ArrayList<WordsForModel>()
    var adapter: MyAdapter = MyAdapter(Arlist)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_for_model)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode= WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        recView=findViewById(R.id.recView)
        recView.layoutManager=LinearLayoutManager(this)
        Arlist= intent.getSerializableExtra("words") as ArrayList<WordsForModel>
        adapter= MyAdapter(Arlist)
        recView.adapter=adapter
        adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val intent =Intent(this@TextForModel, ARActivity::class.java)
                val list=Arlist.toList()
                intent.putExtra("model_name",list[position].words)
                startActivity(intent)
                finish()
            }
        })
    }
}

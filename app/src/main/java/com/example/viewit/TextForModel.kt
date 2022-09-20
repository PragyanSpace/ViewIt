package com.example.viewit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TextForModel() : AppCompatActivity(){
    lateinit var recView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_for_model)

        recView=findViewById(R.id.recView)
        recView.layoutManager=LinearLayoutManager(this)
        val list:ArrayList<String> = intent.getSerializableExtra("words") as ArrayList<String>
        val adapter=MyAdapter(list)
        recView.adapter=adapter
        adapter.setOnItemClickListener(object:MyAdapter.onItemClickListener{
            override fun onItemClicked(position: Int) {
                var intent: Intent =Intent(this@TextForModel,ARActivity::class.java)
                intent.putExtra("model_name", list[position])
                startActivity(intent)
                finish()
            }

        })
    }
}

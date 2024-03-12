package com.example.duplicateactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.selectUnbiased
import java.security.AllPermission

class MainActivity() : AppCompatActivity() , RecyclerViewAdapter.OnItemLongClickListener {
    private lateinit var rv_normalImages:RecyclerView
    private lateinit var rv_compressedImages:RecyclerView
    private lateinit var btn_gotit:Button
    private lateinit var moreInformationLayout: LinearLayout
    private lateinit var btn_delete:Button
    var count = 0
    var size = 0
    var isLongClicked = false
    val listOfNormalImages = mutableListOf<ItemOfRecyclerView>()
    val selectImagesToDelete =  mutableListOf<Int>()
    private lateinit var imgback:ImageView
    private lateinit var imgcancel:ImageView
    private lateinit var txtBanner:TextView
    private lateinit var selectAll:CheckBox
    private lateinit var btn_floating:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()

        listOfNormalImages.add(
            ItemOfRecyclerView(
                img = R.mipmap.img,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfNormalImages.add(
            ItemOfRecyclerView(
                img = R.mipmap.img_1,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfNormalImages.add(
            ItemOfRecyclerView(
                img = R.mipmap.img_2,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfNormalImages.add(
            ItemOfRecyclerView(
                img = R.mipmap.img_3,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfNormalImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",

            )
        )



        val listOfCompressedImages = mutableListOf<ItemOfRecyclerView>()
        listOfCompressedImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfCompressedImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfCompressedImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfCompressedImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",

            )
        )
        listOfCompressedImages.add(
            ItemOfRecyclerView(
                img = R.drawable.ic_launcher_background,
                title = "Jiraya",
                size = "100 gb",
            )
        )
        val adapteroOfNormalImages = RecyclerViewAdapter(listOfNormalImages,this)
        rv_normalImages.layoutManager = LinearLayoutManager(this)
        rv_normalImages.adapter = adapteroOfNormalImages


        val adapteroOfCompressedImages = RecyclerViewAdapter(listOfCompressedImages,this)
        rv_compressedImages.layoutManager = LinearLayoutManager(this)
        rv_compressedImages.adapter = adapteroOfCompressedImages


        btn_gotit.setOnClickListener {
            moreInformationLayout.visibility = View.GONE
        }
        cancelSelectionofItem()
        deleteSelectedItem()
        selectAllItem()

        btn_floating.setOnClickListener {
            startActivity(Intent(this,VideoPlayer::class.java))
        }
    }
    private fun init(){
        rv_compressedImages = findViewById(R.id.rv_compressedImages)
        rv_normalImages = findViewById(R.id.rv_normalImages)
        btn_gotit = findViewById(R.id.btn_gotit)
        moreInformationLayout = findViewById(R.id.more_information)
        btn_delete = findViewById(R.id.btn_delete)
        imgback = findViewById(R.id.back_arrow)
        imgcancel = findViewById(R.id.img_cancel)
        txtBanner = findViewById(R.id.banner_text)
        selectAll = findViewById(R.id.btn_selectall)
        btn_floating = findViewById(R.id.floating_action)
    }

    private fun selectAllItem(){
        selectAll.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                GlobalScope.launch(Dispatchers.Main) {
                    for (item in listOfNormalImages){
                        val layoutParams = item.cardView!!.layoutParams
                        layoutParams.width = 100
                        layoutParams.height= 100
                        item.cardView!!.layoutParams = layoutParams
                        item.tick!!.visibility = View.VISIBLE
                        selectImagesToDelete.add(item.img)
                    }
                    btn_delete.setBackgroundColor(getColor(R.color.light_blue))
                    count = selectImagesToDelete.size

                    btn_delete.text = "Delete ${count} duplicate files"
                    txtBanner.text = "${count} selected\n 9.6 GB"
                    imgback.visibility = View.GONE
                    imgcancel.visibility = View.VISIBLE
                }
            }
            else{
                GlobalScope.launch(Dispatchers.Main) {
                    btn_delete.setBackgroundColor(getColor(R.color.grey))
                    count = 0
                    selectImagesToDelete.clear()
                    btn_delete.text = "Delete duplicate files"
                    txtBanner.text = "Duplicate Files"
                    imgback.visibility = View.VISIBLE
                    imgcancel.visibility = View.GONE

                    for (item in listOfNormalImages) {
                        val layoutParams = item.cardView!!.layoutParams
                        layoutParams.width = 150
                        layoutParams.height = 150
                        item.cardView!!.layoutParams = layoutParams
                        item.tick!!.visibility = View.GONE
                    }
                }
            }
        }

    }

    private fun cancelSelectionofItem(){
        imgcancel.setOnClickListener {
            btn_delete.setBackgroundColor(getColor(R.color.grey))
            count = 0
            selectImagesToDelete.clear()
            btn_delete.text = "Delete duplicate files"
            txtBanner.text = "Duplicate Files"
            imgback.visibility = View.VISIBLE
            imgcancel.visibility = View.GONE

            for (item in listOfNormalImages){
                val layoutParams = item.cardView!!.layoutParams
                layoutParams.width = 150
                layoutParams.height= 150
                item.cardView!!.layoutParams = layoutParams
                item.tick!!.visibility = View.GONE
            }
        }
    }


    override fun onItemLongClick(position: Int) {
        isLongClicked = true
        val layoutParams = listOfNormalImages[position].cardView!!.layoutParams
        layoutParams.width = 100
        layoutParams.height= 100
        listOfNormalImages[position].cardView!!.layoutParams = layoutParams
        listOfNormalImages[position].tick!!.visibility = View.VISIBLE
        selectImagesToDelete.add(listOfNormalImages[position].img)
        btn_delete.setBackgroundColor(getColor(R.color.light_blue))
        count = selectImagesToDelete.size

        btn_delete.text = "Delete ${count} duplicate files"
        txtBanner.text = "${count} selected\n 9.6 GB"
        imgback.visibility = View.GONE
        imgcancel.visibility = View.VISIBLE
    }

    override fun onItemClick(position: Int) {
        if(isLongClicked){

            if(selectImagesToDelete.contains(listOfNormalImages[position].img)){
                Log.i("hello","${selectImagesToDelete.contains(listOfNormalImages[position].img)}----${position}")
                val layoutParams = listOfNormalImages[position].cardView!!.layoutParams
                layoutParams.width = 150
                layoutParams.height= 150
                listOfNormalImages[position].cardView!!.layoutParams = layoutParams
                listOfNormalImages[position].tick!!.visibility = View.GONE
                selectImagesToDelete.remove(listOfNormalImages[position].img)
                count = selectImagesToDelete.size
                btn_delete.text = "Delete ${count} duplicate files"
                txtBanner.text = "${count} selected\n 9.6 GB"
            }
            else{
                val layoutParams = listOfNormalImages[position].cardView!!.layoutParams
                layoutParams.width = 100
                layoutParams.height= 100
                listOfNormalImages[position].cardView!!.layoutParams = layoutParams
                listOfNormalImages[position].tick!!.visibility = View.VISIBLE
                selectImagesToDelete.add(listOfNormalImages[position].img)
                count = selectImagesToDelete.size
                btn_delete.text = "Delete ${count} duplicate files"
                txtBanner.text = "${count} selected\n 9.6 GB"
                imgback.visibility = View.GONE
                imgcancel.visibility = View.VISIBLE
            }

        }
    }

    private fun deleteSelectedItem(){

        btn_delete.setOnClickListener {
            //Perform Delete Action here
            if(isLongClicked){
                Toast.makeText(this,selectImagesToDelete.toString(),Toast.LENGTH_SHORT).show()
                selectImagesToDelete.clear()
                count = selectImagesToDelete.size
                btn_delete.text = "Delete duplicate files"
                txtBanner.text = "Duplicate Files"
                imgback.visibility = View.VISIBLE
                imgcancel.visibility = View.GONE
            }


        }


    }

}
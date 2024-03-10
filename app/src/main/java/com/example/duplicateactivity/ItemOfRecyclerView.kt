package com.example.duplicateactivity

import android.widget.ImageView
import androidx.cardview.widget.CardView

data class ItemOfRecyclerView(
    val img:Int,
    val title:String,
    val size:String,
    var cardView: CardView? = null,
    var tick:ImageView? = null
)

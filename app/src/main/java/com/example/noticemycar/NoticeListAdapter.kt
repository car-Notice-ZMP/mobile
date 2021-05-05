package com.example.noticemycar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class NoticeListAdapter (var mCtx: Context, var resources:Int, var items:List<NoticeListModel>):
    ArrayAdapter<NoticeListModel>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val carimageView:ImageView = view.findViewById(R.id.carimage)
        val markmodelTextView:TextView = view.findViewById(R.id.markmodel)
        val yearTextView:TextView = view.findViewById(R.id.year)
        val priceTextView:TextView = view.findViewById(R.id.price)

        var mItem:NoticeListModel = items[position]
        Picasso.get().load(mItem.img).into(carimageView)
        markmodelTextView.text = mItem.title
        yearTextView.text = mItem.year
        priceTextView.text = mItem.price

        return view
    }
}
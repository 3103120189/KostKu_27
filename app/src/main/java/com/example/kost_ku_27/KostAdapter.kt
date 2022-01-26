package com.example.kost_ku_27

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kost_ku_27.room.Kost
import kotlinx.android.synthetic.main.list_kost.view.*
import kotlinx.android.synthetic.main.list_kost.view.*
import java.util.*

class KostAdapter(private val kosts: ArrayList<Kost>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<KostAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_kost, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie  = kosts[position]
        holder.view.text_title.text = movie.title
        holder.view.text_title.setOnClickListener {
            listener.onClick( movie )
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate( movie )
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete( movie )
        }
    }

    override fun getItemCount() = kosts.size

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Kost>){
        kosts.clear()
        kosts.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(kost: Kost)
        fun onUpdate(kost: Kost)
        fun onDelete(kost: Kost)
    }
}
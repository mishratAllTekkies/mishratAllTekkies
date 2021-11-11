package com.htf.zbCard.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htf.zbCard.R
import com.htf.zbCard.netUtils.Constants
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.row_dashboard_item_view.view.*

class DashBoardAdapter(
    private var currActivity: Activity,
    private var arrCategoryList:ArrayList<DashboardModel>,private var iListItemClickListener: IListItemClickListener<Any>
): RecyclerView.Adapter<DashBoardAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        init {
            itemView.setOnClickListener {
                iListItemClickListener.onItemClickListener(arrCategoryList[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.row_dashboard_item_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrCategoryList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val model=arrCategoryList[position]
        holder.itemView.tv_category_name.text=model.name
        Glide.with(currActivity).load(
            Constants.Urls.IMAGE_URL+model.image)
            .placeholder(R.drawable.homepage_category).into(holder.itemView.iv_category_image)

    }
    companion object {
        const val CATEGORY_MODEL = "model"
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
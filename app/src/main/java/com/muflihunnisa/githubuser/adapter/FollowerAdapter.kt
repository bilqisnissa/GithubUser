package com.muflihunnisa.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muflihunnisa.githubuser.R
import com.muflihunnisa.githubuser.databinding.ListItemBinding
import com.muflihunnisa.githubuser.model.Users

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    private val users = ArrayList<Users>()

    inner class FollowerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val itemUserBinding = ListItemBinding.bind(itemView)

        fun bind (user: Users){
            itemUserBinding.tvItemUsername.text = user.username
            Glide.with(itemView.context).load(user.avatar).apply(RequestOptions().override(55, 55 )).into(itemUserBinding.ivItemUser)
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): FollowerAdapter.FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}
package com.muflihunnisa.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muflihunnisa.githubuser.R
import com.muflihunnisa.githubuser.activity.DetailActivity
import com.muflihunnisa.githubuser.databinding.ListItemBinding
import com.muflihunnisa.githubuser.model.Users

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val users = ArrayList<Users>()

    fun setData(user: ArrayList<Users>){
        users.clear()
        users.addAll(user)

    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemUserBinding = ListItemBinding.bind(itemView)

        fun bind(users: Users) {
            Glide.with(itemView.context).load(users.avatar).apply(RequestOptions().override(55, 55)).into(itemUserBinding.ivItemUser)
            itemUserBinding.tvItemUsername.text = users.username

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, users.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}
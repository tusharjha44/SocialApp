package com.example.socialapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.databinding.ItemPostBinding
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post>, private val listener: IPostAdapter): FirestoreRecyclerAdapter<Post,MyViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Post) {
        holder.bind(model)
        holder.binding.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(position).id)
        }
    }
}

class MyViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(model: Post){
        binding.postTitle.text = model.text
        binding.userName.text = model.createdBy.name
        Glide.with(binding.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(binding.userImage)
        binding.likeCount.text = model.likedBy.size.toString()
        binding.createdAt.text = Utils.getTimeAgo(model.createdAt)


        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            binding.likeButton.setImageDrawable(ContextCompat.getDrawable(binding.likeButton.context, R.drawable.ic_liked))
        } else {
            binding.likeButton.setImageDrawable(ContextCompat.getDrawable(binding.likeButton.context, R.drawable.ic_disliked))
        }
    }
}


interface IPostAdapter {
    fun onLikeClicked(postId: String)
}
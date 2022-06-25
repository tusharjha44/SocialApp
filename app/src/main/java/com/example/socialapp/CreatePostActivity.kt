package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialapp.daos.PostDao
import com.example.socialapp.databinding.ActivityCreatePostBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao
    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postDao = PostDao()

        binding.postButton.setOnClickListener {
            val input = binding.postInput.text.toString().trim()
            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
}
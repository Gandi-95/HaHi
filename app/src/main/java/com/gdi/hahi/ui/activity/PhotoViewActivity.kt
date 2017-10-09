package com.gdi.hahi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gdi.hahi.R

import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

class PhotoViewActivity : AppCompatActivity() {


    internal var photoView: PhotoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)

        photoView = findViewById(R.id.photoView) as PhotoView
        photoView!!.onPhotoTapListener = PhotoViewAttacher.OnPhotoTapListener { view, x, y -> finish() }

        val url = intent.extras.getString(URL)
        Glide.with(this).load(url).apply(RequestOptions().fitCenter()).into(photoView)

    }


    companion object {
        private val URL = "url"

        fun newUrlIntent(context:Context,url:String): Intent {
            val newIntent = Intent(context,PhotoViewActivity::class.java)
            newIntent.putExtra(URL,url);
            return newIntent
        }
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}

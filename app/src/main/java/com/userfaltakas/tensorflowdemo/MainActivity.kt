package com.userfaltakas.tensorflowdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.userfaltakas.tensorflowdemo.databinding.ActivityMainBinding
import com.userfaltakas.tensorflowdemo.ml.BirdsModel
import org.tensorflow.lite.support.image.TensorImage


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val image = BitmapFactory.decodeResource(resources, R.drawable.bird)
        outputGenerator(image)
    }


    private fun outputGenerator(bitmap: Bitmap) {
        val model = BirdsModel.newInstance(this)
        val image = TensorImage.fromBitmap(bitmap)
        val result = model.process(image).probabilityAsCategoryList.apply {
            sortByDescending { it.score }
        }.take(4)

        result.forEach {
            Log.d("bird category", it.toString())
        }
        model.close()
    }
}
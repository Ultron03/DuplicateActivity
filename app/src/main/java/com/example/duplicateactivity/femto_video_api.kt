package com.example.duplicateactivity


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI


// this is API key : 3410bcfc19a9bebcd5ff4ecd465e8d6c


object femto_video_api {

    var file_path:String? = null
    var isSuccessfull= MutableLiveData<Boolean>(false)
    private val API_KEY_ = "9dbd3935da3015a0cbcf4463493d3199"               //  paste you API_KEY here


    fun optimise_video(videoPath: String, context_: Context) {
        val startTime = System.currentTimeMillis()
        val client = OkHttpClient()
        Log.i("name",videoPath)

        val videoFile = File(videoPath)
        val optimised_file_name = "femto_optimised_" + videoFile.name
        val requestBody = MultipartBody
            .Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "video", videoFile.name, RequestBody
                    .create("video/mp4".toMediaTypeOrNull(), videoFile)
            )
            .build()


        val request = Request.Builder()
            .url("https://api.femto.cypherx.in/optimize-video")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $API_KEY_")
            .build()


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw java.io.IOException("Unexpected code $response")

            var responseData = response.body?.bytes()

            //  this is for if you want to log size of optimised file after downloading it ${responseData?.size?.div(1024)} KB

            val file_to_save_optimised_video_ =
                get_a_new_optimised_file_path(context_, optimised_file_name)

            // Write optimized video to a file
            responseData?.let {
                file_to_save_optimised_video_.writeBytes(it)
            }
            isSuccessfull.postValue(true)
            file_path = file_to_save_optimised_video_.toString()


            val endTime = System.currentTimeMillis()
            val delta_time =
                endTime - startTime                    // if you want to log your delta time


            // ################# "file_to_save_optimised_video_" now you can use this file path to upload the optimised video in your server
        }
    }


    // this function will save the video into the App directory so that another App cannot access it in the gallery or something
    fun get_a_new_optimised_file_path(context: Context, fileName: String): File {
        val directory = File(context.filesDir, "femto_optimised_videos")
        if (!directory.exists()) directory.mkdirs() // Create the directory if it doesn't exist

        val file = File(directory, fileName)

        return file
    }

}

// put this in MainActivity or Fragment etc. from which the file will be upload




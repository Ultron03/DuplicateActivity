package com.example.duplicateactivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.media3.common.MediaItem
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.AudioAttributes.AudioAttributesV21
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoPlayer : AppCompatActivity() {
    private lateinit var videoPlayer: ExoPlayer
    private lateinit var pleyer:PlayerView
    private var path:String? = null
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)


        if (!checkPermissions(this)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                101
            )
        }


        init()
        progressBar.visibility = View.VISIBLE
        videoPlayer = ExoPlayer.Builder(this).build()
        femto_video_api.isSuccessfull.observe(this){
            if(it){
                val medialItem = MediaItem.fromUri(Uri.parse(femto_video_api.file_path))
                videoPlayer.setMediaItem(medialItem)
                pleyer.player = videoPlayer
                videoPlayer.prepare()
                progressBar.visibility = View.GONE
                videoPlayer.play()
            }

        }



        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, 101)

    }


    override fun onStart() {
        super.onStart()

        videoPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        videoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayer.release()
    }

    private fun init(){
        pleyer = findViewById(R.id.videoView)
        progressBar = findViewById(R.id.progress_bar)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val videoUri = data?.data
            if (videoUri != null) {
                val videoPath = getRealPathFromUri(videoUri)

                path = videoPath
                Thread({
                    femto_video_api.optimise_video(path!!,applicationContext)
                }).start()

            }
        }
    }
    fun getRealPathFromUri(contentUri: Uri): String {
        val contentResolver = contentResolver
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(MediaStore.Video.Media.DATA)
                return it.getString(index)
            } else {
                return ""
            }
            it.close()
        }
        return ""
    }

    fun checkPermissions(activity: Activity): Boolean {
        val permission = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

                Toast.makeText(this,"Permission not Provided",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
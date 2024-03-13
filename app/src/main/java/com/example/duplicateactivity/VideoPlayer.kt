package com.example.duplicateactivity

import androidx.media3.common.MediaItem
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.VideoView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoPlayer : AppCompatActivity() {
    private lateinit var videoPlayer: ExoPlayer
    private lateinit var pleyer:PlayerView
    private lateinit var forwardVideo:ImageButton
    private lateinit var playVideo:ImageButton
    private lateinit var pauseVideo:ImageButton
    private lateinit var rewindVideo:ImageButton
    private lateinit var seekBar:SeekBar
    private lateinit var rotateScreen:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        init()
        //path of video
        val vPath = "android.resource://"+packageName+"/raw/fifth_video"

        videoPlayer = ExoPlayer.Builder(this).build()
        val medialItem = MediaItem.fromUri(Uri.parse(vPath))
        videoPlayer.setMediaItem(medialItem)
        videoPlayer.prepare()
        videoPlayer.play()
        pleyer.player = videoPlayer
//        resumeVideoBtn()
//        pauseVideoBtn()
    }
//    private fun resumeVideoBtn(){
//        playVideo.setOnClickListener {
//            if(videoPlayer.isPlaying){
//                videoPlayer.resume()
//                playVideo.visibility = View.GONE
//                pauseVideo.visibility = View.VISIBLE
//            }
//            else{
//                videoPlayer.start()
//                playVideo.visibility = View.GONE
//                pauseVideo.visibility = View.VISIBLE
//            }
//
//        }
//    }
//    private fun pauseVideoBtn(){
//        pauseVideo.setOnClickListener {
//            videoPlayer.pause()
//            playVideo.visibility = View.VISIBLE
//            pauseVideo.visibility = View.GONE
//        }
//    }

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
//        forwardVideo = findViewById(R.id.videoView_forward)
//        playVideo = findViewById(R.id.videoView_play)
//        pauseVideo = findViewById(R.id.videoView_pause)
//        rewindVideo = findViewById(R.id.videoView_rewind)
//        seekBar = findViewById(R.id.videoView_seekbar)
//        rotateScreen = findViewById(R.id.videoView_rotate)



    }
}
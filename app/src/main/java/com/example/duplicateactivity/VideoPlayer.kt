package com.example.duplicateactivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.VideoView

class VideoPlayer : AppCompatActivity() {
    private lateinit var videoPlayer: VideoView
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
        val mediaController= MediaController(this)

        videoPlayer.setMediaController(mediaController)
        videoPlayer.setVideoURI(Uri.parse(vPath))
        videoPlayer.requestFocus()
        videoPlayer.start()
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
    private fun init(){
        videoPlayer = findViewById(R.id.videoView)
//        forwardVideo = findViewById(R.id.videoView_forward)
//        playVideo = findViewById(R.id.videoView_play)
//        pauseVideo = findViewById(R.id.videoView_pause)
//        rewindVideo = findViewById(R.id.videoView_rewind)
//        seekBar = findViewById(R.id.videoView_seekbar)
//        rotateScreen = findViewById(R.id.videoView_rotate)



    }
}
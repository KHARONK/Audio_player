package nmhu.edu.sd_bssd4250hw101_mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat

class MainActivity : AppCompatActivity() {

    private val STEP_TAG:String = "nmhu.edu.sd_bssd4250hw101_mediaplayer"
    private val LLID:Int = 123 //constant id for linear layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var mediaPlayer: MediaPlayer? = null

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.step)
        }

        val playButtton = Button(this).apply {
            text = "Play"
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                playAudio()
            }
        }
        val ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )

            id = LLID
        }
        setContentView(ll)
        if (savedInstanceState == null) {
            //create fragment for collection edits buttons
            supportFragmentManager.commit {
                replace(ll.id, AudioFragment.newInstance(R.raw.step), STEP_TAG)
            }
        }
        else
        {
            val stepFragment = supportFragmentManager.findFragmentByTag(STEP_TAG) as AudioFragment
            supportFragmentManager.commit{
                replace(ll.id, stepFragment, STEP_TAG)
            }
        }

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentTime = savedInstanceState.getInt(STEP_TAG)
        playAudio(currentTime)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STEP_TAG, mediaPlayer?.currentPosition!!)
        mediaPlayer?.pause()
    }
    private fun playAudio(time:Int = 0){
        if (mediaPlayer?.isPlaying!!){
            mediaPlayer?.pause()
            mediaPlayer?.seekTo(time) //rewinds and resets buffers
        }
        else
        {
            mediaPlayer?.start()
        }

    }

    companion object{
        private var mediaPlayer:MediaPlayer? = null
    }
}

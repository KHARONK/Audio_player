package nmhu.edu.sd_bssd4250hw101_mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider


private const val AUDIO_RES ="audio_file" //key for bundle
class AudioFragment : Fragment() {

    private lateinit var viewModel:AudioViewModel

    private var mediaPlayer: MediaPlayer? = null///will hold mediaPlayer
    private var audioRes:Int? = null //resource ti play

    override  fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AudioViewModel::class.java)
        arguments?.let {
            audioRes = it.getInt(AUDIO_RES) //load argument from companion
        }
    }

    override fun onCreateView(
        inflator: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View? {
        if (this.mediaPlayer == null) {
            this.mediaPlayer = MediaPlayer.create(context, audioRes!!)//make mediaplayer
        }

        val v: View = inflator.inflate(R.layout.fragment_audio, container, false)
        //look up each button and give it click listener
        v.findViewById<Button>(R.id.play_button).apply {
            setOnClickListener {
               playMedia()//reusable function
            }
        }

        v.findViewById<Button>(R.id.play_button).apply {
            setOnClickListener {
               stopMedia()//reusable function
            }

        }
        return v
    }

    private fun playMedia(time:Int = 0){
        mediaPlayer?.seekTo(time)
        mediaPlayer?.start()
    }

    private fun stopMedia(time:Int = 0){
        mediaPlayer?.stop()
        mediaPlayer?.prepare()
    }

    //these two functions are pretty much same
    //concepts as the instance state stuff from before
    override fun onDestroyView(){
        super.onDestroyView()
        viewModel.setCurrentTime(this.mediaPlayer?.currentPosition!!)
        stopMedia()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (viewModel.getCurrentTime() > 0){
            playMedia(viewModel.getCurrentTime())
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(audio:Int) =
            AudioFragment().apply {
                arguments = Bundle().apply {
                   putInt(AUDIO_RES, audio)
                }
            }
    }
}
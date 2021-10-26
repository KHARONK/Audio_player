import androidx.lifecycle.ViewModel

class AudioViewModel : ViewModel() {
    //could store the audio file name in here too
    //if it were chosen by the user, but it is
    //sent via bundle, which persistes on rotate

    private var currTime: Int = 0

    fun setCurrrentTime(time:Int){
        currTime = time;
    }

    fun getCurrentTime():Int{
        return currTime
    }
}
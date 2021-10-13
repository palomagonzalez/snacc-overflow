package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.viewModels
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.MatchListViewModel
import wpi.cs4518.snaccoverflow.util.FirestoreUtil
import java.util.*

private const val TAG = "wpi.MatchViewFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MatchViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchViewFragment : Fragment() {

    private val matchListViewModel: MatchListViewModel by viewModels()

    private lateinit var gestureDetector: GestureDetectorCompat

    private lateinit var toast: Toast

    private lateinit var currentMatch: MatchListViewModel.Match


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_match_view, container, false)
        Log.d(TAG, "Inflated")
        loadCurrentMatch()
        // setup gesture detector
        gestureDetector = GestureDetectorCompat(context, MatchGestureListener(this::onSwipeLeft, this::onSwipeRight))
        view.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

        })
        toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        return view
    }

    private fun loadCurrentMatch() {
        Log.d(TAG, "Loading potential match")
        matchListViewModel.getCurrentMatch().observe(
            viewLifecycleOwner,
            {
                    match -> updateUI(match)
            }
        )
    }

    private fun loadNextMatch() {
        Log.d(TAG, "Loading potential match")
        matchListViewModel.getNewMatch().observe(
            viewLifecycleOwner,
            {
                match -> updateUI(match)
                currentMatch = match
            }
        )
    }

    private fun updateUI(match: MatchListViewModel.Match) {
        val infoLabel = view?.findViewById<TextView>(R.id.labelMatchInfo)
        val locationLabel = view?.findViewById<TextView>(R.id.labelMatchLocation)
        val ans1Label = view?.findViewById<TextView>(R.id.labelMatchAnswerOne)
        val ans2Label = view?.findViewById<TextView>(R.id.labelMatchAnswerTwo)
        val ans3Label = view?.findViewById<TextView>(R.id.labelMatchAnswerThree)
        infoLabel?.text = "${match.profile.name}, ${match.profile.age}"
        locationLabel?.text = match.location
        ans1Label?.text = match.profile.answerOne
        ans2Label?.text = match.profile.answerTwo
        ans3Label?.text = match.profile.answerThree
    }

    private fun onSwipeLeft() {
        writeToast("Red Flag")
        loadNextMatch()
    }

    private fun onSwipeRight() {
        writeToast("I Can Overlook That")
        FirestoreUtil.saveUser(currentMatch.profile)
        loadNextMatch()
    }

    private fun writeToast(text: String) {
        toast.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private inner class MatchGestureListener(
        val onSwipeLeft:()->Unit,
        val onSwipeRight:()->Unit):
        GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 100

        override fun onDown(event: MotionEvent): Boolean {
            Log.d(TAG, "onDown: $event")
            return true
        }

        override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val dX = event2.x - event1.x
            val dY = event2.y - event1.y
            Log.d(TAG, "dX: $dX, dY: $dY")
            if (dY > SWIPE_THRESHOLD || Math.abs(dX) < SWIPE_THRESHOLD) {
                return false
            }
            if (dX > 0) {
                Log.d(TAG, "Swiping Right")
                onSwipeRight()
            } else {
                Log.d(TAG, "Swiping Left")
                onSwipeLeft()
            }
            return true
        }

    }

}
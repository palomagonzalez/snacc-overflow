package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.viewModels
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.MatchListViewModel
import java.util.*

private const val TAG = "wpi.MatchViewFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MatchViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchViewFragment : Fragment() {

    private val matchListViewModel: MatchListViewModel by viewModels()


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
        loadNextMatch()
        // setup gesture detector
        return view
    }

    private fun loadNextMatch() {
        Log.d(TAG, "Loading potential match")
        matchListViewModel.getMatch().observe(
            viewLifecycleOwner,
            {
                match -> updateUI(match)
            }
        )
    }

    private fun updateUI(match: MatchListViewModel.Match) {
        val infoLabel = view?.findViewById<TextView>(R.id.labelMatchInfo)
        val locationLabel = view?.findViewById<TextView>(R.id.labelMatchLocation)
        val ans1Label = view?.findViewById<TextView>(R.id.labelMatchAnswerOne)
        val ans2Label = view?.findViewById<TextView>(R.id.labelMatchAnswerTwo)
        val ans3Label = view?.findViewById<TextView>(R.id.labelMatchAnswerThree)
        infoLabel?.text = "${match.name}, ${match.age}"
        locationLabel?.text = match.location
        ans1Label?.text = match.answerOne
        ans2Label?.text = match.answerTwo
        ans3Label?.text = match.answerThree
    }

}
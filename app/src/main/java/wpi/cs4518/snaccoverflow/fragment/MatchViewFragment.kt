package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import wpi.cs4518.snaccoverflow.R

private const val TAG = "wpi.MatchViewFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MatchViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "Inflated")
        return inflater.inflate(R.layout.fragment_match_view, container, false)
    }

}
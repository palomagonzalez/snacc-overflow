package wpi.cs4518.snaccoverflow.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import wpi.cs4518.snaccoverflow.R

private const val TAG = "wpi.MessageListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Inflated")
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

}
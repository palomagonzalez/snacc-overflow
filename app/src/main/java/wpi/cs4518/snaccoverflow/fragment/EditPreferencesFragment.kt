package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import wpi.cs4518.snaccoverflow.R

private const val TAG = "wpi.Preferences"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPreferencesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_preferences, container, false)
    }
}
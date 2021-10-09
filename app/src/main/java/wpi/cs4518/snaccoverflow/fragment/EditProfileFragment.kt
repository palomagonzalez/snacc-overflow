package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.Profile
import wpi.cs4518.snaccoverflow.model.ProfileRepository

private const val TAG = "wpi.EditProfile"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        loadCurrentProfile()
        return view
    }

    private fun loadCurrentProfile() {
        ProfileRepository.get().getProfile().observe(
            viewLifecycleOwner,
            {
                profile -> updateUI(profile)
            }
        )
    }

    private fun updateUI(profile: Profile) {
        val infoLabel = view?.findViewById<EditText>(R.id.labelEditInfo)
        val answerOneLabel = view?.findViewById<EditText>(R.id.labelEditAnswerOne)
        val answerTwoLabel = view?.findViewById<EditText>(R.id.labelEditAnswerTwo)
        val answerThreeLabel = view?.findViewById<EditText>(R.id.labelEditAnswerThree)
        infoLabel?.setText("${profile.name}, ${profile.age}")
        answerOneLabel?.setText(profile.answerOne)
        answerTwoLabel?.setText(profile.answerTwo)
        answerThreeLabel?.setText(profile.answerThree)

    }

}
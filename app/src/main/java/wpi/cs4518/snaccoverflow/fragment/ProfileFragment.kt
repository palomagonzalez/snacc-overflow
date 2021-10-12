package wpi.cs4518.snaccoverflow.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.helpers.ImageUtils
import wpi.cs4518.snaccoverflow.model.ProfileViewModel

private const val TAG = "wpi.ProfileFragment"

// TODO: load profile picture

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Inflated")
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Edit Profile Button
        view.findViewById<ImageButton>(R.id.buttonEditProfile)?.setOnClickListener {
            val fragment = EditProfileFragment()
            transitionToFragment(fragment)
        }
        // Edit Preferences Button
        view.findViewById<ImageButton>(R.id.buttonTakeProfilePicture)?.setOnClickListener {
            val fragment = EditPreferencesFragment()
            transitionToFragment(fragment)
        }
        // set name
        viewModel.profile.observe(
            viewLifecycleOwner,
            {
                profile ->
                view.findViewById<TextView>(R.id.labelProfileViewInfo).text = "${profile.name}, ${profile.age}"
                if (profile.profilePictureLocation != null) {
                    view.findViewById<ImageView>(R.id.imageProfile).setImageBitmap(ImageUtils.getImage(
                        profile.profilePictureLocation!!
                    ))
                }
            }
        )
        return view
    }

    private fun transitionToFragment(fragment: Fragment) {
        val fragmentManger = activity?.supportFragmentManager
        fragmentManger?.commit {
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}
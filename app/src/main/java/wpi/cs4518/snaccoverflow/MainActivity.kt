package wpi.cs4518.snaccoverflow

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wpi.cs4518.snaccoverflow.fragment.MatchViewFragment
import wpi.cs4518.snaccoverflow.fragment.MessageListFragment
import wpi.cs4518.snaccoverflow.fragment.PeopleFragment
import wpi.cs4518.snaccoverflow.fragment.ProfileFragment
import wpi.cs4518.snaccoverflow.model.ProfileRepository

private const val TAG = "wpi.MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        val profileFragment = ProfileFragment()
        val peopleFragment = PeopleFragment()
        val matchViewFragment = MatchViewFragment()

        if (currentFragment == null) {
            setCurrentFragment(matchViewFragment)
        }

        findViewById<BottomNavigationView>(R.id.nav_bar).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.matchView -> setCurrentFragment(matchViewFragment)
                R.id.messageListView -> setCurrentFragment(peopleFragment)
                R.id.profileView -> setCurrentFragment(profileFragment)
            }
            true
        }

        Log.d(TAG, "Created MainActivity")
    }

    private fun setCurrentFragment(fragment: Fragment): Boolean {
        Log.d(TAG, "Switching to fragment $fragment")
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
        return true
    }

}
package wpi.cs4518.snaccoverflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import wpi.cs4518.snaccoverflow.fragment.MatchViewFragment
import wpi.cs4518.snaccoverflow.fragment.MessageListFragment
import wpi.cs4518.snaccoverflow.fragment.ProfileFragment

private const val TAG = "wpi.MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        val profileFragment = ProfileFragment()
        val messageListFragment = MessageListFragment()
        val matchViewFragment = MatchViewFragment()

        if (currentFragment == null) {
            setCurrentFragment(matchViewFragment)
        }

        findViewById<BottomNavigationView>(R.id.nav_bar).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.matchView -> setCurrentFragment(matchViewFragment)
                R.id.messageListView -> setCurrentFragment(messageListFragment)
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
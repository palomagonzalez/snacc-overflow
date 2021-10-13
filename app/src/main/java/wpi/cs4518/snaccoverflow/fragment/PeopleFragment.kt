package wpi.cs4518.snaccoverflow.fragment

import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ListenerRegistration
import wpi.cs4518.snaccoverflow.AppConstants
import wpi.cs4518.snaccoverflow.ChatActivity

import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.recyclerview.item.PersonItem
import wpi.cs4518.snaccoverflow.util.FirestoreUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_people.*
import org.jetbrains.anko.support.v4.startActivity
import wpi.cs4518.snaccoverflow.model.ProfileRepository.Companion.init


private const val TAG = "wpi.PeopleFragment"

class PeopleFragment : Fragment() {

    private lateinit var userListenerRegistration: ListenerRegistration

    private lateinit var peopleSection: Section

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_people, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_people)
        recyclerView.layoutManager = LinearLayoutManager(this@PeopleFragment.context)
        recyclerView.adapter = GroupAdapter<ViewHolder>().apply {
            peopleSection = Section(listOf<Item>())
            add(peopleSection)
            setOnItemClickListener(onItemClick)
        }


        userListenerRegistration =
            FirestoreUtil.addUsersListener(this.requireActivity(), this::updateRecyclerView)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListenerRegistration)

    }

    private fun updateRecyclerView(items: List<Item>) {
        Log.d(TAG, "Updating view with ${items.size} messages")
        peopleSection.update(items)
    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItem) {
            startActivity<ChatActivity>(
                AppConstants.USER_NAME to item.person.name,
                AppConstants.USER_ID to item.userId
            )
        }
    }

}
package wpi.cs4518.snaccoverflow.recyclerview.item

import android.content.ClipData
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.Profile
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val person: Profile,
                 val userId: String,
                 private val context: Context)
    : Item() {

    public override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        TODO("Not yet implemented")
        person.name = viewHolder.itemView.findViewById<RecyclerView>(R.id.textView_name).toString()
    }

    override fun getLayout(): Int {
        TODO("Not yet implemented")
        R.layout.item_person
    }
}
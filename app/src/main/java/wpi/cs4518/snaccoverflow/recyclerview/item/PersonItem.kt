package wpi.cs4518.snaccoverflow.recyclerview.item

import android.content.ClipData
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.Profile
import kotlinx.android.synthetic.main.item_person.*
import wpi.cs4518.snaccoverflow.model.TextMessage

class PersonItem(val person: Profile,
                 val userId: String,
                 private val context: Context)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        person.name = viewHolder.itemView.findViewById<TextView>(R.id.textView_name).toString()
    }

    override fun getLayout(): Int {
        return R.layout.item_person
    }
}
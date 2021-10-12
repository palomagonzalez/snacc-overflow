package wpi.cs4518.snaccoverflow.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "profiles")
class Profile(
    @PrimaryKey val id: Int = 0,
    var name: String,
    var age: Int,
    var answerOne: String,
    var answerTwo: String,
    var answerThree: String,
    var profilePictureLocation: String?
) {
    override fun toString(): String {
        return "{\n" +
                "\tname: $name\n" +
                "\tage: $age\n" +
                "\tanswer_one: $answerOne\n" +
                "\tanswer_two: $answerTwo\n" +
                "\tanswer_three: $answerThree\n" +
                "\tprofile_picture: $profilePictureLocation\n" +
                "}"
    }
}
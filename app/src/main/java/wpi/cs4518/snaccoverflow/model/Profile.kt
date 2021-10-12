package wpi.cs4518.snaccoverflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//maybe make a data class?

@Entity(tableName = "profiles")
class Profile(
    @PrimaryKey val id: Int = 0,
    var name: String,
    var age: Int,
    var answerOne: String,
    var answerTwo: String,
    var answerThree: String
) {
    constructor():this(0,"", 0,"","", "")
}
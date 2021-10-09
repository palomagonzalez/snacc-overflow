package wpi.cs4518.snaccoverflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
class Profile(
    @PrimaryKey val id: Int = 0,
    var name: String,
    var age: Int,
    var answerOne: String,
    var answerTwo: String,
    var answerThree: String
) {}
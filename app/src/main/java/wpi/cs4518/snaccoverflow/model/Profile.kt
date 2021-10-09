package wpi.cs4518.snaccoverflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
class Profile(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val age: Int,
    val answerOne: String,
    val answerTwo: String,
    val answerThree: String
) {}
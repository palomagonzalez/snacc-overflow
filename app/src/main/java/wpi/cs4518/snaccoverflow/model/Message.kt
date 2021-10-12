package wpi.cs4518.snaccoverflow.model

import java.util.*

interface Message {
    val time: Date
    val senderId: String
}
package wpi.cs4518.snaccoverflow.model

import java.util.*

data class TextMessage (val text: String,
                        override val time: Date,
                        override val senderId: String)
    :Message {
        constructor() : this("", Date(0), "")
}
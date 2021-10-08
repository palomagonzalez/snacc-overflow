package wpi.cs4518.snaccoverflow.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MatchListViewModel : ViewModel() {

    data class Match(
        val id: Int,
        val name: String,
        val age: Int,
        val location: String,
        val answerOne: String,
        val answerTwo: String,
        val answerThree: String
    )

    private var id = 0

    fun getMatch(): LiveData<Match> {
        return MutableLiveData(randomMatch())
    }

    private fun randomMatch(): Match {
        val rng = Random()
        val name = NAMES[rng.nextInt(NAMES.size)]
        val age = rng.nextInt(52) + 18
        val location = LOCATIONS[rng.nextInt(LOCATIONS.size)]
        val ans1 = LANGS[rng.nextInt(LANGS.size)]
        val ans2 = IDES[rng.nextInt(IDES.size)]
        val ans3 = CELEB[rng.nextInt(CELEB.size)]
        return Match(id++, name, age, location, ans1, ans2, ans3)
    }

}

private val NAMES = listOf(
    "Deegan",
    "Zander",
    "Derrick",
    "Augustus",
    "Jameson",
    "Draven",
    "Phillip",
    "Cael",
    "Jermaine",
    "Jovani",
    "Zaiden",
    "Easton",
    "Jayvon",
    "Evan",
    "Scott",
    "Isaiah",
    "Tyson",
    "Isaac",
    "Neil",
    "Bronson",
    "Yair",
    "Aiden",
    "Sergio",
    "Zechariah",
    "Frederick"
)
private val LOCATIONS = listOf(
    "Malibu (US)",
    "Boonesborough (US)",
    "Branson (US)",
    "Brooklyn (US)",
    "Boca Raton (US)",
    "Asbury Park (US)",
    "Wewoka (US)",
    "Manistee (US)",
    "Augusta (US)",
    "San Bernardino (US)",
    "Titusville (US)",
    "Rawlins (US)",
    "Mankato (US)",
    "Orono (US)",
    "Bristol (US)",
    "Eagle Pass (US)",
    "Coos Bay (US)",
    "Barnstable (US)",
    "Anniston (US)",
    "Oakland (US)",
    "Borger (US)",
    "Council Grove (US)",
    "Aiken (US)",
    "Ely (US)",
    "Libertyville (US)"
)
private val LANGS = listOf(
    "Java",
    "Kotlin",
    "Python",
    "C++",
    "C",
    "Go",
    "Racket",
    "Rust",
    "PHP",
    "Javascript"
)
private val IDES = listOf(
    "IntelliJ",
    "Android Studio",
    "Dr Racket",
    "Eclipse",
    "Python IDLE",
    "XCode",
    "Visual Studio",
    "VS Code"
)
private val CELEB = listOf(
    "Jeff Bezos",
    "Elon Musk",
    "Mark Zuckerburg",
    "Bill Gates",
    "Steve Jobs (rip)",
    "Gregor"
)
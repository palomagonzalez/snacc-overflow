package wpi.cs4518.snaccoverflow.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import java.lang.IllegalStateException

private const val DATABASE_NAME = "profile-database"
private const val TAG = "wpi.ProfileRepository"

class ProfileRepository private constructor(context: Context){

    private val database: ProfileDatabase = Room.databaseBuilder(
        context.applicationContext,
        ProfileDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val dao = database.profileDAO()

    init {
        if (!dao.doesProfileExist()) {
            dao.setInitialProfile()
        }
        Log.d(TAG, "Initialized")
    }

    fun getProfile(): LiveData<Profile> {
        return dao.loadProfile()
    }

    fun saveProfile(profile: Profile) {
        dao.saveProfile(profile.name, profile.age, profile.answerOne, profile.answerTwo, profile.answerThree)
    }

    companion object {
        private var INSTANCE: ProfileRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ProfileRepository(context)
            }
        }

        fun get(): ProfileRepository {
            return INSTANCE?:
            throw IllegalStateException("ProfileRepository has not been initialized")
        }

    }

    @Database(entities = [Profile::class], version = 1)
    abstract class ProfileDatabase: RoomDatabase() {

        abstract fun profileDAO(): ProfileDAO

    }

    @Dao
    interface ProfileDAO {

        @Query("insert into profiles (id, name, age, answerOne, answerTwo, answerThree) values (0, 'Your Name', 21, 'Kotlin', 'Android Studio', 'Laurie Leshin')")
        fun setInitialProfile()

        @Query("SELECT EXISTS(SELECT 1 FROM profiles)")
        fun doesProfileExist(): Boolean

        @Query("select * from profiles limit 1")
        fun loadProfile(): LiveData<Profile>

        @Query("update profiles set name=:name, age=:age, answerOne=:answerOne, answerTwo=:answerTwo, answerThree=:answerThree")
        fun saveProfile(name: String, age: Int, answerOne: String, answerTwo: String, answerThree: String)

    }

}
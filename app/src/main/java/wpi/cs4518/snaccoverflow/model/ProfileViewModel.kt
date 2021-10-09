package wpi.cs4518.snaccoverflow.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    val profile: LiveData<Profile> = ProfileRepository.get().getProfile()

    fun save() {
        profile.value?.let { ProfileRepository.get().saveProfile(it) }
    }

}
package wpi.cs4518.snaccoverflow.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import wpi.cs4518.snaccoverflow.R
import wpi.cs4518.snaccoverflow.model.Profile
import wpi.cs4518.snaccoverflow.model.ProfileRepository
import wpi.cs4518.snaccoverflow.model.ProfileViewModel
import java.io.File
import java.io.IOException
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "wpi.EditProfile"
private const val TAKE_PROFILE_PICTURE_ID = 4562

// TODO: make gps location appear
// TODO: make camera function

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        loadCurrentProfile(view)
        setupEditTextHandlers(view)
        setupCameraButton(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "Saving view model")
        Thread {
            viewModel.save()
        }.start()
        Toast.makeText(context, "Saved Profile!", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAKE_PROFILE_PICTURE_ID) {
            view?.let { setProfileImage(it, getImage(currentPhotoPath)) }
        }
    }

    private fun setupCameraButton(view: View) {
        val button =
            view.findViewById<ImageButton>(R.id.buttonTakeProfilePicture).setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { photoIntent ->
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d(TAG, "ERROR CREATING FILE: $ex")
                        null
                    }
                    Log.d(TAG, "Saving image to ${photoFile?.absolutePath}")
                    photoFile?.also {
                        val photoURI = FileProvider.getUriForFile(
                            requireContext(),
                            "wpi.cs4518.snaccoverflow.fileprovider",
                            it
                        )
                        viewModel.profile.observe(
                            viewLifecycleOwner,
                            {
                                profile -> profile.profilePictureLocation = currentPhotoPath
                            }
                        )
                        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(photoIntent, TAKE_PROFILE_PICTURE_ID)
                    }
                }
            }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun getImage(path: String): Bitmap {
        val exifInterface = ExifInterface(path)
        var rotation = 0.0f;
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270f
        }
        val source = BitmapFactory.decodeFile(path)
        val matrix = Matrix()
        matrix.postRotate(rotation)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun setupEditTextHandlers(view: View) {
        val textHandler = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun afterTextChanged(p0: Editable?) {
                saveCurrentProfile()
            }
        }
        val ids = arrayOf(
            R.id.labelEditInfo,
            R.id.labelEditAnswerOne,
            R.id.labelEditAnswerTwo,
            R.id.labelEditAnswerThree
        )
        for (id in ids) {
            view.findViewById<EditText>(id).addTextChangedListener(textHandler)
        }
    }

    private fun loadCurrentProfile(view: View) {
        viewModel.profile.observe(
            viewLifecycleOwner,
            {
                profile -> updateUI(view, profile)
            }
        )
    }

    private fun saveCurrentProfile() {
        val info = view?.findViewById<EditText>(R.id.labelEditInfo)?.text?.toString()
        val answerOne: String = view?.findViewById<EditText>(R.id.labelEditAnswerOne)?.text.toString()
        val answerTwo: String = view?.findViewById<EditText>(R.id.labelEditAnswerTwo)?.text.toString()
        val answerThree: String = view?.findViewById<EditText>(R.id.labelEditAnswerThree)?.text.toString()
        val name: String = info?.split(",")?.get(0)?: "Your Name"
        val age: Int = try {
            info?.split(", ")?.get(1)?.toInt() ?: 21 }
        catch (e: NumberFormatException) {
            21
        }
        viewModel.profile.value?.name = name
        viewModel.profile.value?.age = age
        viewModel.profile.value?.answerOne = answerOne
        viewModel.profile.value?.answerTwo = answerTwo
        viewModel.profile.value?.answerThree = answerThree
    }

    private fun updateUI(view: View, profile: Profile) {
        val infoLabel = view.findViewById<EditText>(R.id.labelEditInfo)
        val answerOneLabel = view.findViewById<EditText>(R.id.labelEditAnswerOne)
        val answerTwoLabel = view.findViewById<EditText>(R.id.labelEditAnswerTwo)
        val answerThreeLabel = view.findViewById<EditText>(R.id.labelEditAnswerThree)
        infoLabel?.setText("${profile.name}, ${profile.age}")
        answerOneLabel?.setText(profile.answerOne)
        answerTwoLabel?.setText(profile.answerTwo)
        answerThreeLabel?.setText(profile.answerThree)
        if (profile.profilePictureLocation != null) {
            setProfileImage(view, getImage(profile.profilePictureLocation.toString()))
        }
    }

    private fun setProfileImage(view: View, bitmap: Bitmap) {
        view.findViewById<ImageView>(R.id.imageEditProfile).setImageBitmap(bitmap)
    }

}
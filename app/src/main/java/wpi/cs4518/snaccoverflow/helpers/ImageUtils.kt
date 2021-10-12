package wpi.cs4518.snaccoverflow.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

class ImageUtils {

    companion object {
        fun getImage(path: String): Bitmap {
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
    }

}
package com.kolisnichenko2828.profiles.data.profile

import android.content.Context
import androidx.core.net.toUri
import com.kolisnichenko2828.profiles.domain.interfaces.LocalFileStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import java.util.concurrent.CancellationException

class ProfileFileStorageImpl(
    private val context: Context
) : LocalFileStorage {
    override suspend fun saveImage(uri: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = "${UUID.randomUUID()}.jpg"
                val file = File(context.filesDir, fileName)

                val inputStream = context.contentResolver.openInputStream(uri.toUri())
                if (inputStream == null) throw okio.FileNotFoundException()

                inputStream.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                Result.success(file.absolutePath)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
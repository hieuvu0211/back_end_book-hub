package com.book.myapplication.components.Settings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.userService
import com.book.myapplication.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun ChooseImage(navController: NavController) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    val dataUserStore = UserData(context)
    val userGetLocal by dataUserStore.getDataUserFromLocal.collectAsState(null)
    var state by rememberSaveable { mutableStateOf("") }
    if (state == "1") {
        Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()
        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate("account")
        }
    } else if (state == "0") {
        Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
    }
    Column {
        ImagePickerWithPermission { uri ->
            selectedImageUri = uri
        }

        selectedImageUri?.let { uri ->
            val imagePainter = rememberAsyncImagePainter(model = uri)
            Image(painter = imagePainter, contentDescription = null)


            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val isUploaded = uploadImage(
                            context,
                            uri,
                            "avatar",
                            "1",
                            userGetLocal?.user_id.toString()
                        )
                        state = if (isUploaded) {
                            "1"
                        } else {
                            "0"
                        }
                    } catch (e: Exception) {
                        HandleError(e)
                    }

                }
            }) {
                Text("Upload Image")
            }
        }
    }
}

@Composable
fun ImagePickerWithPermission(onImageSelected: (Uri?) -> Unit) {
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            // Handle permission denied case
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }



    Button(onClick = {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                imagePickerLauncher.launch("image/*")
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }) {
        Text("Select Image")
    }
}

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        inputStream.readBytes()
    }
}

suspend fun uploadImage(
    context: Context,
    uri: Uri,
    key: String,
    value: String,
    idUser: String
): Boolean {
    val isUploaded = mutableStateOf("")
    val byteArray = uriToByteArray(context, uri) ?: return false

    val requestBody = byteArray.toRequestBody("image/*".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

    val keyPart = key.toRequestBody("text/plain".toMediaTypeOrNull())
    val valuePart = value.toRequestBody("text/plain".toMediaTypeOrNull())

    val userLocalStore = UserData(context)
    try {
        val response = userService.uploadImage(body, keyPart, valuePart, idUser)

        if (response.isSuccessful) {
            userLocalStore.setDataUserInLocal(response.body() as User)
            return true
        } else {
            HandleError(Exception("Error: ${response.message()}"))
            return false
        }
    } catch (e: Exception) {
        HandleError(e)
        return false
    }
}
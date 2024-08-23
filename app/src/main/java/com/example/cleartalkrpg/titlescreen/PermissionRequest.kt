package com.example.cleartalkrpg.titlescreen

import android.Manifest.permission.RECORD_AUDIO
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun requestAudioPermission() {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

        }
    )

    permissionLauncher.launch(RECORD_AUDIO)
}
package com.ian.iicontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.*
import com.ian.iicontacts.screens.home.HomeScreen
import com.ian.iicontacts.ui.theme.IIContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }
}

@Composable
fun MainContent(darkMode: Boolean = isSystemInDarkTheme()) {
    IIContactsTheme(darkTheme = darkMode) {
        HomeScreen()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MainContent(true)
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LightPreview() {
    MainContent(false)
}
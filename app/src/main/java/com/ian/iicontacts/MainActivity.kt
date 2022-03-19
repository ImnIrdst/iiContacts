package com.ian.iicontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
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
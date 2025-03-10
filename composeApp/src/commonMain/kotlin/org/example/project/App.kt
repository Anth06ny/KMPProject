package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.ui.AppNavigation
import org.example.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        AppNavigation()
    }
}


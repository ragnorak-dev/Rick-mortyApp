package com.ragnorak.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ragnorak.ui.R

@Composable
fun ErrorComponent(message: String? = null) {
   Box(
       modifier = Modifier
           .fillMaxSize(),
       contentAlignment = Alignment.Center
   ) {

       Text(text = message ?: stringResource(id = R.string.ERROR_general))
   }
}
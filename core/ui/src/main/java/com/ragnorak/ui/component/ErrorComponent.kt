package com.ragnorak.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ragnorak.ui.R
import com.ragnorak.ui.errormanage.UiError

@Composable
fun ErrorComponent(uiError: UiError) {
   Column(
       modifier = Modifier
           .fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {

       when (uiError) {
           is UiError.Resource -> Text(text = stringResource(id = uiError.resId))
           is UiError.WithAction -> {
               Text(text = stringResource(id = uiError.resId))
               Button(onClick = uiError.onRetry) {
                   Text(text = stringResource(id = R.string.GLOBAL_retry))
               }
           }
       }
   }
}
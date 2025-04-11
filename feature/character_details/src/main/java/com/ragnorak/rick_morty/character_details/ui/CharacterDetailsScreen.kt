package com.ragnorak.rick_morty.character_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.ragnorak.ui.R
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.component.ErrorComponent
import com.ragnorak.ui.component.LoadingComponent

@Composable
fun CharacterDetailsScreen(
    uiState: ViewState<CharacterDetailsModel> = ViewState.Idle,
    onRetry: () -> Unit = {},
) {

    when (uiState) {
        is ViewState.Loading -> LoadingComponent()
        is ViewState.Success -> CharacterDetailsView(uiState.data)
        is ViewState.Error -> ErrorComponent()
        else -> {}

    }

}

@Composable
fun CharacterDetailsView(data: CharacterDetailsModel) {

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.paddingM))
            .fillMaxSize()
    ) {
        Text(text = data.name)
        Text(text = data.status)
        Text(text = data.species)
        Text(text = data.type)
        Text(text = data.gender)
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreen(
        uiState = ViewState.Success(
            CharacterDetailsModel(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                image = ""
            )
        ),
        onRetry = {}
    )
}

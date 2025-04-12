package com.ragnorak.rick_morty.character_details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.ui.ComponentIdentifier
import com.ragnorak.ui.R
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.component.ErrorComponent
import com.ragnorak.ui.component.LoadingComponent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: ViewState<CharacterDetailsModel> = ViewState.Idle,
) {

    Box(
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is ViewState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    LoadingComponent()
                }
        }
            is ViewState.Success -> CharacterDetailsView(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                uiState.data
            )

            is ViewState.Error -> ErrorComponent(uiState.uiError)
            else -> {}
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetailsView(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    data: CharacterDetailsModel
) {

    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "image-${data.image}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxWidth(),
                model = data.image,
                contentDescription = data.name,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.paddingM))
            ) {
                val items = listOf(
                    R.string.character_detail_name to data.name,
                    R.string.character_detail_status to data.status,
                    R.string.character_detail_species to data.species,
                    R.string.character_detail_type to data.type,
                    R.string.character_detail_gender to data.gender
                )

                items.forEach { (labelRes, value) ->
                    CardInfo(label = stringResource(id = labelRes), value = value)
                }
            }
        }
    }
}

@Composable
private fun CardInfo(label: String, value: String) {
    val tag = getTestTag(label)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.paddingS))
            .then(if (tag.isNotEmpty()) Modifier.testTag(tag) else Modifier)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.paddingM))) {
            Text(text = label, style = MaterialTheme.typography.labelSmall)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun getTestTag(label: String): String {
    val tag = when (label) {
        stringResource(id = R.string.character_detail_name) -> ComponentIdentifier.DETAILS_CARD_FIELD_NAME
        stringResource(id = R.string.character_detail_status) -> ComponentIdentifier.DETAILS_CARD_FIELD_STATUS
        stringResource(id = R.string.character_detail_species) -> ComponentIdentifier.DETAILS_CARD_FIELD_SPECIES
        stringResource(id = R.string.character_detail_type) -> ComponentIdentifier.DETAILS_CARD_FIELD_TYPE
        stringResource(id = R.string.character_detail_gender) -> ComponentIdentifier.DETAILS_CARD_FIELD_GENDER
        else -> ""
    }
    return tag
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun CharacterDetailsScreenPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            CharacterDetailsScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this,
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
            )
        }
    }
}

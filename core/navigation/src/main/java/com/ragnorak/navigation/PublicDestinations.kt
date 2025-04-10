package com.ragnorak.navigation

import kotlinx.serialization.Serializable

interface Route

@Serializable
data object CharacterListDestination : Route
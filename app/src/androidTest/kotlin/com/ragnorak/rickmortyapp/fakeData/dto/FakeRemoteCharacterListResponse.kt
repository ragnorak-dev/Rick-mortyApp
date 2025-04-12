package com.ragnorak.rickmortyapp.fakeData.dto

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.api.response.CharacterListDto
import com.ragnorak.api.response.InfoDto
import com.ragnorak.api.response.LocationDto
import com.ragnorak.api.response.OriginDto

val fakeCharacterListDto = CharacterListDto(
    info = InfoDto(
        count = 4,
        pages = 1,
        next = null,
        prev = null
    ),
    results = listOf(
        CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        ),
        CharacterDto(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/2",
            created = "2017-11-04T18:50:21.651Z"
        ),
        CharacterDto(
            id = 3,
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = OriginDto(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = LocationDto(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/6"),
            url = "https://rickandmortyapi.com/api/character/3",
            created = "2017-11-04T19:09:56.428Z"
        ),
        CharacterDto(
            id = 4,
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = OriginDto(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = LocationDto(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/6"),
            url = "https://rickandmortyapi.com/api/character/4",
            created = "2017-11-04T19:22:43.665Z"
        )
    )
)


val fakeCharacterListByNameDto = CharacterListDto(
    info = InfoDto(
        count = 10,
        pages = 1,
        next = null,
        prev = null
    ),
    results = listOf(
        CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        ),
        CharacterDto(
            id = 8,
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/28"),
            url = "https://rickandmortyapi.com/api/character/8",
            created = "2017-11-04T20:03:34.737Z"
        ),
        CharacterDto(
            id = 15,
            name = "Alien Rick",
            status = "unknown",
            species = "Alien",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/15.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/10"),
            url = "https://rickandmortyapi.com/api/character/15",
            created = "2017-11-04T20:56:13.215Z"
        ),
        CharacterDto(
            id = 19,
            name = "Antenna Rick",
            status = "unknown",
            species = "Human",
            type = "Human with antennae",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto("unknown", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/19.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/10"),
            url = "https://rickandmortyapi.com/api/character/19",
            created = "2017-11-04T22:28:13.756Z"
        ),
        CharacterDto(
            id = 22,
            name = "Aqua Rick",
            status = "unknown",
            species = "Humanoid",
            type = "Fish-Person",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/22.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/10",
                "https://rickandmortyapi.com/api/episode/22",
                "https://rickandmortyapi.com/api/episode/28"
            ),
            url = "https://rickandmortyapi.com/api/character/22",
            created = "2017-11-04T22:41:07.171Z"
        ),
        CharacterDto(
            id = 48,
            name = "Black Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/48.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/22",
                "https://rickandmortyapi.com/api/episode/28"
            ),
            url = "https://rickandmortyapi.com/api/character/48",
            created = "2017-11-05T11:15:26.044Z"
        ),
        CharacterDto(
            id = 56,
            name = "Bootleg Portal Chemist Rick",
            status = "Dead",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/56.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/28"),
            url = "https://rickandmortyapi.com/api/character/56",
            created = "2017-11-05T11:34:16.447Z"
        ),
        CharacterDto(
            id = 69,
            name = "Commander Rick",
            status = "Dead",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/69.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/22"),
            url = "https://rickandmortyapi.com/api/character/69",
            created = "2017-11-30T11:28:06.461Z"
        ),
        CharacterDto(
            id = 72,
            name = "Cool Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("Earth (K-83)", "https://rickandmortyapi.com/api/location/26"),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/72.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/28"),
            url = "https://rickandmortyapi.com/api/character/72",
            created = "2017-11-30T11:41:11.542Z"
        ),
        CharacterDto(
            id = 74,
            name = "Cop Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = OriginDto("unknown", ""),
            location = LocationDto(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/74.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/28"),
            url = "https://rickandmortyapi.com/api/character/74",
            created = "2017-11-30T11:48:18.950Z"
        )
    )
)
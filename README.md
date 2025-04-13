# Rick & Morty App

Android application built with Jetpack Compose that consumes the [Rick and Morty API](https://rickandmortyapi.com/). This app lists characters, allows viewing details, performs search queries.

---

## 🎓 Technologies Used

- **Kotlin** (100%)
- **Jetpack Compose + Material 3**
- **Jetpack Compose Navigation**
- **ViewModel + StateFlow (MVI)**
- **Room** (local cache)
- **Retrofit + Kotlinx Serialization**
- **Hilt** (dependency injection)
- **Coil** (image loading)
- **Detekt** (static code analysis)
- **JUnit + Turbine** (testing)

## 🚀 Architecture

The app follows a simple but robust **MVI (Model-View-Intent)** pattern:

View (Composable)
↓    ↑
Intent   ViewState
↓    ↑
ViewModel
↓
Repository
├─️ RemoteDataSource (Retrofit)
└─️ LocalDataSource (Room)

The `ViewModel` exposes a `StateFlow` with the current screen state. Each user action is modeled as an `Intent` processed by the `ViewModel`.

---

## 📊 Features

- Infinite scrolling character list
- Remote search by name
- Character detail with shared element transitions (`SharedTransitionLayout`)
- Pull to refresh with proper state handling (initial load vs refresh)
- Cache-first strategy: checks Room before Retrofit, validated with timestamp
- Persistent paginated data in Room with `PaginationInfo`
- Decoupled error mapping with `ErrorManager`
- Smooth card and element animations on detail screen


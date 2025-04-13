# Rick & Morty App

Android application built with Jetpack Compose that consumes the [Rick and Morty API](https://rickandmortyapi.com/). This app lists characters, allows viewing details, performs search queries.

## 🧪 Running the Project

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/ragnorak-dev/Rick-mortyApp.git
   ```
   ### 1. Open in Android Studio
- The project was created using **Android Studio Meerkat | 2024.3.1**
- Kotlin version: **2.1.10**
- JDK: **17**

### 2. Run the App
- Select the `app` module
- Choose an emulator or physical device and click **Run**

### 3. Test the Project

- Run unit tests:
  ```bash
  ./gradlew testDebugUnitTest

- Executes integration tests (instrumented):
     ```bash
     ./gradlew connectedDebugAndroidTest
     ```

---

## 🎓 Technologies Used

- **Kotlin 2.1.10** (100%)
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

![RickAndMorty_modules_diagram drawio](https://github.com/user-attachments/assets/a832f452-2797-40c6-ae64-07fe9c718126)

---

## 📊 Features

- Infinite scrolling character list
- Remote search by name
- Character detail with shared element transitions (`SharedTransitionLayout`)
- Pull to refresh with proper state handling (initial load vs refresh)
- Cache-first strategy: checks Room before Retrofit, validated with timestamp
- Persistent paginated data in Room.
- Decoupled error mapping with `ErrorManager`

---

## ✅ Best Practices

- Layered structure by feature, following **Clean Architecture** principles:
    - Each `feature` module (`character_list`, `character_details`, etc.) is separated into `data`, `domain`, and `ui` layers.
    - `core` modules provide reusable components:
        - `core:network`: Retrofit, serialization, interceptors
        - `core:persistence`: Room setup and DAOs
        - `core:navigation`: app navigation config
        - `core:ui`: design system components
- `ViewState` representation for loading/success/error states
- Detekt configured for Jetpack Compose
- Unit tests in ViewModel, repository, and error handling
- Integration test with the happy patch list and navigation to details.



https://github.com/user-attachments/assets/5018404e-4052-4ce5-b3d0-52ac976b2596



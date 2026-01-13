# 🍳 Chef - Smart Recipe Finder

**Chef** is a modern, offline-first Android application built with **Jetpack Compose** that allows users to discover, search, and save recipes. It leverages the **Single Source of Truth (SSOT)** principle to provide a seamless user experience even with poor network connectivity.

## 📱 Screenshots
| Home Screen | Search | Recipe Details | Favorites |
|:---:|:---:|:---:|:---:|
| <img src="ss1.png" width="200"/> | <img src="ss2.jpeg" width="200"/> | <img src="ss3.png" width="200"/> | <img src="ss4.png" width="200"/> |

## ✨ Key Features

* **⚡ Offline-First Architecture:** Categories are cached locally using **Room Database**, ensuring instant load times on subsequent launches.
* **🔍 Live Search:** Real-time recipe searching using **TheMealDB API**.
* **❤️ Favorites System:** Users can persist their favorite recipes locally using Room.
* **🎨 Modern UI:** Built entirely with **Jetpack Compose** and **Material 3** components.
* **🔄 Reactive Data Layer:** Uses **Kotlin Coroutines** and **StateFlow** for seamless data updates.

## 🛠️ Tech Stack

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Dependency Injection:** Hilt
* **Network:** Retrofit & OkHttp
* **Local Storage:** Room Database (SQLite)
* **Image Loading:** Coil
* **Concurrency:** Coroutines & Flow
* **Navigation:** Jetpack Navigation Compose

## 🏗️ Architecture

The app follows the **Repository Pattern** to separate data logic from UI logic.

1.  **UI Layer (Compose):** Observes `StateFlow` from the ViewModel.
2.  **ViewModel:** Manages UI state and handles business logic.
3.  **Repository:** Acts as the single source of truth, deciding whether to fetch data from the **Local Database** (Room) or the **Network** (Retrofit).
4.  **Data Source:**
    * *Remote:* TheMealDB API
    * *Local:* Room Database (Cached Categories & Favorites)

## 🚀 How to Run

1.  Clone the repository:
    ```bash
    git clone [https://github.com/your-username/Chef-Recipe-App.git](https://github.com/your-username/Chef-Recipe-App.git)
    ```
2.  Open in **Android Studio**.
3.  Sync Gradle files.
4.  Run on an Emulator or Physical Device.

## 🔮 Future Improvements
* [ ] Implement Unit Tests for ViewModels.
* [ ] Add "Filter by Ingredient" feature.
* [ ] Migrate to Paging 3 for large lists.

---

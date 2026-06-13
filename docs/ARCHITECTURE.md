# Architecture and design

This is a short note on how the app is built and why.

## Layers

The app follows clean architecture with three layers, split into Gradle modules:

```
:app             starts the app, sets up navigation and dependency injection
:feature-player  UI: player list (Views) and player detail (Compose) + ViewModels
:domain          models, repository interface, use cases (plain Kotlin)
:data            network, database, mappers, repository implementation
```

Dependencies point inward. `app` and `feature-player` depend on `domain`. `data` also depends on `domain`. The `domain` module is plain Kotlin with no Android, Retrofit, or Room code, so the business rules don't depend on any framework.

## Data flow

```
Network (Retrofit)  ->  Database (Room)  ->  domain models  ->  ViewModel  ->  UI
                              ^                                     |
                              +------------ observed as Flow -------+
```

The UI reads from the database, not from the network. A refresh fetches from the API and saves the result into Room. The screen listens to Room with a `Flow`, so it updates by itself when the data changes.

## Design decisions

**Offline-first.** Room is the single source of truth. Because the UI is wired to the cache, a failed network call doesn't blank the screen. The last saved data stays visible and the error is shown as a message instead of crashing.

**Sync on reconnect.** A `ConnectivityObserver` reports network status. The list ViewModel refreshes when the connection becomes available. The observer interface lives in `domain` and the Android implementation lives in `data`, so the domain layer stays framework-free.

**Separate models per layer.** The network model (`PlayerDto`), database model (`PlayerEntity`), and domain model (`Player`) are different types with mappers between them. A change in the JSON shape stays in the data layer and does not reach the UI.

**MVVM.** ViewModels expose UI state as a `StateFlow`. The UI just renders that state. Search filters by name or club in the ViewModel.

**Hybrid UI.** The list uses XML with data binding (a RecyclerView fits a scrolling list). The detail screen uses Jetpack Compose (the animated stat bars are simpler there). Both are fragments in one navigation graph.

**Dependency injection with Koin.** Each layer has its own Koin module. The app wires them together at startup. This keeps construction in one place and makes the ViewModels easy to test.

**Build-time API branch.** The mock data lives on GitHub. The branch to read from is decided at build time (develop build uses develop data, everything else uses main data), so the app works on both branches.

**One source for theming.** Colors are defined once using Material 3 role names. The screens use theme attributes (and `MaterialTheme.colorScheme` in Compose) instead of hard-coded colors, so the app supports both light and dark themes and follows the system setting.

## Testing

Unit tests use JUnit, MockK, Turbine, and kotlinx-coroutines-test. `PlayerDetailViewModelTest` uses Turbine to check the stats the ViewModel sends to the UI.

## Trade-offs

- The data is read-only. There is no login or write path.
- The mock API is static JSON on GitHub, not a real server.
- Player photos are hotlinked from Wikimedia Commons. Wikimedia rate-limits
  these requests (HTTP 429), so on a fresh install a few images may not load
  at first and fall back to the placeholder. A real app would host its own
  images or use a CDN.

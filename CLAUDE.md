# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew assembleDebug          # Debug build
./gradlew assembleRelease        # Release build
./gradlew test                   # Unit tests (all modules)
./gradlew :module:test           # Unit tests for a specific module
./gradlew connectedAndroidTest   # Instrumented tests (requires device/emulator)
./gradlew build                  # Full build with all checks
```

## Architecture

Clean Architecture with MVI (Model-View-Intent) and Unidirectional Data Flow.

### Module Responsibilities

| Module | Type | Responsibility |
|--------|------|----------------|
| `app` | Android Application | Entry point, Room DB setup, Hilt DI modules, theme |
| `core` | Kotlin library | Common utilities, extensions, coroutine dispatchers |
| `domain` | Kotlin library | Domain models, repository interfaces, use cases |
| `data` | Android library | Repository implementations, Room DAO, Health Connect data source, mappers |
| `presentation` | Android library | Compose screens, ViewModels, Glance widget UI |
| `sync` | Android library | WorkManager worker for Health Connect background sync |

### Dependency Direction

```
presentation → domain ← data
app → all modules
sync → domain, data
```

Neither `domain` nor `core` depend on Android framework APIs.

### Data Flow

**Write:** UI → Intent → ViewModel → UseCase → Repository → DataSource → Room / Health Connect

**Read:** Room → DataSource → Repository → UseCase → ViewModel → StateFlow → Compose UI / Widget

### Strict Rules

- Presentation accesses data only through use cases — never DAOs, Room, or Health Connect directly.
- `UseCase → DataSource` dependency is forbidden; use cases call repositories.
- All UI renders from immutable state (`StateFlow`); one-time events (navigation, toasts) go through `Effect`.
- Use `Flow`/`StateFlow` — no LiveData, no RxJava.
- Simple settings (e.g., monthly distance goal) → DataStore. Workout records → Room.

### MVI Pattern

Each screen has three types:

- **State** — immutable data class rendered by Compose
- **Intent** — sealed class representing user actions
- **Effect** — sealed class for one-time events (navigation, snackbar, toast)

ViewModels are `@HiltViewModel`-annotated and inject use cases.

### Compose Rules

- Screens are stateless whenever possible.
- ViewModel owns state.
- Collect StateFlow using collectAsStateWithLifecycle().
- Do not launch business logic directly from composables.
- Use Material3.

### Coroutine Rules

- ViewModel uses viewModelScope.
- Use Cases are suspend functions unless reactive streams are required.
- Repository exposes Flow for observable data.
- Avoid GlobalScope.

### Source of Truth

Health Connect is an external source only.

All screens and widgets read data exclusively from Room.

Data flow:

Health Connect → Room → Repository → UseCase → UI

## Key Libraries

- **Hilt** — DI framework; use `@HiltAndroidApp`, `@HiltViewModel`, `@AndroidEntryPoint`
- **Room** — local database with `cycling_workouts` table; sync strategy is `INSERT OR REPLACE` (Health Connect is always source of truth)
- **Glance** — home screen widget in `presentation/widget`; widget holds no business logic, reads through use cases
- **WorkManager** — `HealthConnectSyncWorker` in `sync` module; triggered on first launch, hourly, and on widget refresh
- **Health Connect** — external data source for `BIKING` and `MOUNTAIN_BIKING` activity types; never read by UI directly
- **KSP** — annotation processor for both Hilt and Room

## Testing

- **Unit tests** — use cases, repository logic, mappers (in `domain` and `data`)
- **Integration tests** — Room DAO, Health Connect data source (instrumented)
- **UI tests** — Compose UI Tests in `presentation`
- **Widget tests** — Glance Tests

Business logic validation is prioritized in the `domain` layer.

## Current MVP Scope

- Health Connect synchronization
- Monthly statistics
- Workout history
- Monthly goal
- Glance widget
- Hourly synchronization

Avoid introducing features outside this scope unless explicitly requested.

## Project Context

Full feature and architecture documentation lives in `descriptions/`:
- `descriptions/project-overview.md` — features and first-launch flow @descriptions/project-overview.md
- `descriptions/architecture.md` — authoritative architecture reference @descriptions/architecture.md

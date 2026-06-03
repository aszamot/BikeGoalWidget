# Architecture

## Technology Stack

* Kotlin
* Jetpack Compose
* Glance App Widget
* Coroutines
* Flow / StateFlow
* Hilt
* Room
* DataStore
* WorkManager
* Health Connect
* Clean Architecture
* MVI
* UDF (Unidirectional Data Flow)

---

# Architecture Overview

The project follows Clean Architecture principles.

## Layers

### Presentation

Responsibilities:

* Compose UI
* ViewModels
* MVI state management
* Glance widget implementation

Presentation never accesses data sources directly.

All interactions go through Use Cases.

---

### Domain

Contains:

* Domain models
* Repository contracts
* Use Cases

The domain layer is platform-independent and contains business logic only.

---

### Data

Contains:

* Repository implementations
* Room data source
* Health Connect data source
* Mappers

The data layer is responsible for transforming and persisting data.

---

# Module Structure

```text
app
├── database
├── di
├── theme

sync
├── worker

core
├── common
├── extensions
├── utils

data
├── datasource
│   ├── local
│   └── healthconnect
├── repository
├── mapper

domain
├── model
├── repository
├── usecase

presentation
├── screens
    ├── dashboard
    └── settings
├── widget
├── ui
```

---

# UI Pattern

The application uses:

* MVI
* UDF

## State

UI is rendered exclusively from immutable state.

## Intent

User actions are represented as intents.

## Effect

Used for one-time events such as:

* Navigation
* Snackbar messages
* Toasts

---

# Data Flow

Write flow:

```text
UI
 ↓
Intent
 ↓
ViewModel
 ↓
UseCase
 ↓
Repository
 ↓
DataSource
 ↓
Room / Health Connect
```

Read flow:

```text
Room
 ↓
DataSource
 ↓
Repository
 ↓
UseCase
 ↓
ViewModel
 ↓
StateFlow
 ↓
Compose UI / Widget
```

---

# Synchronization

## HealthConnectSyncWorker

Responsible for synchronizing workout data from Health Connect.

Triggered by:

* First application launch
* Hourly periodic work
* Widget refresh action

The worker updates Room and never communicates with UI directly.

---

# Persistence

## Room Database

Table:

```text
cycling_workouts
```

Fields:

* id
* startTime
* endTime
* durationSeconds
* distanceMeters
* caloriesKcal
* updatedAt

Synchronization strategy:

```sql
INSERT OR REPLACE
```

Health Connect always overwrites existing local records.

---

## DataStore

Used for:

* Monthly distance goal
* Future application settings

Simple application settings should be stored in DataStore rather than Room.

---

# Widget

Technology:

* Glance App Widget

The widget does not contain business logic.

Data flow:

```text
UseCase
 ↓
Repository
 ↓
Room
```

The refresh button schedules a synchronization worker.

---

# Health Connect

Supported activity types:

* BIKING
* MOUNTAIN_BIKING

Health Connect serves as the external source of workout data.

The application stores synchronized data locally and reads exclusively from Room.

---

# Repository Rules

Repositories are the only entry point for data access.

Forbidden dependencies:

* UI → DAO
* UI → Room
* UI → Health Connect
* UseCase → DataSource

Required flow:

```text
UI
 ↓
UseCase
 ↓
Repository
 ↓
DataSource
```

---

# Reactive Programming

Preferred:

* Flow
* StateFlow

Avoid:

* LiveData
* RxJava

---

# Dependency Injection

Framework:

* Hilt

Injected components:

* Repositories
* Use Cases
* DAOs
* Worker dependencies
* Health Connect client

---

# Testing Strategy

## Unit Tests

* Use Cases
* Repository logic
* Mappers

## Integration Tests

* Room
* Health Connect data source

## UI Tests

* Compose UI Tests

## Widget Tests

* Glance Tests

Business logic validation should be prioritized in the Domain layer.

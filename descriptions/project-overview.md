# Cycling Stats Widget

## Project Overview

Cycling Stats Widget is an Android application that integrates with Health Connect to collect cycling workout data and present monthly statistics in both the application UI and a home screen widget.

The application acts as a local data layer on top of Health Connect. All UI components consume data exclusively from a local Room database.

---

# Core Features

## Health Connect Synchronization

The application imports cycling workouts from Health Connect.

For each workout, the following information is stored:

* Workout ID
* Start time
* End time
* Duration
* Distance
* Calories burned

Health Connect is always treated as the source of truth. Existing records are updated whenever newer data is received.

---

## Dashboard Screen

The main screen displays:

### Monthly Summary

* Total distance
* Total calories burned
* Total ride duration

### Workout History

A list of synchronized cycling workouts.

All displayed data is loaded from Room.

---

## Settings Screen

### Monthly Distance Goal

Users can define a monthly cycling goal in kilometers.

Examples:

* 300 km
* 500 km
* 1000 km

The goal is used by both the dashboard and widget.

### Widget Settings

Reserved for future widget customization.

---

## Home Screen Widget

The widget displays:

* Current monthly distance
* Total calories burned
* Total ride duration
* Goal progress

Example:

320 km / 500 km

64% completed

The widget also provides a manual refresh action.

---

# Data Synchronization

Synchronization is triggered:

* During first application launch
* Every hour via WorkManager
* From the widget refresh action

---

# First Launch Flow

1. Verify Health Connect availability.
2. Request required Health Connect permissions.
3. Check local database state.
4. If no data exists for the current month, perform initial synchronization.
5. Navigate to the dashboard.

---

# Data Sources

## Source of Truth

Room Database

## External Source

Health Connect

Data flow:

Health Connect → Room → Repository → Use Cases → UI

Neither screens nor widgets communicate directly with Health Connect.

---

# MVP Scope

* Health Connect integration
* Cycling workout synchronization
* Monthly statistics
* Workout history
* Monthly goal configuration
* Glance widget
* Hourly background synchronization
* Manual synchronization from widget

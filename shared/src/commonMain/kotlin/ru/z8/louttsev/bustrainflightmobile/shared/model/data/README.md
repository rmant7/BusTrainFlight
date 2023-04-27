# Package ru.z8.louttsev.cheaptripmobile.shared.model.data

Data model classes.

## Description

Contains data classes for Model layer of the MVVM architecture.

## Summary

Type                  | Name                   | Description
----------------------|------------------------|--------------------------------------------------------------------
data class            | **Location**           | Concrete location (city).
nested enum           | **Location.Type**      | Location type in relation to route.
data class            | **Route**              | Aggregate route between selected locations.
nested enum           | **Route.Type**         | Route type in relation to ways of moving.
data class            | **Path**               | Particular section (path) within aggregate route.
enum                  | **TransportationType** | Path type in relation to transport.
enum                  | **ConditionalClause**  | Clause or disclaimer in terms of transportation.
enum                  | **Locale**             | Supported language.
class                 | **DurationConverter**  | Duration conversion to string representations.
enum                  | **Country**            | Countries relevant to affiliate programs.
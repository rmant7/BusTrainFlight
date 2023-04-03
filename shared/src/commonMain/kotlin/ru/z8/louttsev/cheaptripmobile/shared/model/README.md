# Package ru.z8.louttsev.cheaptripmobile.shared.model

Model layer of the MVVM architecture.

## Description

Contains repositories and data sources.

## Summary

Type                  | Name                            | Description
----------------------|---------------------------------|-----------------------------------------------
package               | **data**                        | Data classes for Model.
class                 | **LocationRepository**          | Read-only storage of available locations.
class                 | **RouteRepository**             | Read-only storage of available routes.
interface             | **DataSource**                  | Read-only data access logic.
nested class          | **DataSource.ParamsBundle**     | A merger of isolated parameters.
nested enum           | **DataSource.ParamsBundle.Key** | Valid parameter key.
interface             | **DataStorage**                 | Full data access logic.
enum                  | **RepositoryStrategy**          | Data sources usage logic.
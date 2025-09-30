# API de Demon Slayer

## Base URL
```
https://demon-slayer-api.onrender.com/
```

## Endpoints disponibles

### GET /v1/
- Devuelve una lista de `CharacterSummary`.
- Cada elemento incluye:
  - `name`: nombre del personaje.
  - `url`: ruta del recurso en la wiki.
  - `image`: URL de la imagen principal.
- La app utiliza este endpoint en `GetAllCharactersUseCase` para poblar la pantalla de listado.

### GET /v1/{name}
- Devuelve una lista con un unico `CharacterDetail` (la API responde con lista aunque sea singular).
- Campos relevantes:
  - `gallery`: coleccion de imagenes adicionales.
  - `race`, `gender`, `age`, `height`, `weight`.
  - `affiliation`, `occupation`, `status`, `partner(s)`, `relative(s)`.
  - `manga debut`, `anime debut`, `japanese va`, `english va`, `stage play`.
- El repositorio toma el primer elemento de la lista y lo expone a la UI.

## Consideraciones
- Actualmente no se manejan codigos de paginacion; cualquier paginado adicional debe implementarse en el cliente.
- La API responde en ingles. Se planea traducir los textos usando `TranslateTextUseCase`.
- Manejar errores de red (timeouts, 404) y mostrar mensajes claros en la UI es prioritario.

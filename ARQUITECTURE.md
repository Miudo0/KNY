# Arquitectura

La aplicacion sigue una version ligera de Clean Architecture organizada en tres capas principales:

## 1. Capa de presentacion (UI)
- Implementada integramente con Jetpack Compose y Material 3.
- Cada pantalla (por ejemplo, `AllCharactersScreen` y `CharacterDetailScreen`) consume estado expuesto por un ViewModel.
- Se usan `StateFlow` y colecciones combinadas con `collectAsState()` para mantener la UI reactiva.
- Animaciones y efectos (parallax, rotacion de galeria, `Crossfade`) residen en la UI para mantener la logica desacoplada.

## 2. Capa de dominio
- Encapsula reglas de negocio mediante casos de uso (`GetAllCharactersUseCase`, `GetCharacterDetailUseCase`).
- Define modelos independientes del origen de datos (`CharacterSummary`, `CharacterDetail`).
- Proporciona un punto unico de acceso para la UI y facilita la incorporacion de pruebas unitarias.

## 3. Capa de datos
- El repositorio `DemonSlayerRepository` implementa la logica de obtencion de datos y abstrae la fuente remota.
- `DemonSlayerApi` define los contratos HTTP (endpoints `v1/` y `v1/{name}`) consumidos mediante Retrofit.
- `DemonSlayerNetworkModule` expone las dependencias de red y configura la base URL (`https://demon-slayer-api.onrender.com/`).
- El proyecto esta preparado para combinar multiples fuentes (Room, cache) gracias al uso de interfaces y `@Inject`.

## Flujo de datos
1. La UI solicita informacion a su ViewModel.
2. El ViewModel ejecuta el caso de uso correspondiente en una corrutina (`viewModelScope`).
3. El caso de uso delega la peticion al repositorio.
4. El repositorio invoca la API remota mediante Retrofit, transforma la respuesta y la devuelve al dominio.
5. El ViewModel actualiza su `MutableStateFlow`, provocando que la UI se recomponga.

## Inyeccion de dependencias
- Hilt se utiliza para proveer instancias de Retrofit, API y repositorio.
- Las anotaciones `@HiltViewModel` y `@Inject` permiten construir ViewModels y casos de uso sin factorias manuales.

## Consideraciones adicionales
- Existe un `TranslateTextUseCase` basado en ML Kit listo para integrarse en la UI para traduccion contextual.
- Se emplea Paging 3 para futuras ampliaciones de scroll infinito, aunque aun no esta conectado.
- El modulo de navegacion Compose esta preparado pero sin implementacion definitiva (`ui/navegacion`).

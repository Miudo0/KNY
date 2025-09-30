# KNY

Aplicacion Android creada con Kotlin y Jetpack Compose para explorar el universo de *Kimetsu no Yaiba* (Demon Slayer). La app consume la API publica de Demon Slayer, muestra listados ilustrados de personajes y permite consultar detalles enriquecidos con traducciones dinamicas.

## Caracteristicas principales
- Catalogo animado de personajes con imagenes obtenidas desde la API oficial.
- Pantalla de detalle con galeria, ficha biografica y rotacion automatica de imagenes.
- Arquitectura basada en MVVM + Use Cases con inyeccion de dependencias mediante Hilt.
- Consumo de datos remoto con Retrofit y serializacion basada en Gson/Kotlinx.
- Preparada para traduccion automatica de texto mediante ML Kit (pendiente de activacion en UI).

## Requisitos previos
- Android Studio Iguana o superior con soporte para AGP 8.13.0.
- JDK 17 instalado (el proyecto compila con `jvmTarget 11`).
- Dispositivo o emulador con Android 7.0 (API 24) o mayor.

## Puesta en marcha
1. Clonar este repositorio en tu maquina local.
2. Abrir la carpeta `KNY-clean` desde Android Studio.
3. Sincronizar Gradle y esperar la descarga de dependencias.
4. Ejecutar `Run > Run 'app'` o usar `./gradlew assembleDebug` desde la terminal.

```bash
./gradlew assembleDebug
```

El comando generara el APK en `app/build/outputs/apk/debug/`.

## Arquitectura en breve
- **UI (Compose):** pantallas declarativas que reaccionan a `StateFlow` expuesto por los ViewModels.
- **ViewModels (Hilt):** gestionan el estado, coordinan casos de uso y encapsulan logica de presentacion.
- **Dominio:** casos de uso (`GetAllCharactersUseCase`, `GetCharacterDetailUseCase`) que orquestan la logica.
- **Datos:** repositorio `DemonSlayerRepository` que interactua con `DemonSlayerApi` via Retrofit.
- **DI:** `DemonSlayerNetworkModule` configura Retrofit y expone el contrato de la API.

## Estructura del proyecto
- `app/src/main/java/com/empresa/kny/ui`: pantallas Compose y ViewModels.
- `app/src/main/java/com/empresa/kny/domain`: modelos de dominio y casos de uso.
- `app/src/main/java/com/empresa/kny/data`: capa de datos (API y repositorio).
- `app/src/main/res`: recursos graficos y definiciones de tema Material 3.

## Scripts utiles
- `./gradlew lint`: revisa el codigo estatico y chequea reglas basicas de calidad.
- `./gradlew test`: ejecuta pruebas locales (actualmente sin pruebas unitarias definidas).

## Licencia
Pendiente de definir.

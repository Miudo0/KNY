package com.empresa.kny.domain.translation

import android.content.Context
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.Translation
import kotlinx.coroutines.tasks.await // Necesario para usar await() con ML Kit Tasks
import javax.inject.Inject

/**
 * UseCase para traducir texto automáticamente al idioma configurado en el dispositivo.
 * Utiliza ML Kit Translation de Google.
 */
class TranslateTextUseCase @Inject constructor() {

    /**
     * Traduce un texto dado al idioma del dispositivo.
     *
     * @param context Contexto de Android, usado para obtener idioma del sistema.
     * @param text Texto original que se quiere traducir.
     * @param sourceLanguage Código del idioma de origen (por defecto inglés).
     * @return Texto traducido al idioma del dispositivo.
     */
    suspend fun execute(
        context: Context,
        text: String,
        sourceLanguage: String = TranslateLanguage.ENGLISH
    ): String {

        // Detecta el idioma configurado en el dispositivo.
        val locale = context.resources.configuration.locales[0]

        // Obtiene el código del idioma destino (ej. "es" para español).
        val targetLanguage =
            TranslateLanguage.fromLanguageTag(locale.language) ?: TranslateLanguage.SPANISH
        // Si no se detecta correctamente, usa español por defecto.

        // Configura el traductor con idioma origen y destino.
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        // Obtiene el cliente traductor de ML Kit.
        val translator = Translation.getClient(options)

        // Descarga el modelo de traducción si no está presente.
        translator.downloadModelIfNeeded().await()

        // Traduce el texto y devuelve el resultado.
        return translator.translate(text).await()
    }
}
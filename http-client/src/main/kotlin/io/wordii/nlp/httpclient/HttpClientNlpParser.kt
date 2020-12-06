package io.wordii.nlp.httpclient

import io.wordii.nlp.api.NlpParser
import io.wordii.nlp.api.SentenceSequence
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

class HttpClientNlpParser(private val baseUrl: String) : NlpParser {
    private val client = OkHttpClient.Builder()
        .readTimeout(Duration.ofMinutes(1)) // the first request may take some time
        .build()

    override fun depParse(text: String?): SentenceSequence {
        if (text == null) {
            return SentenceSequence(emptyList(), emptySet())
        }
        val request = Request.Builder()
            .url("$baseUrl/nlp/depparse")
            .post(text.toRequestBody(TEXT_PLAIN))
            .build()
        val result = client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw HttpClientException("Something went wrong. Code '${response.code}' and message '${response.message}'.")
            }
            return@use Json.decodeFromString(SentenceSequence.serializer(), response.body!!.string())
        }
        LOGGER.info("Result: $result")
        return result
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(HttpClientNlpParser::class.java)
        private val TEXT_PLAIN = "text/plain; charset=utf-8".toMediaType()
    }
}

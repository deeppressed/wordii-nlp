package io.wordii.nlp.server

import io.ktor.http.*
import io.ktor.server.testing.*
import io.wordii.nlp.api.SentenceSequence
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/nlp/depparse").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val content = response.content
                assertNotNull(content)
                val result = Json.decodeFromString(SentenceSequence.serializer(), content)
                assertTrue(result.sentenceList.isEmpty())
            }
        }
    }
}

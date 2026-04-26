package io.noartcode.theprice.page.api

import io.noartcode.theprice.page.i18n.Platform
import io.noartcode.theprice.page.api.dto.TesterSignupRequest
import io.noartcode.theprice.page.api.dto.TesterSignupResponse
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import kotlin.js.json

class ThePriceApi {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val baseUrl = "https://noartcode.io/apps/theprice/testers/api"

    suspend fun submitTesterSignup(
        email: String,
        name: String,
        platform: Platform
    ) : Result<TesterSignupResponse>  {

        val request = TesterSignupRequest(
            email = email,
            name = name,
            platform = platform.id
        )
        return try {
            val response = window.fetch(
                input = "$baseUrl/signup",
                init = RequestInit(
                    method = "POST",
                    headers = json("Content-type" to "application/json"),
                    body = json.encodeToString(request)
                )
            ).await()

            val text = response.text().await()
            val signupResponse = json.decodeFromString<TesterSignupResponse>(text)
            if (signupResponse.success) {
                Result.success(signupResponse)
            } else {
                Result.failure(Exception(message = signupResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package io.noartcode.theprice.page.api

import io.noartcode.theprice.page.api.dto.ApiResponse
import io.noartcode.theprice.page.api.dto.TesterDto
import io.noartcode.theprice.page.models.Platform
import io.noartcode.theprice.page.api.dto.TesterSignupRequest
import io.noartcode.theprice.page.api.dto.TesterSignupResponse
import io.noartcode.theprice.page.api.dto.TestersResponse
import io.noartcode.theprice.page.api.exception.UnauthorizedException
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.INCLUDE
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.json

class ThePriceApi {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    //private val baseUrl = "https://noartcode.io/apps/theprice/testers/api"
    private val baseUrl = "http://localhost:8080/api/v1"

    suspend fun submitTesterSignup(email: String, name: String, platform: Platform) : Result<TesterSignupResponse>  {
        val request = TesterSignupRequest(
            email = email,
            name = name,
            platform = platform.name.lowercase()
        )
        return try {
            val response = window.fetch(
                input = "$baseUrl/testers/signup",
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

    suspend fun requestMagicLin(email: String) : Result<ApiResponse> {
        return try {
            val response = window.fetch(
                input = "$baseUrl/admin/request-magic-link",
                init= RequestInit(
                    method = "POST",
                    headers = json("Content-Type" to "application/json"),
                    body = JSON.stringify(json("email" to email)),
                    credentials = RequestCredentials.INCLUDE // CRITICAL: Send cookies
                )
            ).await()

             handleResponse(response, "Failed to request magic link")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchTesters() : Result<List<TesterDto>> {
        return try {
            val response = window.fetch(
                input = "$baseUrl/admin/testers",
                init = RequestInit(
                    method = "GET",
                    credentials = RequestCredentials.INCLUDE // CRITICAL: Include session
                )
            ).await()

            if (response.ok) {
                val text = response.text().await()
                val testers = json.decodeFromString<TestersResponse>(text).data
                Result.success(testers)
            } else if(response.status == 401.toShort()) {
                Result.failure(UnauthorizedException())
            } else {
                Result.failure(Exception("Failed to fetch testers"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun approveTester(testerId:String) : Result<ApiResponse> {
        return try {
            val response = window.fetch(
                input = "$baseUrl/admin/testers/$testerId/approve",
                init = RequestInit(
                    method = "POST",
                    credentials = RequestCredentials.INCLUDE
                )
            ).await()
            handleResponse(response, "Failed to approve tester")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun rejectTester(testerId: String) : Result<ApiResponse> {
        return try {
            val response = window.fetch(
                input = "$baseUrl/admin/testers/$testerId/reject",
                init = RequestInit(
                    method = "POST",
                    credentials = RequestCredentials.INCLUDE
                )
            ).await()
            handleResponse(response, "Failed to reject tester")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTester(testerId: String) : Result<ApiResponse> {
        return try {
            val response = window.fetch(
                input = "$baseUrl/admin/testers/$testerId/delete",
                init = RequestInit(
                    method = "DELETE",
                    credentials = RequestCredentials.INCLUDE
                )
            ).await()
            handleResponse(response, "Failed to delete tester")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO: in the future maybe I will implement a specific endpoint from resend invite
    suspend fun resendInvite(testerId: String) : Result<ApiResponse> =
       approveTester(testerId)

    private suspend fun handleResponse(response: Response, defaultErrorMessage:String) =
        if (response.ok) {
            val text = response.text().await()
            val data = json.decodeFromString<ApiResponse>(text)
            Result.success(data)
        } else if (response.status == 401.toShort()){
            Result.failure(UnauthorizedException())
        } else {
            Result.failure(Exception(defaultErrorMessage))
        }

}
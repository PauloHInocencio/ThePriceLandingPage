package io.noartcode.theprice.page.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class TesterSignupResponse(
    val success: Boolean,
    val message:String
)

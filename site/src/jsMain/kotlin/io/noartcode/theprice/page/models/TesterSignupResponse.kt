package io.noartcode.theprice.page.models

import kotlinx.serialization.Serializable

@Serializable
data class TesterSignupResponse(
    val success: Boolean,
    val message:String
)

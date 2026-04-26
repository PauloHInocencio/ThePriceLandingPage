package io.noartcode.theprice.page.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class TesterSignupRequest(
    val email:String,
    val name:String,
    val platform:String,
)

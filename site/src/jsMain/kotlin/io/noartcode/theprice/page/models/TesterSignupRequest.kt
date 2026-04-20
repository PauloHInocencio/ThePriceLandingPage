package io.noartcode.theprice.page.models

import kotlinx.serialization.Serializable

@Serializable
data class TesterSignupRequest(
    val email:String,
    val name:String,
    val platform:String,
)

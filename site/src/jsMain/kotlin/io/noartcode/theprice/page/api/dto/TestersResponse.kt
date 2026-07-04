package io.noartcode.theprice.page.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class TestersResponse(
    val data: List<TesterDto>
)

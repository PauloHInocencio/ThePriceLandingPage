package io.noartcode.theprice.page.api.dto

import io.noartcode.theprice.page.models.Platform
import io.noartcode.theprice.page.models.TesterStatus
import kotlinx.serialization.Serializable

@Serializable
data class TesterDto(
    val id: String,
    val email: String,
    val name: String,
    val status: TesterStatus,
    val platform: Platform,
    val createdAt: String,
    val approvedAt: String? = null,
    val rejectedAt: String? = null,
    val invitedAt: String? = null,
)



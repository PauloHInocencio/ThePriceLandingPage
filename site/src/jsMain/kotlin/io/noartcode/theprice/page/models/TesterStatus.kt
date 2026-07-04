package io.noartcode.theprice.page.models

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import io.noartcode.theprice.page.PageColors
import io.noartcode.theprice.page.i18n.Strings
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleXmark
import com.varabyte.kobweb.silk.components.icons.fa.FaClock
import com.varabyte.kobweb.silk.components.icons.fa.FaEnvelope
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import io.noartcode.theprice.page.models.TesterStatus.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TesterStatus {
    @SerialName("approved") APPROVED,
    @SerialName("pending") PENDING,
    @SerialName("invited") INVITED,
    @SerialName("rejected") REJECTED
}

fun TesterStatus.displayName(strings: Strings) : String = when(this) {
    APPROVED -> strings.statusApproved
    PENDING -> strings.statusPending
    INVITED -> strings.statusInvited
    REJECTED -> strings.statusRejected
}

fun TesterStatus.Color(): Color = when (this) {
    APPROVED -> PageColors.successGreen
    PENDING, INVITED  -> PageColors.thePriceBlue
    REJECTED -> PageColors.errorRed
}

@Composable
fun TesterStatus.Icon() =  when (this) {
    APPROVED -> { FaCircleCheck(size = IconSize.SM, modifier = Modifier.color(this.Color())) }
    PENDING -> { FaClock(size = IconSize.SM, modifier = Modifier.color(this.Color())) }
    INVITED -> { FaEnvelope(size = IconSize.SM, modifier = Modifier.color(this.Color())) }
    REJECTED -> { FaCircleXmark(size = IconSize.SM, modifier = Modifier.color(this.Color())) }
}
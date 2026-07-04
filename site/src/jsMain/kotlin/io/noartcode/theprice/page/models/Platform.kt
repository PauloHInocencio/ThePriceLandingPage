package io.noartcode.theprice.page.models

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaApple
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import io.noartcode.theprice.page.i18n.Strings
import io.noartcode.theprice.page.models.Platform.Android
import io.noartcode.theprice.page.models.Platform.Both
import io.noartcode.theprice.page.models.Platform.IOS
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Platform {
    @SerialName("android") Android,
    @SerialName("ios") IOS,
    @SerialName("both") Both;

    companion object {
        val all = listOf(Android, IOS, Both)
    }
}

fun Platform.displayName(strings: Strings): String = when (this) {
    Android -> strings.platformAndroid
    IOS -> strings.platformIOS
    Both -> strings.platformBoth
}

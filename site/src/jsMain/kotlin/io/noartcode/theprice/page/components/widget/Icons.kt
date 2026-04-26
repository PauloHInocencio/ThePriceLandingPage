package io.noartcode.theprice.page.components.widget

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.icons.fa.FaFlask
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import io.noartcode.theprice.page.PageColors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px



@Composable
fun TestFlaskIcon() {
    IconCircularContainer {
        FaFlask(
            modifier = Modifier.color(PageColors.thePriceBlue),
            size = IconSize.LG
        )
    }
}

@Composable
fun SuccessIcon() {
    IconCircularContainer {
        FaCircleCheck(
            modifier = Modifier.color(PageColors.successGreen),
            size = IconSize.LG
        )
    }
}

@Composable
fun ErrorIcon() {
    IconCircularContainer {
        FaCircleXmark(
            modifier = Modifier.color(PageColors.errorRed),
            size = IconSize.LG
        )
    }
}


@Composable
private fun IconCircularContainer(
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .borderRadius(50.px)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = PageColors.thePriceBlue.copy(alpha = 30)
            )
            .size(3.cssRem)
            .background(PageColors.thePriceBlue.copy(alpha = 70))
            .padding(1.cssRem),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


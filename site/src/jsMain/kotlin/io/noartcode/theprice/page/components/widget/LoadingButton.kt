package io.noartcode.theprice.page.components.widget

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.toAnimation
import io.noartcode.theprice.page.BlueButtonVariant
import io.noartcode.theprice.page.SpinKeyframes
import io.noartcode.theprice.page.TesterColors
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

@Composable
fun LoadingButton(
    text:String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = { onClick() },
        variant = BlueButtonVariant,
        enabled = isEnabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularLoadingIndicator()
            }
            SpanText(text)
        }
    }
}

@Composable
private fun CircularLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .size(24.px)
            .borderRadius(50.px)
            .border(
                width = 3.px,
                style = LineStyle.Solid,
                color = Colors.White
            )
            .borderTop(
                width = 3.px,
                style = LineStyle.Solid,
                color = TesterColors.thePriceBlue
            )
            .animation(
                SpinKeyframes.toAnimation(
                    duration = 1.s,
                    iterationCount = AnimationIterationCount.Infinite,
                    timingFunction = AnimationTimingFunction.Linear
                )
            )
    )
}
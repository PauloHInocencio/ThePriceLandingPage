package io.noartcode.theprice.page.components.widget

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.style.animation.toAnimation
import io.noartcode.theprice.page.PageColors
import io.noartcode.theprice.page.SpinKeyframes
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.CSSNumericValue
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

@Composable
fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Int = 24,
) {
    Box(
        modifier
            .size(size.px)
            .borderRadius(50.px)
            .border(
                width = 3.px,
                style = LineStyle.Solid,
                color = Colors.White
            )
            .borderTop(
                width = 3.px,
                style = LineStyle.Solid,
                color = PageColors.thePriceBlue
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
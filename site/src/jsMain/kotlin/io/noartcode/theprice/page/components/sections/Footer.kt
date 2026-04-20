package io.noartcode.theprice.page.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.css.functions.grayscale
import com.varabyte.kobweb.compose.css.functions.opacity
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.filter
import com.varabyte.kobweb.compose.ui.modifiers.font
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.CheckboxKind
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import io.noartcode.theprice.page.TesterColors
import io.noartcode.theprice.page.i18n.Strings
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Span


@Composable
fun Footer(
    strings: Strings,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .gap(2.cssRem)
            .padding(2.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Row(
            Modifier.gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                src = "pric-logo.png",
                modifier = Modifier
                    .size(35.px)
                    .filter(grayscale(100.percent), brightness(0.6f))
            )
            SpanText(
                text = "ThePrice",
                modifier = Modifier
                    .fontSize(1.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .color(TesterColors.textGray)
            )
        }

        SpanText(
            text = strings.footerTagline,
            modifier = Modifier
                .textAlign(TextAlign.Center)
                .color(TesterColors.textGray)
                .fontSize(1.cssRem)
                .fillMaxWidth()
                .maxWidth(500.px)
        )
        Row(
            Modifier
                .gap(0.5.cssRem)
                .margin(top = 2.cssRem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(
                text = strings.madeWithKobweb,
                modifier = Modifier
                    .color(TesterColors.textGray)
                    .fontSize(0.8.cssRem)
            )
            A(
                href = "https://kobweb.varabyte.com",
                attrs = {
                        attr("target", "_blank")
                        attr("rel", "noopener noreferrer") // <-- Security best practices (uncle Claude)
                    }
            ) {
                Image(
                    src = "kobweb-logo.png",
                    modifier = modifier
                        .height(16.px)
                        .cursor(Cursor.Pointer)
                )
            }

        }
        SpanText(
            text = strings.copyrightText,
            modifier = Modifier
                .color(TesterColors.lightGray.copy(alpha = 80))
                .fontSize(0.75.cssRem)
        )
    }
}

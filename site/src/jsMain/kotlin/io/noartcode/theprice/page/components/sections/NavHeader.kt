package io.noartcode.theprice.page.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.paddingInline
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import io.noartcode.theprice.page.TesterColors
import io.noartcode.theprice.page.i18n.Language
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

@Composable
fun NavHeader(
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth().padding(1.5.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .maxWidth(1000.px)  // Container max width
                .fillMaxWidth()
                .paddingInline(2.cssRem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoContainer()
            Spacer()
            LanguageToggle(currentLanguage, onLanguageChange)
        }
    }
}

@Composable
private fun LogoContainer() {
    Row(
        Modifier.gap(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            src = "pric-logo.png",
            modifier = Modifier.size(48.px)
        )
        SpanText(
            text = "ThePrice",
            modifier = Modifier
                .fontSize(1.5.cssRem)
                .fontWeight(FontWeight.SemiBold)
                .color(TesterColors.thePriceBlue)
        )
    }
}

@Composable
private fun LanguageToggle(
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit,
) {
    Row(
        Modifier.gap(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Language.all.forEachIndexed { index, lang ->
            if (index > 0) {
                SpanText(" | ", Modifier.color(Colors.Gray))
            }
            SpanText(
                lang.displayName,
                Modifier
                    .displayIfAtLeast(Breakpoint.MD)
                    .color(if (lang == currentLanguage) TesterColors.thePriceBlue else Colors.Gray)
                    .cursor(Cursor.Pointer)
                    .onClick { onLanguageChange(lang) }
            )
            SpanText(
                lang.flag,
                Modifier
                    .fontSize(FontSize.XXLarge)
                    .displayUntil(Breakpoint.MD)
                    .opacity(if (lang == currentLanguage) 1.0 else 0.4)
                    .cursor(Cursor.Pointer)
                    .onClick { onLanguageChange(lang) }
            )
        }
    }
}
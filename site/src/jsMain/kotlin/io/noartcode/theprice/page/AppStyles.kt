package io.noartcode.theprice.page

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.silk.style.addVariantBase
import io.noartcode.theprice.page.PlatformSelectorButtonStyle
import org.jetbrains.compose.web.css.*

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    // This site does not need scrolling itself, but this is a good demonstration for how you might enable this in your
    // own site. Note that we only enable smooth scrolling unless the user has requested reduced motion, which is
    // considered a best practice.
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Oxygen", "Ubuntu",
                "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "sans-serif"
            )
            .fontSize(18.px)
            .lineHeight(1.5)
    }

    // Silk dividers only extend 90% by default; we want full width dividers in our site
    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val TesterPageStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .minHeight(100.vh)
        .minWidth(400.px)
        .backgroundColor(TesterColors.background)
}


val TesterCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(700.px)
            .background(TesterColors.cardBackground)
            .borderRadius(24.px)
            .padding(3.cssRem)
            .border(1.px, LineStyle.Solid, TesterColors.border)
    }
}

val FormInputStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(500.px)
            .height(3.cssRem)
            .padding(1.cssRem)
            .backgroundColor(TesterColors.inputBackground)
            .border(
                width = 1.px ,
                style = LineStyle.Solid,
                color = TesterColors.border
            )
            .boxShadow(0.px, 0.px, 0.px, 0.px, null)
            .borderRadius(12.px)
            .color(Colors.White)
            .fontSize(1.cssRem)
    }
}


val BlueButtonVariant = ButtonStyle.addVariant {
    base {
        Modifier
            .fillMaxWidth()
            .height(3.cssRem)
            .maxWidth(500.px)
            .padding(1.cssRem)
            .color(Colors.White)
            .backgroundColor(TesterColors.thePriceBlue)
            .borderRadius(12.px)
            .fontSize(1.cssRem)
            .fontWeight(FontWeight.SemiBold)
            .cursor(Cursor.Pointer)
    }
}

val PlatformSelectorContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(500.px)
        .display(DisplayStyle.Flex)
        .borderRadius(12.px)
        .border(width = 1.px, style = LineStyle.Solid, TesterColors.border)
        .overflow(Overflow.Hidden)
}

val PlatformSelectorButtonStyle = CssStyle {
    base {
        Modifier
            .flex(1)
            .height(3.cssRem)
            .padding(1.cssRem)
            .backgroundColor(TesterColors.inputBackground)
            .color(TesterColors.textGray)
            .fontSize(1.cssRem)
            .fontWeight(FontWeight.Medium)
            .textAlign(TextAlign.Center)
            .cursor(Cursor.Pointer)
            .transition(
                listOf(
                    Transition.of(property = "background-color", duration = 0.2.s),
                    Transition.of(property = "color", 0.2.s)
                )
            )
    }
}

val PlatformSelectedVariant = CssStyle.base {
    Modifier
        .backgroundColor(TesterColors.thePriceBlue)
        .color(Colors.White)
}

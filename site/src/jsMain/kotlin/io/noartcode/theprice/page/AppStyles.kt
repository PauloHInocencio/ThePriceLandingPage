package io.noartcode.theprice.page

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextOverflow
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
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.silk.style.animation.Keyframes
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
        .backgroundColor(PageColors.background)
}


val TesterCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(700.px)
            .background(PageColors.cardBackground)
            .borderRadius(24.px)
            .padding(3.cssRem)
            .border(1.px, LineStyle.Solid, PageColors.border)
    }
}

val FormInputStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(500.px)
            .height(3.cssRem)
            .padding(1.cssRem)
            .backgroundColor(PageColors.inputBackground)
            .border(
                width = 1.px ,
                style = LineStyle.Solid,
                color = PageColors.border
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
            .backgroundColor(PageColors.thePriceBlue)
            .borderRadius(12.px)
            .fontSize(1.cssRem)
            .fontWeight(FontWeight.SemiBold)
            .cursor(Cursor.Pointer)
    }
}

val ErrorButtonVariant = ButtonStyle.addVariant {
    base {
        Modifier
            .height(3.cssRem)
            .padding(1.cssRem)
            .color(Colors.White)
            .textAlign(TextAlign.Center)
            .backgroundColor(PageColors.errorRedBg.copy(alpha = 95))
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
        .border(width = 1.px, style = LineStyle.Solid, PageColors.border)
        .overflow(Overflow.Hidden)
}

val PlatformSelectorButtonStyle = CssStyle {
    base {
        Modifier
            .flex(1)
            .height(3.cssRem)
            .padding(1.cssRem)
            .backgroundColor(PageColors.inputBackground)
            .color(PageColors.textGray)
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
        .backgroundColor(PageColors.thePriceBlue)
        .color(Colors.White)
}

val SpinKeyframes = Keyframes {
    from {
        Modifier.rotate(0.deg)
    }
    to {
        Modifier.rotate(360.deg)
    }
}


val TesterListItemStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
            .borderBottom(1.px, LineStyle.Solid, PageColors.border)
            .backgroundColor(PageColors.cardBackground)
            .transition(
                Transition.of(property = "background-color", duration = 0.2.s)
            )
    }

    cssRule(":hover") {
        Modifier.backgroundColor(PageColors.inputBackground)
    }
}

val TesterListHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 1.cssRem, leftRight = 1.cssRem)
        .backgroundColor(PageColors.inputBackground)
        .borderBottom(2.px, LineStyle.Solid, PageColors.thePriceBlue)
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.9.cssRem)
        .color(PageColors.lightGray)
}

val StatusBadgeStyle = CssStyle.base {
    Modifier
        .minWidth(80.px)
        .maxWidth(100.px)
        .padding(topBottom = 0.25.cssRem, leftRight = 0.5.cssRem)
        .borderRadius(12.px)
        .gap(0.3.cssRem)
}

val ActionButtonStyle = CssStyle {
    base {
        Modifier
            .size(32.px)
            .borderRadius(6.px)
            .backgroundColor(PageColors.inputBackground)
            .cursor(Cursor.Pointer)
            .transition(
                listOf(
                    Transition.of(property = "background-color", duration = 0.2.s),
                    Transition.of(property = "transform", duration = 0.1.s)
                )
            )
    }

    cssRule(":hover") {
        Modifier
            .backgroundColor(PageColors.border)
            .scale(1.05)
    }

    cssRule(":active") {
        Modifier.scale(0.95)
    }
}

val TestersListContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(1200.px)
        .background(PageColors.cardBackground)
        .borderRadius(16.px)
        .border(1.px, LineStyle.Solid, PageColors.border)
        .overflow(Overflow.Hidden)
}

val TesterListItemSpanTextStyle = CssStyle.base {
    Modifier
        .minWidth(200.px)
        .maxWidth(250.px)
        .overflow(Overflow.Hidden)
        .textOverflow(TextOverflow.Ellipsis)
        .whiteSpace(WhiteSpace.NoWrap)
        .fontSize(0.9.cssRem)
        .color(Colors.White)
}

val EmptyStateStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(4.cssRem, 2.cssRem)
        .gap(1.5.cssRem)
        .textAlign(TextAlign.Center)
}
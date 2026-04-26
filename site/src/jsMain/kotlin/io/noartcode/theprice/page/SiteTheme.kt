package io.noartcode.theprice.page

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(0xFAFAFA)
    ctx.theme.palettes.light.color = Colors.Black
    ctx.theme.palettes.dark.background = Color.rgb(0x06080B)
    ctx.theme.palettes.dark.color = Colors.White
}


object PageColors {
    val background = Color.rgb(0x0A0E1A)        // Dark navy
    val cardBackground = Color.rgb(0x1A1F2E)    // Card background
    val inputBackground = Color.rgb(0x0F1419)   // Input fields
    val border = Color.rgb(0x2A2F3E)            // Borders
    val textGray = Color.rgb(0x9CA3AF)          // Gray text
    val lightGray = Color.rgb(0xE5E7EB)
    val thePriceBlue = Color.rgb(0x459AE5)
    val successGreen = Color.rgb(0x22C55E)
    val successGreenBg = Color.rgb(0x166534)
    val errorRed = Color.rgb(0xEF4444)
    val errorRedBg = Color.rgb(0x991B1B)
}
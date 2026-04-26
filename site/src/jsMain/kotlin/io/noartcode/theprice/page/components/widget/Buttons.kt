package io.noartcode.theprice.page.components.widget

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import io.noartcode.theprice.page.BlueButtonVariant
import io.noartcode.theprice.page.ErrorButtonVariant
import org.jetbrains.compose.web.css.cssRem

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
fun ErrorButton(
    text:String,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        variant = ErrorButtonVariant,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SpanText(text)
        }
    }
}
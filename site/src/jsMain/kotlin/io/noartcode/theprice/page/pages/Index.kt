package io.noartcode.theprice.page.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.icons.fa.FaFlask
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.noartcode.theprice.page.FormInputStyle
import io.noartcode.theprice.page.PlatformSelectedVariant
import io.noartcode.theprice.page.PlatformSelectorButtonStyle
import io.noartcode.theprice.page.PlatformSelectorContainerStyle
import io.noartcode.theprice.page.TesterCardStyle
import io.noartcode.theprice.page.TesterColors
import io.noartcode.theprice.page.TesterPageStyle
import io.noartcode.theprice.page.api.ThePriceApi
import io.noartcode.theprice.page.components.sections.Footer
import io.noartcode.theprice.page.components.sections.NavHeader
import io.noartcode.theprice.page.components.widget.LoadingButton
import io.noartcode.theprice.page.i18n.Language
import io.noartcode.theprice.page.i18n.LanguageStorage
import io.noartcode.theprice.page.i18n.Platform
import io.noartcode.theprice.page.i18n.Strings
import io.noartcode.theprice.page.i18n.displayName
import io.noartcode.theprice.page.i18n.strings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input

@Page
@Composable
fun HomePage() {

    var currentLanguage by remember {
        mutableStateOf(LanguageStorage.load() ?: Language.English)
    }

    LaunchedEffect(currentLanguage) {
        LanguageStorage.save(currentLanguage)
    }
    val api = remember { ThePriceApi() }
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
    val strings = currentLanguage.strings()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedPlatform by remember { mutableStateOf<Platform>(Platform.Android) }
    var isSubmitting by remember { mutableStateOf(false) }
    val isSubmissionEnabled = name.isNotBlank() && emailRegex.matches(email) && !isSubmitting
    var errorMessage by remember { mutableStateOf<String?>(null) }


    Box(
        TesterPageStyle.toModifier(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(Modifier.fillMaxSize()) {
            NavHeader(
                currentLanguage = currentLanguage,
                onLanguageChange = { currentLanguage = it }
            )
            TesterForm(
                strings = strings,
                selectedPlatform = selectedPlatform,
                onPlatformChange = { selectedPlatform = it },
                name = name,
                onNameChange = { name = it },
                email = email,
                onEmailChange = { email = it },
                isSubmissionEnabled = isSubmissionEnabled,
                onSubmit = {
                    MainScope().launch {
                        isSubmitting = true
                        val result = api.submitTesterSignup(
                            email = email,
                            name = name,
                            platform = selectedPlatform
                        )

                        result.fold(
                            onSuccess = {
                                isSubmitting = false
                            },
                            onFailure = { e ->
                                isSubmitting = false
                                errorMessage = e.message ?: "Something went wrong!"
                            }
                        )
                    }
                },
                isSubmitting = isSubmitting
            )
            Footer(
                strings = strings
            )
        }
    }
}


@Composable
private fun ColumnScope.TesterForm(
    strings: Strings,
    selectedPlatform: Platform,
    onPlatformChange: (Platform) -> Unit,
    name:String,
    onNameChange : (String) -> Unit,
    email:String,
    onEmailChange : (String) -> Unit,
    isSubmissionEnabled: Boolean,
    onSubmit: () -> Unit,
    isSubmitting: Boolean,
) {
    Box(
        Modifier.fillMaxWidth()
            .weight(1)
            .padding(2.cssRem),
        contentAlignment = Alignment.Center
    ) {
        Column(TesterCardStyle.toModifier().gap(2.cssRem)) {
            Row(
                Modifier.gap(1.cssRem),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TestFlaskIcon()
                SpanText(
                    text = strings.becomeATester,
                    Modifier
                        .fontSize(1.5.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .color(Colors.LightGray)
                )
            }

            SpanText(
                strings.signUpDescription,
                Modifier.color(TesterColors.textGray)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .gap(1.cssRem),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PlatformSelector(
                    selectedPlatform = selectedPlatform,
                    onPlatformChange = { onPlatformChange(it) },
                    strings = strings
                )

                Input(
                    type = InputType.Text,
                    attrs = FormInputStyle.toModifier().toAttrs {
                        value(name)
                        onInput { onNameChange(it.value) }
                        placeholder(strings.namePlaceholder)
                    }
                )
                Input(
                    type = InputType.Email,
                    attrs = FormInputStyle.toModifier().toAttrs {
                        value(email)
                        onInput { onEmailChange(it.value) }
                        placeholder(strings.emailPlaceholder)
                    }
                )
                LoadingButton(
                    text = if (isSubmitting) strings.registeringButton else strings.registerButton,
                    onClick = onSubmit,
                    isEnabled = isSubmissionEnabled,
                    isLoading = isSubmitting,
                )
            }
        }
    }
}

@Composable
private fun PlatformSelector(
    selectedPlatform: Platform,
    onPlatformChange: (Platform) -> Unit,
    strings: Strings,
    modifier: Modifier = Modifier
) {
    Row(
        PlatformSelectorContainerStyle.toModifier().then(modifier)
    ) {
        Platform.all.forEachIndexed { index, platform ->
            val isSelected = platform == selectedPlatform
            val isLast = index == Platform.all.size - 1
            Box(
                PlatformSelectorButtonStyle
                    .toModifier()
                    .then(if (isSelected) PlatformSelectedVariant.toModifier() else Modifier)
                    .then(if (!isLast) Modifier
                        .border(
                            width = 1.px,
                            style = LineStyle.Solid,
                            color = TesterColors.border
                        ) else Modifier
                    )
                    .onClick { onPlatformChange(platform) },
                contentAlignment = Alignment.Center
            ) {
                SpanText(platform.displayName(strings))
            }
        }
    }
}


@Composable
private fun TestFlaskIcon() {
    Box(
        Modifier
            .borderRadius(50.px)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = TesterColors.thePriceBlue.copy(alpha = 30)
            )
            .size(3.cssRem)
            .background(TesterColors.thePriceBlue.copy(alpha = 70))
            .padding(1.cssRem),
        contentAlignment = Alignment.Center
    ) {
        FaFlask(
            modifier = Modifier.color(TesterColors.thePriceBlue),
            size = IconSize.LG
        )
    }
}



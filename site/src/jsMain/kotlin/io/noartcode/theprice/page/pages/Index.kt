package io.noartcode.theprice.page.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
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
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
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
import io.noartcode.theprice.page.PageColors
import io.noartcode.theprice.page.TesterPageStyle
import io.noartcode.theprice.page.api.ThePriceApi
import io.noartcode.theprice.page.components.sections.Footer
import io.noartcode.theprice.page.components.sections.NavHeader
import io.noartcode.theprice.page.components.widget.ErrorButton
import io.noartcode.theprice.page.components.widget.ErrorIcon
import io.noartcode.theprice.page.components.widget.LoadingButton
import io.noartcode.theprice.page.components.widget.SuccessIcon
import io.noartcode.theprice.page.components.widget.TestFlaskIcon
import io.noartcode.theprice.page.i18n.Language
import io.noartcode.theprice.page.i18n.LanguageStorage
import io.noartcode.theprice.page.i18n.Platform
import io.noartcode.theprice.page.i18n.Strings
import io.noartcode.theprice.page.i18n.displayName
import io.noartcode.theprice.page.i18n.strings
import io.noartcode.theprice.page.pages.state.SubmissionState
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
    var submissionState by remember { mutableStateOf<SubmissionState>(SubmissionState.Error)}
    val isSubmitting = submissionState is SubmissionState.Submitting
    val isSubmissionEnabled = name.isNotBlank() && emailRegex.matches(email) && !isSubmitting

    Box(
        TesterPageStyle.toModifier(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(Modifier.fillMaxSize()) {
            NavHeader(
                currentLanguage = currentLanguage,
                onLanguageChange = { currentLanguage = it }
            )
            when (submissionState){
                is SubmissionState.Idle, is SubmissionState.Submitting  -> {
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
                                submissionState = SubmissionState.Submitting
                                val result = api.submitTesterSignup(
                                    email = email,
                                    name = name,
                                    platform = selectedPlatform
                                )

                                result.fold(
                                    onSuccess = { response ->
                                        println("Success message: ${response.message}")
                                        submissionState = SubmissionState.Success
                                    },
                                    onFailure = { e ->
                                        println("Error message: ${e.message}")
                                        submissionState = SubmissionState.Error
                                    }
                                )
                            }
                        },
                        isSubmitting = isSubmitting
                    )
                }
                is SubmissionState.Success -> {
                    StatusCard(
                        isSuccess = true,
                        strings = strings,
                    )
                }
                is SubmissionState.Error -> {
                    StatusCard(
                        isSuccess = false,
                        strings = strings,
                        onTryAgain = {
                            submissionState = SubmissionState.Idle
                        }
                    )
                }
            }

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
                Modifier.color(PageColors.textGray)
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
                            color = PageColors.border
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
private fun ColumnScope.StatusCard(
    isSuccess: Boolean,
    strings: Strings,
    onTryAgain : () -> Unit = {}
) {
    Box(
        Modifier.fillMaxWidth()
            .weight(1)
            .padding(2.cssRem),
        contentAlignment = Alignment.Center
    ) {
        Column(
            TesterCardStyle.toModifier()
                .background(PageColors.errorRedBg.copy(alpha = 30))
                .gap(2.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.gap(1.cssRem),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if  (isSuccess) SuccessIcon() else ErrorIcon()
                SpanText(
                    text = if (isSuccess) strings.successTitle else strings.errorTitle,
                    Modifier
                        .fontSize(1.5.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .color(if (isSuccess) PageColors.successGreen else PageColors.errorRed)
                )
            }

            SpanText(
                if (isSuccess) strings.successMessage else strings.defaultErrorMessage,
                Modifier
                    .color(
                        if (isSuccess) PageColors.successGreen
                        else PageColors.errorRed
                    )
                    .textAlign(TextAlign.Center)
            )

            if (!isSuccess) {
                ErrorButton(
                    text = strings.tryAgainButton,
                    onClick = onTryAgain
                )
            }
        }
    }
}


package io.noartcode.theprice.page.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.noartcode.theprice.page.FormInputStyle
import io.noartcode.theprice.page.TesterCardStyle
import io.noartcode.theprice.page.TesterListItemSpanTextStyle
import io.noartcode.theprice.page.TesterPageStyle
import io.noartcode.theprice.page.api.ThePriceApi
import io.noartcode.theprice.page.components.widget.CircularLoadingIndicator
import io.noartcode.theprice.page.components.widget.LoadingButton
import io.noartcode.theprice.page.models.Language
import io.noartcode.theprice.page.models.strings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Input

@Page("/admin/login")
@Composable
fun AdminLoginPage() {


    val strings = Language.English.strings()
    var email by remember { mutableStateOf("") }
    var pageState by remember { mutableStateOf<LoginState>(LoginState.Idle) }

    val api = remember { ThePriceApi() }

    Box(
        modifier = TesterPageStyle.toModifier(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = TesterCardStyle.toModifier().gap(2.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(val state = pageState) {
                is LoginState.Idle -> {
                    Input(
                        type = InputType.Email,
                        attrs = FormInputStyle.toModifier().toAttrs {
                            value(email)
                            onInput { email = it.value }
                            placeholder(strings.emailPlaceholder)
                        }
                    )
                    LoadingButton(
                        text = "Send Magic Link",
                        onClick = {
                            MainScope().launch {
                                pageState = LoginState.Loading
                                val result = api.requestMagicLin(email)
                                pageState = if (result.isSuccess) {
                                    LoginState.Success(message = result.getOrNull()?.message ?: "Check your email")
                                } else {
                                    LoginState.Error("Magic link sent. Check your email")
                                }
                            }
                        }
                    )
                }
                is LoginState.Loading -> {
                    CircularLoadingIndicator(size = 100)
                }
                is LoginState.Success -> {
                    SpanText(
                        modifier = TesterListItemSpanTextStyle.toModifier(),
                        text = state.message
                    )
                }
                is LoginState.Error -> {
                    SpanText(
                        modifier = TesterListItemSpanTextStyle.toModifier(),
                        text = state.message
                    )
                }
            }
        }
    }

}


sealed interface LoginState {
    object Idle: LoginState
    object Loading: LoginState
    data class Success(val message: String) : LoginState
    data class Error(val message: String) : LoginState
}
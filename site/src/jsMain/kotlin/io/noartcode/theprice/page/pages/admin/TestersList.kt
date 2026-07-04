package io.noartcode.theprice.page.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.title
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaApple
import com.varabyte.kobweb.silk.components.icons.fa.FaCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleXmark
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.FaRotateRight
import com.varabyte.kobweb.silk.components.icons.fa.FaTrash
import com.varabyte.kobweb.silk.components.icons.fa.FaUsers
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.noartcode.theprice.page.ActionButtonStyle
import io.noartcode.theprice.page.EmptyStateStyle
import io.noartcode.theprice.page.PageColors
import io.noartcode.theprice.page.StatusBadgeStyle
import io.noartcode.theprice.page.TesterListHeaderStyle
import io.noartcode.theprice.page.TesterListItemSpanTextStyle
import io.noartcode.theprice.page.TesterListItemStyle
import io.noartcode.theprice.page.TesterPageStyle
import io.noartcode.theprice.page.TestersListContainerStyle
import io.noartcode.theprice.page.api.ThePriceApi
import io.noartcode.theprice.page.api.dto.ApiResponse
import io.noartcode.theprice.page.api.dto.TesterDto
import io.noartcode.theprice.page.api.exception.UnauthorizedException
import io.noartcode.theprice.page.components.widget.CircularLoadingIndicator
import io.noartcode.theprice.page.i18n.Strings
import io.noartcode.theprice.page.models.Color
import io.noartcode.theprice.page.models.Icon
import io.noartcode.theprice.page.models.Language
import io.noartcode.theprice.page.models.Platform
import io.noartcode.theprice.page.models.Platform.*
import io.noartcode.theprice.page.models.TesterStatus
import io.noartcode.theprice.page.models.displayName
import io.noartcode.theprice.page.models.strings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

enum class TesterAction {
    APPROVE,
    REJECT,
    RESEND_INVITE,
    DELETE
}


@Page("/admin/testers-list")
@Composable
fun TestersListPage(){
    var testers by remember { mutableStateOf<List<TesterDto>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var currentLanguage by remember {
        mutableStateOf(Language.English)
    }

    val api = remember { ThePriceApi() }
    val ctx = rememberPageContext()
    val strings = currentLanguage.strings()

    LaunchedEffect(Unit) {
        val result = api.fetchTesters()
        loading = true
        result.onSuccess { data ->
            println("List of signup testers:$data")
            loading = false
            testers = data
        }.onFailure { e ->
            loading = false
            println("Failed to load testers: $e")
            ctx.router.navigateTo("/admin/login")
        }
    }

    fun handleException(e: Throwable) {
        error = e.message
        loading = false
        if (e is UnauthorizedException){
            ctx.router.navigateTo("/admin/login")
        }
    }

    Box(
        TesterPageStyle.toModifier(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(2.cssRem),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading) {
                CircularLoadingIndicator(size = 100)
            } else if (error != null) {
                SpanText(
                    text = error ?: "",
                    modifier = Modifier
                        .color(PageColors.errorRed)
                        .padding(1.cssRem)
                )
            } else {
                Column(TestersListContainerStyle.toModifier()) {
                    TesterListHeader(strings = strings)

                    if (testers.isEmpty()) {
                        EmptyState(
                            strings = strings,
                            onRefresh = {
                                MainScope().launch {
                                    loading = true
                                    api.fetchTesters()
                                        .onSuccess { testers = it }
                                        .onFailure { e -> error = e.message }
                                    loading = false
                                }
                            }
                        )
                    } else {
                        testers.forEach { tester ->
                            TesterListItem(
                                tester = tester,
                                strings = strings,
                                onAction = { action, testerId ->
                                    MainScope().launch {
                                        loading = true
                                        when (action) {
                                            TesterAction.APPROVE -> {
                                                println("Approving tester: $testerId")
                                                val exception = api.approveTester(testerId).exceptionOrNull()
                                                if (exception !=  null) {
                                                    handleException(exception)
                                                    return@launch
                                                }
                                            }
                                            TesterAction.REJECT -> {
                                                println("Rejecting tester: $testerId")
                                                val exception = api.rejectTester(testerId).exceptionOrNull()
                                                if (exception !=  null) {
                                                    handleException(exception)
                                                    return@launch
                                                }
                                            }
                                            TesterAction.RESEND_INVITE -> {
                                                println("Resending invite to tester: $testerId")
                                                val exception = api.resendInvite(testerId).exceptionOrNull()
                                                if (exception !=  null) {
                                                    handleException(exception)
                                                    return@launch
                                                }
                                            }
                                            TesterAction.DELETE -> {
                                                println("Deleting tester: $testerId")
                                                val exception = api.deleteTester(testerId).exceptionOrNull()
                                                if (exception !=  null) {
                                                    handleException(exception)
                                                    return@launch
                                                }
                                            }
                                        }
                                        api.fetchTesters()
                                            .onSuccess { testers = it }
                                            .onFailure { e -> error = e.message }
                                        loading = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun TesterListHeader(
    strings: Strings,
    modifier: Modifier = Modifier
) {
    Row(
        TesterListHeaderStyle.toModifier().then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier.gap(1.cssRem).flex(1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(
                text = strings.headerEmail,
                modifier = Modifier.minWidth(200.px).maxWidth(250.px)
            )
            SpanText(
                text = strings.headerName,
                modifier = Modifier.minWidth(150.px).maxWidth(200.px)
            )
            SpanText(
                text = strings.headerStatus,
                modifier = Modifier.minWidth(100.px).maxWidth(120.px)
            )
            SpanText(
                text = strings.headerPlatform,
                modifier = Modifier.minWidth(100.px)
                    .maxWidth(120.px)
                    .textAlign(TextAlign.Center)
            )
            SpanText(
                text = strings.headerDate,
                modifier = Modifier.minWidth(100.px)
            )
        }

        SpanText(
            text = strings.headerActions,
            modifier = Modifier.minWidth(120.px).textAlign(TextAlign.End)
        )
    }
}

@Composable
fun TesterListItem(
    tester: TesterDto,
    strings: Strings,
    onAction: (TesterAction, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        TesterListItemStyle.toModifier().then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            Modifier.gap(1.cssRem).flex(1), // I need to remember this.
            verticalAlignment = Alignment.CenterVertically
        ){

            SpanText(
                text = tester.email,
                modifier = TesterListItemSpanTextStyle.toModifier()
            )

            SpanText(
                text = tester.name,
                modifier = TesterListItemSpanTextStyle
                    .toModifier()
                    .minWidth(150.px).maxWidth(200.px)
            )

            StatusBadge(tester.status, strings)

            PlatformIcons(tester.platform)

            RelativeDate(
                date = tester.invitedAt ?: tester.createdAt,
                strings = strings
            )
        }

        ActionButtons(
            tester = tester,
            strings = strings,
            onAction = onAction
        )
    }
}



/**
 * Displays status badge with appropriate icon and color
 */
@Composable
private fun StatusBadge(
    status: TesterStatus,
    strings: Strings
) {
    Box(
        modifier = Modifier
            .minWidth(100.px)
            .maxWidth(120.px),
        contentAlignment = Alignment.Center
    ){
        Row(
            StatusBadgeStyle.toModifier()
                .background(status.Color().toRgb().copy(alpha = 20))
                .border(1.px, LineStyle.Solid, status.Color().toRgb().copy(alpha = 50)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            status.Icon()
            SpanText(
                text = status.displayName(strings),
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(status.Color())
                    .fontWeight(FontWeight.Medium)
            )
        }
    }

}

/**
 * Displays platform icons (Android, iOS, or both)
 */
@Composable
private fun PlatformIcons(platform: Platform) {
    Row(
        Modifier
            .minWidth(100.px).maxWidth(120.px)
            .gap(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (platform) {
             Android -> FaAndroid(
                size = IconSize.LG,
                modifier = Modifier.color(Color.rgb(0x3DDC84))
            )
            IOS -> FaApple(
                size = IconSize.LG,
                modifier = Modifier.color(Colors.White)
            )
            Both -> {
                FaAndroid(
                    size = IconSize.SM,
                    modifier = Modifier.color(Color.rgb(0x3DDC84))
                )
                FaApple(
                    size = IconSize.SM,
                    modifier = Modifier.color(Colors.White)
                )
            }
        }
    }
}

/**
 * Displays relative time
 */
@Composable
private fun RelativeDate(
    date: String,
    strings: Strings
) {

    SpanText(
        text = date,
        modifier = Modifier
            .minWidth(100.px)
            .fontSize(0.8.cssRem)
            .color(PageColors.textGray)
    )
}

/**
 * Displays action buttons based on tester status
 */
@Composable
private fun ActionButtons(
    tester: TesterDto,
    strings: Strings,
    onAction: (TesterAction, String) -> Unit
) {
    Row(
        Modifier.gap(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (tester.status) {
            TesterStatus.PENDING -> {
                ActionButton(
                    icon = { FaCheck(size = IconSize.SM, modifier = Modifier.color(PageColors.successGreen)) },
                    tooltip = strings.actionApprove,
                    onClick = { onAction(TesterAction.APPROVE, tester.id) }
                )
                ActionButton(
                    icon = { FaCircleXmark(size = IconSize.SM, modifier = Modifier.color(PageColors.errorRed)) },
                    tooltip = strings.actionReject,
                    onClick = { onAction(TesterAction.REJECT, tester.id) }
                )
            }
            TesterStatus.APPROVED, TesterStatus.INVITED -> {
                ActionButton(
                    icon = { FaPaperPlane(size = IconSize.SM, modifier = Modifier.color(PageColors.thePriceBlue)) },
                    tooltip = strings.actionResendInvite,
                    onClick = { onAction(TesterAction.RESEND_INVITE, tester.id) }
                )
            }

            TesterStatus.REJECTED -> {

            }
        }

        // Delete button always available
        ActionButton(
            icon = { FaTrash(size = IconSize.SM, modifier = Modifier.color(PageColors.errorRed)) },
            tooltip = strings.actionDelete,
            onClick = { onAction(TesterAction.DELETE, tester.id) }
        )
    }
}

/**
 * Single action button with icon
 */
@Composable
private fun ActionButton(
    icon: @Composable () -> Unit,
    tooltip: String,
    onClick: () -> Unit
) {
    Box(
        ActionButtonStyle.toModifier()
            .onClick { onClick() }
            .title(tooltip),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

/**
 * Displays empty state when no testers exist
 */
@Composable
private fun EmptyState(
    strings: Strings,
    onRefresh: () -> Unit
) {
    Column(
        EmptyStateStyle.toModifier(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        FaUsers(
            size = IconSize.XXL,
            modifier = Modifier.color(PageColors.textGray)
        )

        // Title
        SpanText(
            text = strings.emptyStateTitle,
            modifier = Modifier
                .fontSize(1.5.cssRem)
                .fontWeight(FontWeight.SemiBold)
                .color(Colors.White)
        )

        // Message
        SpanText(
            text = strings.emptyStateMessage,
            modifier = Modifier
                .fontSize(1.cssRem)
                .color(PageColors.textGray)
                .maxWidth(500.px)
        )

        // Refresh Button
        Box(
            ActionButtonStyle.toModifier()
                .padding(0.75.cssRem, 4.cssRem)
                .onClick { onRefresh() }
                .cursor(Cursor.Pointer),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier.gap(0.5.cssRem),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaRotateRight(
                    size = IconSize.SM,
                    modifier = Modifier.color(PageColors.thePriceBlue)
                )
                SpanText(
                    text = strings.refreshButton,
                    modifier = Modifier
                        .color(Colors.White)
                        .fontSize(0.9.cssRem)
                )
            }
        }
    }
}


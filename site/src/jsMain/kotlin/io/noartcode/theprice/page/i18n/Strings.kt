package io.noartcode.theprice.page.i18n

sealed class Strings {
    abstract val becomeATester: String
    abstract val signUpDescription: String
    abstract val namePlaceholder: String
    abstract val emailPlaceholder: String
    abstract val registerButton: String
    abstract val registeringButton: String
    abstract val footerTagline: String
    abstract val platformAndroid: String
    abstract val platformIOS: String
    abstract val platformBoth: String
    abstract val copyrightText: String
    abstract val madeWithKobweb:String
    abstract val successTitle: String
    abstract val errorTitle: String
    abstract val tryAgainButton: String
    abstract val defaultErrorMessage: String
    abstract val successMessage: String

    // Status labels
    abstract val statusPending: String
    abstract val statusApproved: String
    abstract val statusRejected: String
    abstract val statusInvited: String

    // Action labels
    abstract val actionApprove: String
    abstract val actionReject: String
    abstract val actionResendInvite: String
    abstract val actionDelete: String

    // Relative time functions
    abstract fun minutesAgo(count: Int): String
    abstract fun hoursAgo(count: Int): String
    abstract fun daysAgo(count: Int): String
    abstract fun weeksAgo(count: Int): String
    abstract fun monthsAgo(count: Int): String
    abstract fun yearsAgo(count: Int): String
    abstract val justNow: String

    // Table headers
    abstract val headerEmail: String
    abstract val headerName: String
    abstract val headerStatus: String
    abstract val headerPlatform: String
    abstract val headerDate: String
    abstract val headerActions: String

    // Empty state
    abstract val emptyStateTitle: String
    abstract val emptyStateMessage: String
    abstract val refreshButton: String

    data object English : Strings() {
        override val becomeATester = "Become a tester"
        override val signUpDescription = "Sign up with your name and email to receive beta builds of the app whenever we publish a new release."
        override val namePlaceholder = "Your name"
        override val emailPlaceholder = "you@email.com"
        override val registerButton = "Register me"
        override val registeringButton = "Registering..."
        override val footerTagline = "ThePrice helps you understand your cost of living by tracking your monthly bills."
        override val platformAndroid = "Android"
        override val platformIOS = "iOS"
        override val platformBoth = "Both"
        override val copyrightText = "Copyright © 2026 NOARTCODE LTDA. All Rights Reserved."
        override val madeWithKobweb = "Website built with"
        override val successTitle = "Success!"
        override val errorTitle = "Error"
        override val tryAgainButton = "Try Again"
        override val defaultErrorMessage = "Something went wrong. Please try again."
        override val successMessage: String = "Done. You've been added as a tester. You'll receive an email from us when a new version is available to try."

        // Status labels
        override val statusPending = "Pending"
        override val statusApproved = "Approved"
        override val statusRejected = "Rejected"
        override val statusInvited = "Invited"

        // Action labels
        override val actionApprove = "Approve"
        override val actionReject = "Reject"
        override val actionResendInvite = "Resend Invite"
        override val actionDelete = "Delete"

        // Relative time
        override fun minutesAgo(count: Int) = if (count == 1) "1 minute ago" else "$count minutes ago"
        override fun hoursAgo(count: Int) = if (count == 1) "1 hour ago" else "$count hours ago"
        override fun daysAgo(count: Int) = if (count == 1) "1 day ago" else "$count days ago"
        override fun weeksAgo(count: Int) = if (count == 1) "1 week ago" else "$count weeks ago"
        override fun monthsAgo(count: Int) = if (count == 1) "1 month ago" else "$count months ago"
        override fun yearsAgo(count: Int) = if (count == 1) "1 year ago" else "$count years ago"
        override val justNow = "Just now"

        // Table headers
        override val headerEmail = "Email"
        override val headerName = "Name"
        override val headerStatus = "Status"
        override val headerPlatform = "Platform"
        override val headerDate = "Date"
        override val headerActions = "Actions"

        // Empty state
        override val emptyStateTitle = "No Testers Yet"
        override val emptyStateMessage = "There are no testers registered at the moment. Check back later or refresh to see updates."
        override val refreshButton = "Refresh"
    }

    data object PortugueseBR : Strings() {
        override val becomeATester = "Torne-se um tester"
        override val signUpDescription = "Cadastre-se com seu nome e email para receber builds beta do app sempre que publicarmos uma nova versão."
        override val namePlaceholder = "Seu nome"
        override val emailPlaceholder = "voce@email.com"
        override val registerButton = "Registrar-me"
        override val registeringButton = "Registrando..."
        override val footerTagline = "ThePrice te ajuda entender seu custo de vida rastreando suas contas mensais."
        override val platformAndroid = "Android"
        override val platformIOS = "iOS"
        override val platformBoth = "Ambos"
        override val copyrightText = "Copyright © 2026 NOARTCODE LTDA. Todos os Direitos Reservados."
        override val madeWithKobweb = "Website feito com"
        override val successTitle = "Sucesso!"
        override val errorTitle = "Erro"
        override val tryAgainButton = "Tentar Novamente"
        override val defaultErrorMessage = "Algo deu errado. Por favor, tente novamente."
        override val successMessage: String = "Pronto. Você foi adicionado como testador. Você receberá um e-mail nosso quando uma nova versão estiver disponível para teste."

        // Status labels
        override val statusPending = "Pendente"
        override val statusApproved = "Aprovado"
        override val statusRejected = "Rejeitado"
        override val statusInvited = "Convidado"

        // Action labels
        override val actionApprove = "Aprovar"
        override val actionReject = "Rejeitar"
        override val actionResendInvite = "Reenviar Convite"
        override val actionDelete = "Excluir"

        // Relative time
        override fun minutesAgo(count: Int) = if (count == 1) "1 minuto atrás" else "$count minutos atrás"
        override fun hoursAgo(count: Int) = if (count == 1) "1 hora atrás" else "$count horas atrás"
        override fun daysAgo(count: Int) = if (count == 1) "1 dia atrás" else "$count dias atrás"
        override fun weeksAgo(count: Int) = if (count == 1) "1 semana atrás" else "$count semanas atrás"
        override fun monthsAgo(count: Int) = if (count == 1) "1 mês atrás" else "$count meses atrás"
        override fun yearsAgo(count: Int) = if (count == 1) "1 ano atrás" else "$count anos atrás"
        override val justNow = "Agora mesmo"

        // Table headers
        override val headerEmail = "E-mail"
        override val headerName = "Nome"
        override val headerStatus = "Status"
        override val headerPlatform = "Plataforma"
        override val headerDate = "Data"
        override val headerActions = "Ações"

        // Empty state
        override val emptyStateTitle = "Nenhum Testador Ainda"
        override val emptyStateMessage = "Não há testadores registrados no momento. Volte mais tarde ou atualize para ver mudanças."
        override val refreshButton = "Atualizar"
    }
}
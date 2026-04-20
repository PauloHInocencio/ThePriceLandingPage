package io.noartcode.theprice.page.i18n

sealed class Strings {
    abstract val becomeATester: String
    abstract val signUpDescription: String
    abstract val namePlaceholder: String
    abstract val emailPlaceholder: String
    abstract val registerButton: String
    abstract val footerTagline: String
    abstract val platformAndroid: String
    abstract val platformIOS: String
    abstract val platformBoth: String
    abstract val copyrightText: String
    abstract val madeWithKobweb:String

    data object English : Strings() {
        override val becomeATester = "Become a tester"
        override val signUpDescription = "Sign up with your name and email to receive beta builds of the app whenever we publish a new release."
        override val namePlaceholder = "Your name"
        override val emailPlaceholder = "you@email.com"
        override val registerButton = "Register me"
        override val footerTagline = "ThePrice helps you understand your cost of living by tracking your monthly bills."
        override val platformAndroid = "Android"
        override val platformIOS = "iOS"
        override val platformBoth = "Both"
        override val copyrightText = "Copyright © 2026 NOARTCODE LTDA. All Rights Reserved."
        override val madeWithKobweb = "Website built with"
    }

    data object PortugueseBR : Strings() {
        override val becomeATester = "Torne-se um tester"
        override val signUpDescription = "Cadastre-se com seu nome e email para receber builds beta do app sempre que publicarmos uma nova versão."
        override val namePlaceholder = "Seu nome"
        override val emailPlaceholder = "voce@email.com"
        override val registerButton = "Registrar-me"
        override val footerTagline = "ThePrice te ajuda entender seu custo de vida rastreando suas contas mensais."
        override val platformAndroid = "Android"
        override val platformIOS = "iOS"
        override val platformBoth = "Ambos"
        override val copyrightText = "Copyright © 2026 NOARTCODE LTDA. Todos os Direitos Reservados."
        override val madeWithKobweb = "Website feito com"
    }
}
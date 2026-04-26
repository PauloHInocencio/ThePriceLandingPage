package io.noartcode.theprice.page.pages.state

sealed interface SubmissionState {
    data object Idle : SubmissionState
    data object Submitting : SubmissionState
    data object Success : SubmissionState
    data object Error : SubmissionState
}
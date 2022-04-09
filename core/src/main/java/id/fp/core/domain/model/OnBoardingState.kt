package id.fp.core.domain.model

sealed class OnBoardingState {
    data class COMPLET(val list: List<OnBoardingPart>) : OnBoardingState()
}

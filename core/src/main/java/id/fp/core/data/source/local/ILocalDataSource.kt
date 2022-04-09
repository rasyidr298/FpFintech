package id.fp.core.data.source.local

import id.fp.core.domain.model.OnBoardingPart
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(isComplete: Boolean)
    fun getOnBoardingList(): List<OnBoardingPart>
    suspend fun clearAppData()
}
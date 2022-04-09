package id.fp.core.data.source.local

import id.fp.core.R
import id.fp.core.data.source.local.entity.SampleEntity
import id.fp.core.data.source.local.room.Dao
import id.fp.core.domain.model.OnBoardingPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val mDao: Dao) : ILocalDataSource {

    //content feature
    suspend fun insertSample(insert: List<SampleEntity>) = mDao.insertSample(insert)

    fun getSample(): Flow<List<SampleEntity>> = mDao.getSample()

    fun searchSample(search: String): Flow<List<SampleEntity>> =
        mDao.searchSample(search).flowOn(Dispatchers.Default).conflate()

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return flow { emit(true) }
    }

    override suspend fun setOnboardingCompleted(isComplete: Boolean) {

    }

    override suspend fun clearAppData() {

    }

    override fun getOnBoardingList(): List<OnBoardingPart> {
        return listOf(
            OnBoardingPart(
                image = R.raw.lottie_money,
                title = R.string.title_onboarding_1,
                description = R.string.description_onboarding_1,
            ),
            OnBoardingPart(
                image = R.raw.lottie_money_circle,
                title = R.string.title_onboarding_2,
                description =  R.string.description_onboarding_2,
            ),
            OnBoardingPart(
                image = R.raw.lottie_market_analyst,
                title = R.string.title_onboarding_3,
                description =  R.string.description_onboarding_3,
            )
        )
    }
}
package id.fp.core.data.source.remote.mapper

import id.fp.core.data.source.remote.models.ExchangeDto
import id.fp.core.domain.model.Exchange

object ExchangeMapper {
    fun ExchangeDto.toDomain() = Exchange(
        id = this.id,
        name = this.name,
        year_established = this.year_established,
        country = this.country,
        description = this.description,
        url = this.url,
        image = this.image,
        has_trading_incentive = this.has_trading_incentive,
        trust_score = this.trust_score,
        trust_score_rank = this.trust_score_rank,
        trade_volume_24h_btc = this.trade_volume_24h_btc,
        trade_volume_24h_btc_normalized = this.trade_volume_24h_btc_normalized
    )

    fun List<ExchangeDto>?.toDomain() = this?.map {
        it.toDomain()
    } ?: emptyList()
}
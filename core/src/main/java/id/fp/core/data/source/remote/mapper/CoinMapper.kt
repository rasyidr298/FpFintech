package id.fp.core.data.source.remote.mapper

import id.fp.core.data.source.remote.models.CoinDto
import id.fp.core.domain.model.Coin

object CoinMapper {
    fun CoinDto.toDomain() = Coin(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        image = this.image,
        current_price = this.current_price,
        market_cap = this.market_cap,
        market_cap_rank = this.market_cap_rank,
        fully_diluted_valuation = this.fully_diluted_valuation,
        total_volume = this.total_volume,
        high_24h = this.high_24h,
        low_24h = this.low_24h,
        price_change_24h = this.price_change_24h,
        price_change_percentage_24h = this.price_change_percentage_24h,
        market_cap_change_24h = this.market_cap_change_24h,
        market_cap_change_percentage_24h = this.market_cap_change_percentage_24h,
        ath = this.ath,
        max_supply = this.max_supply
    )

    fun List<CoinDto>?.toDomain() = this?.map {
        it.toDomain()
    } ?: emptyList()
}
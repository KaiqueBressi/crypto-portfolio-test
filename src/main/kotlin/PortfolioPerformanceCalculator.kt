import kotlinx.coroutines.*
import java.math.BigDecimal

class PortfolioPerformanceCalculator(private val assetAPI: AssetAPI) {
    suspend fun calculate(assets: Map<String, Asset>): Portfolio = withContext(Dispatchers.IO) {
        val assetsRequests = assets.values.map { asset ->
            async {
                val assetsData = assetAPI.getAssets()
                val assetId = assetsData.find { it.symbol == asset.symbol }?.id

                if (assetId != null) {
                    val assetPrice = assetAPI.getAssetHistoryPrice(assetId, "d1", 1617753600000, 1617753601000)

                    return@async Asset(asset.symbol, asset.quantity, assetPrice)
                } else {
                    return@async null
                }
            }
        }

        val currentAssets = assetsRequests.awaitAll().filterNotNull().filter { assets[it.symbol] != null }

        val total = currentAssets.fold(BigDecimal(0)) { total, asset -> asset.price * asset.quantity + total }
        val bestAsset = currentAssets.maxBy { asset -> asset.total - assets[asset.symbol]?.total!! }
        val bestPerformance = currentAssets.maxOf { asset -> asset.total / assets[asset.symbol]?.total!! }
        val worstAsset = currentAssets.minBy { asset -> asset.total - assets[asset.symbol]?.total!! }
        val worstPerformance = currentAssets.minOf { asset -> asset.total / assets[asset.symbol]?.total!! }

        return@withContext Portfolio(total, bestAsset.symbol, bestPerformance, worstAsset.symbol, worstPerformance)
    }
}
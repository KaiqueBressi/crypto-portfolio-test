import io.ktor.client.engine.cio.*
import java.time.LocalTime
import java.util.logging.Logger

suspend fun main() {
    val logger = Logger.getAnonymousLogger()

    logger.info("Now is ${LocalTime.now()}")

    val assets = AssetCsvReader().readAssets("assets.csv")
    val assetAPI = AssetAPI(CIO.create())
    val portfolio = PortfolioPerformanceCalculator(assetAPI).calculate(assets)

    println("total=${portfolio.total}," +
            "best_asset=${portfolio.bestAsset}," +
            "best_performance=${portfolio.bestPerformance}," +
            "worst_asset=${portfolio.worstAsset}," +
            "worst_performance=${portfolio.worstPerformance}")

    logger.info("Finished at ${LocalTime.now()}")
}
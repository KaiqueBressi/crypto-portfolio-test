import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import java.time.LocalTime
import java.util.logging.Logger


@Serializable
data class AssetData(
    val id: String,
    val symbol: String
)

@Serializable
data class AssetsData(
    val data: List<AssetData>
)

@Serializable
data class AssetHistoryPrice(
    val priceUsd: String
)

@Serializable
data class AssetHistoryData(
    val data: List<AssetHistoryPrice>,
)

class AssetAPI(private val httpClientEngine: HttpClientEngine) {
    private val logger = Logger.getLogger(this::class.simpleName)
    private val httpClient = HttpClient(httpClientEngine) {
       install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
       }
    }

    suspend fun getAssets(): List<AssetData> {
        val response = httpClient.get("https://api.coincap.io/v2/assets")

        return response.body<AssetsData>().data
    }

    suspend fun getAssetHistoryPrice(id: String, interval: String, start: Long, end: Long): BigDecimal {
        logger.info("Submitted request $id at ${LocalTime.now()}")

        val response = httpClient.get("https://api.coincap.io/v2/assets/$id/history?interval=$interval&start=$start&end=$end")

        return response.body<AssetHistoryData>().data.first().priceUsd.toBigDecimal()
    }
}

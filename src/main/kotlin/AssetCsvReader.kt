import java.io.File

class AssetCsvReader {
    fun readAssets(assetsFilename: String): Map<String, Asset> {
        return File(ClassLoader.getSystemResource(assetsFilename).file)
            .readLines()
            .drop(1)
            .filter { it.isNotBlank() }
            .map {
                val (symbol, quantity, price) = it.split(',', ignoreCase = false, limit = 3)

                Asset(symbol, quantity.toBigDecimal(), price.toBigDecimal())
            }.associateBy { it.symbol }
    }
}
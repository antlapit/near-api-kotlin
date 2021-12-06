package antlapit.near.api.json

data class DeserializationTestData<T>(
    val raw: String,
    val typed: T
)

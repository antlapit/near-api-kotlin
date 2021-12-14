package antlapit.near.api

import antlapit.near.api.providers.util.toHexString
import org.komputing.kbase58.decodeBase58

class KeysTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Anu7LYDfpLtkP7E16LT9imXF694BdQaa9ufVkQiwTQxC".decodeBase58().toHexString())
        }
    }
}

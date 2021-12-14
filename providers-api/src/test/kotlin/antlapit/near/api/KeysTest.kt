package antlapit.near.api

import antlapit.near.api.providers.util.fromHex
import antlapit.near.api.providers.util.toHexString
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String

class KeysTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Anu7LYDfpLtkP7E16LT9imXF694BdQaa9ufVkQiwTQxC".decodeBase58().toHexString())


            println("0f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef800".fromHex().encodeToBase58String())
        }
    }
}

package com.ragabuza.bitcoinmonitor.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 * Created by diego.moyses on 12/12/2017.
 */
data class ProvidersList(val FOX: ProviderItem,
                         val MBT: ProviderItem,
                         val NEG: ProviderItem,
                         val B2U: ProviderItem,
                         val BTD: ProviderItem,
                         val FLW: ProviderItem,
                         val LOC: ProviderItem,
                         val ARN: ProviderItem){

    class Deserializer: ResponseDeserializable<ProvidersList> {
        override fun deserialize(content: String): ProvidersList? = Gson().fromJson(content, ProvidersList::class.java)
    }
}
data class ProviderItem(val ask: Float)
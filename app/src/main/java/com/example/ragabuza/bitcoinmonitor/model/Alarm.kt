package com.example.ragabuza.bitcoinmonitor.model

/**
 * Created by diego on 10/12/2017.
 */

enum class Condition(val value: Int) { GREATER(1), LESSER(-1) }
enum class AlarmType { SIMPLE, SOUND, LOUD, RINGER }
enum class Providers(val nome: String) {
FOX("FoxBit"),
MBT("Mercado Bitcoin"),
NEG("Negocie Coins"),
B2U("BitcoinToYou"),
BTD("BitcoinTrade"),
FLW("flowBTC"),
LOC("LocalBitcoins"),
ARN("Arena Bitcoin")}
enum class AlarmTimes(val value: Int){
    M15(15),
    M30(30),
    H1(60),
    H6(60*6),
    H12(60*12),
    H24(60*24)
}


data class Alarm(val id: Long, val value: Long, val condition: Condition, val provider: String, val type: AlarmType)
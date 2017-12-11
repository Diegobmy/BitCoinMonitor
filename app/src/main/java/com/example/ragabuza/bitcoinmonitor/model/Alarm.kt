package com.example.ragabuza.bitcoinmonitor.model

/**
 * Created by diego on 10/12/2017.
 */

enum class Condition(val value: Int) {
    GREATER(1), LESSER(-1)
}
enum class AlarmType(val value: Int) { SIMPLE(0), SOUND(1), LOUD(2), RINGER(3)}

data class Alarm(val id: Long, val value: Long, val condition: Condition, val provider: String, val time: Int, val type: AlarmType)
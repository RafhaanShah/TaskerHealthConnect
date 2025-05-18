package com.rafapps.taskerhealthconnect

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class FakeClock(private val timeMs: Long = 0): Clock() {
    override fun instant(): Instant {
        TODO("Not yet implemented")
    }

    override fun withZone(zone: ZoneId?): Clock {
        TODO("Not yet implemented")
    }

    override fun getZone(): ZoneId {
        TODO("Not yet implemented")
    }

    override fun millis(): Long = timeMs
}

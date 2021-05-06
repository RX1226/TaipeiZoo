package com.rx.application.http

interface Config {
    companion object {
        const val READ_TIMEOUT : Long = 20 * 1000
        const val CONNECT_TIMEOUT : Long = 10 * 1000
        const val RETRY_DELAY_MILLIS : Long = 30 * 1000
        const val RETRY_COUNT : Int = 5
    }
}
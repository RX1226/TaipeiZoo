package com.rx.application.http

interface ApiCode {
    companion object {
        const val SUCCESS : Int = 200
        const val CREATED : Int = 201
        const val UNAUTHORIZED : Int = 401
        const val FORBIDDEN : Int = 403
        const val NOT_FOUND : Int = 404
        const val INTERNAL_SERVER_ERROR : Int = 500
    }
}
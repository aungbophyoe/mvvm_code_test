package com.aungbophyoe.data.repo

import com.aungbophyoe.data.utility.Failure
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

interface CleanRepository {
    fun handleException(e: Throwable): Failure {
        return when (e) {
            is IOException -> Failure.Network(e.message.toString())
            is ConnectException -> Failure.Network(e.message.toString())
            is UnknownHostException -> Failure.Network(e.message.toString())
            is InterruptedException -> Failure.Network(e.message.toString())
            is JsonSyntaxException -> Failure.WrongFormat(e.message.toString())
            is JsonParseException -> Failure.WrongFormat(e.message.toString())
            is HttpException -> when (e.code()) {
                400 -> Failure.BadRequest((e.message.toString()))
                401 -> Failure.AccessDenied((e.message.toString()))
                404 -> Failure.NotFound((e.message.toString()))
                else -> Failure.ServiceUnavailable((e.message.toString()))
            }
            else -> Failure.Unknown((e.message.toString()))
        }
    }
}
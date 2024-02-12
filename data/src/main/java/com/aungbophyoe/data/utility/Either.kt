package com.aungbophyoe.data.utility

typealias RESULT<V> = Either<Failure, V>


sealed class Either<A, B> {
    class FAILED<A, B>(val ERROR: A) : Either<A, B>()
    class SUCCESS<A, B>(val DATA: B) : Either<A, B>()
}


abstract class Failure : Throwable() {

    data class Unknown(val errorMessage : String?) : Failure()

    data class ServiceUnavailable(val errorMessage : String?) : Failure()

    data class Network(val errorMessage : String?) : Failure()

    data class NotFound(val errorMessage : String?) : Failure()

    data class AccessDenied(val errorMessage : String?) : Failure()

    data class BadRequest(val errorMessage : String?) : Failure()

    data class WrongFormat(val errorMessage : String?) : Failure()
}
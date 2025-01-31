package io.bootsolana.errors

data class InvalidPublicKey(override val message: String) : Exception(message)
data class InvalidPrivateKey(override val message: String) : Exception(message)
package io.bootsolana.errors

data class TransferFailedException(override val message:String):Exception(message){}
data class AccountBalanceRequestException(override val message:String):Exception(message){}
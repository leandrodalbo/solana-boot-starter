package com.solanaBoot.starter.errors

data class TransferFailedException(override val message:String):Exception(message){}
package io.solana.boot.response

data class AccountBalanceResponse(
    val result: AccountBalance
)

data class AccountBalance(
    val value: Long
)

data class TransactionResponse(val result: String)
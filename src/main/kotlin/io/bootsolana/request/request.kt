package io.bootsolana.request

import java.util.UUID

data class RpcRequest(
    val jsonrpc: String = JSON_RPC_VERSION,
    val id: String = generateRequestId(),
    val method: String,
    val params: List<Any>
) {
    companion object {
        private const val JSON_RPC_VERSION = "2.0"

        private fun generateRequestId(): String {
            return UUID.randomUUID().toString()
        }

        fun createTransactionMessage(from: String, to: String, amount: Long): String =
            """{"from": "$from","to": $to, "amount": $amount}"""

        fun createTransactionRequest(message: String, signatureBase64: String): RpcRequest {
            return RpcRequest(
                method = RpcRequestMethod.SEND_TRANSACTION.methodName,
                params = listOf(
                    message, signatureBase64
                )
            )
        }

        fun createGetBalanceRequest(publicKey: String): RpcRequest {
            return RpcRequest(
                method = RpcRequestMethod.GET_BALANCE.methodName,
                params = listOf(publicKey)
            )
        }
    }


}

enum class RpcRequestMethod(val methodName: String) {
    GET_BALANCE("getBalance"),
    GET_ACCOUNT_INFO("getAccountInfo"),
    GET_BLOCK_HEIGHT("getBlockHeight"),
    GET_TRANSACTION("getTransaction"),
    SEND_TRANSACTION("sendTransaction");

    override fun toString(): String = methodName
}


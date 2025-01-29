package io.solana.boot.solana

import org.springframework.web.client.RestClient
import java.security.PrivateKey
import java.security.PublicKey

class Solana(val client: RestClient) {

    fun transfer(
        base64SenderPrivateKey: String,
        base64SenderPublicKey: String,
        base64ReceiverPublicKey: String,
        amount: Long
    ): String {
        val senderPrivateKey: PrivateKey = io.solana.boot.keys.KeyUtils.decodePrivateKey(base64SenderPrivateKey)
        val senderPublicKey: PublicKey = io.solana.boot.keys.KeyUtils.decodePublicKey(base64SenderPublicKey)
        val receiverPublicKey: PublicKey = io.solana.boot.keys.KeyUtils.decodePublicKey(base64ReceiverPublicKey)

        val transactionMessage = io.solana.boot.request.RpcRequest.createTransactionMessage(
            io.solana.boot.keys.KeyUtils.encodeToBase58(io.solana.boot.keys.KeyUtils.encodeToBase64(senderPublicKey.encoded)),
            io.solana.boot.keys.KeyUtils.encodeToBase58(io.solana.boot.keys.KeyUtils.encodeToBase64(receiverPublicKey.encoded)),
            amount
        )

        val signedTransaction = io.solana.boot.keys.KeyUtils.signTransaction(transactionMessage.toByteArray(), senderPrivateKey)

        val response = client.post()
            .body(io.solana.boot.request.RpcRequest.createTransactionRequest(transactionMessage, signedTransaction))
            .retrieve()
            .body(io.solana.boot.response.TransactionResponse::class.java)
            ?: throw io.solana.boot.errors.AccountBalanceRequestException("Transaction failed sender: $senderPublicKey, receiver:$receiverPublicKey, amount: $amount")

        return response.result
    }

    fun getBalance(base64PublicKey: String): io.solana.boot.response.AccountBalance {
        val response = client.post()
            .body(io.solana.boot.request.RpcRequest.createGetBalanceRequest(base64PublicKey))
            .retrieve()
            .body(io.solana.boot.response.AccountBalanceResponse::class.java)
            ?: throw io.solana.boot.errors.AccountBalanceRequestException("Failed to fetch balance for ${base64PublicKey}")

        return response.result
    }

}
package io.solana.boot.solana

import io.solana.boot.errors.AccountBalanceRequestException
import io.solana.boot.keys.KeyUtils
import io.solana.boot.request.RpcRequest
import io.solana.boot.response.AccountBalance
import io.solana.boot.response.AccountBalanceResponse
import io.solana.boot.response.TransactionResponse
import org.springframework.web.client.RestClient
import java.security.PrivateKey

class Solana(val client: RestClient) {

    fun transfer(
        base64SenderPrivateKey: String,
        senderPublicKey: String,
        receiverPublicKey: String,
        amount: Long
    ): String {
        val senderPrivateKey: PrivateKey = KeyUtils.decodePrivateKey(base64SenderPrivateKey)

        val transactionMessage = RpcRequest.createTransactionMessage(
            KeyUtils.encodeToBase58(senderPublicKey),
            KeyUtils.encodeToBase58(receiverPublicKey),
            amount
        )

        val signedTransaction = KeyUtils.signTransaction(transactionMessage.toByteArray(), senderPrivateKey)

        val response = client.post()
            .body(RpcRequest.createTransactionRequest(transactionMessage, signedTransaction))
            .retrieve()
            .body(TransactionResponse::class.java)
            ?: throw AccountBalanceRequestException("Transaction failed sender: $senderPublicKey, receiver:$receiverPublicKey, amount: $amount")

        return response.result
    }

    fun getBalance(publicKey: String): AccountBalance {
        val response = client.post()
            .body(RpcRequest.createGetBalanceRequest(publicKey))
            .retrieve()
            .body(AccountBalanceResponse::class.java)
            ?: throw AccountBalanceRequestException("Failed to fetch balance for ${publicKey}")

        return response.result
    }

}
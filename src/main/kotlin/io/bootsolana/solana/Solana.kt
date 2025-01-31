package io.bootsolana.solana

import io.bootsolana.errors.InvalidPrivateKey
import io.bootsolana.errors.InvalidPublicKey
import org.bitcoinj.core.Base58
import org.p2p.solanaj.core.Account
import org.p2p.solanaj.core.PublicKey
import org.p2p.solanaj.core.Transaction
import org.p2p.solanaj.programs.SystemProgram
import org.p2p.solanaj.rpc.RpcClient

class Solana(val client: RpcClient) {

    fun transfer(
        base58SenderPrivateKey: String,
        receiverPublicKey: String,
        sol: Double
    ): String =
        kotlin.runCatching {
            val fromAccount = accountFromPrivateKey(base58SenderPrivateKey)
            val toPublicKey = publicKeyFromString(receiverPublicKey)
            val lamports = lamportsFromSol(sol)
            val transaction = Transaction()

            transaction.addInstruction(SystemProgram.transfer(fromAccount.publicKey, toPublicKey, lamports))
            return client.api.sendTransaction(transaction, fromAccount)
        }.getOrElse { throw it }


    fun getBalance(publicKey: String): Double = kotlin.runCatching {
        val key = publicKeyFromString(publicKey)
        lamportsToSol(client.api.getBalance(key))
    }.getOrElse { throw it }


    private fun lamportsToSol(lamports: Long) = lamports / 1_000_000_000.0

    private fun lamportsFromSol(sol: Double) = (sol * 1_000_000_000).toLong()

    private fun publicKeyFromString(publicKey: String): PublicKey = kotlin.runCatching {
        return PublicKey(publicKey)
    }.getOrElse { throw InvalidPublicKey("invalid-key: $publicKey") }

    private fun accountFromPrivateKey(base58Key: String): Account = kotlin.runCatching {
        return Account(Base58.decode(base58Key))
    }.getOrElse { throw InvalidPrivateKey("invalid-key: $base58Key") }
}
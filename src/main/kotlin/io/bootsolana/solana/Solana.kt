package io.bootsolana.solana

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
    ): String {
        try {
            val fromAccount = Account(Base58.decode(base58SenderPrivateKey))
            val toPublicKey = PublicKey(receiverPublicKey)
            val lamports = lamportsFromSol(sol)

            val transaction = Transaction()
            transaction.addInstruction(SystemProgram.transfer(fromAccount.publicKey, toPublicKey, lamports))
            return client.api.sendTransaction(transaction, fromAccount)

        } catch (e: Exception) {
            throw RuntimeException("Transaction failed", e)
        }
    }


    fun getBalance(publicKey: String): Double = lamportsToSol(client.api.getBalance(PublicKey(publicKey)))

    private fun lamportsToSol(lamports: Long) = lamports / 1_000_000_000.0

    private fun lamportsFromSol(sol: Double) = (sol * 1_000_000_000).toLong()
}
package com.solanaBoot.starter.solana

import com.solanaBoot.starter.errors.TransferFailedException
import org.p2p.solanaj.core.Account
import org.p2p.solanaj.core.Transaction
import org.p2p.solanaj.programs.SystemProgram
import org.p2p.solanaj.rpc.RpcClient


class Solana(val rpcClient: RpcClient) {

    fun transfer(sender: Account, receiver: Account, amount: Long): String {
        val transaction = Transaction()
        val transferInstruction = SystemProgram.transfer(sender.publicKey, receiver.publicKey, amount)
        transaction.addInstruction(transferInstruction)

        return try {
            rpcClient.api.sendTransaction(transaction, sender)
        } catch (e: Exception) {
            throw TransferFailedException("from:${sender.publicKey}, to:${receiver.publicKey}, amount:$amount")
        }
    }
}
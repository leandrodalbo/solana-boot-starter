package com.solanaBoot.starter.solana

import com.solanaBoot.starter.errors.TransferFailedException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.p2p.solanaj.core.Account
import org.p2p.solanaj.rpc.RpcClient
import org.p2p.solanaj.rpc.RpcException


class SolanaTest {
    private val rpcClient: RpcClient = mockk()
    private val solana: Solana = Solana(rpcClient)

    @Test
    fun shouldTransferSolana() {
        every { rpcClient.api.sendTransaction(any(), any()) } returns "transaction-completed"

        val sender = Account()
        val receiver = Account()
        val lamports = 1000000000L

        val result = solana.transfer(sender, receiver, lamports)

        assertThat("transaction-completed").isEqualTo(result)

        verify { rpcClient.api.sendTransaction(any(), any()) }
    }

    @Test
    fun shouldHandleAFailedTransaction() {
        every { rpcClient.api.sendTransaction(any(), any()) } throws RpcException("Transaction Failed")

        val sender = Account()
        val receiver = Account()
        val lamports = 1000000000L

        assertThatExceptionOfType(TransferFailedException::class.java)
            .isThrownBy { solana.transfer(sender, receiver, lamports) }

        verify { rpcClient.api.sendTransaction(any(), any()) }
    }

}
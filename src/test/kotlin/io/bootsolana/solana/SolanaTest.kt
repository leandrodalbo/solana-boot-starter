package io.bootsolana.solana

import io.bootsolana.errors.InvalidPrivateKey
import io.bootsolana.errors.InvalidPublicKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.bitcoinj.core.Base58
import org.junit.jupiter.api.Test
import org.p2p.solanaj.rpc.RpcClient
import org.p2p.solanaj.utils.TweetNaclFast.Signature.KeyPair

class SolanaTest {

    val keyPair = KeyPair()

    val rpcClient: RpcClient = mockk()

    val solana: Solana = Solana(rpcClient)

    @Test
    fun shouldCompleteATransaction() {
        val senderPrivateKey = Base58.encode(keyPair.secretKey)
        val recipientPublicKey = Base58.encode(keyPair.publicKey)
        val amount = 2.0
        val transactionHash = Base58.encode("success".toByteArray())

        every { rpcClient.api.sendTransaction(any(), any()) } returns transactionHash

        assertThat(transactionHash).isEqualTo(solana.transfer(senderPrivateKey, recipientPublicKey, amount))

        verify { rpcClient.api.sendTransaction(any(), any()) }

    }

    @Test
    fun shouldHandleAnInvalidSenderKey() {
        val senderPrivateKey = "invalidKey"
        val recipientPublicKey = Base58.encode(keyPair.publicKey)
        val amount = 2.0

        assertThatExceptionOfType(InvalidPrivateKey::class.java)
            .isThrownBy { solana.transfer(senderPrivateKey, recipientPublicKey, amount) }

    }

    @Test
    fun shouldHandleAnInvalidRecipientKey() {
        val senderPrivateKey = Base58.encode(keyPair.secretKey)
        val recipientPublicKey = "invalidKey"
        val amount = 2.0

        assertThatExceptionOfType(InvalidPublicKey::class.java)
            .isThrownBy { solana.transfer(senderPrivateKey, recipientPublicKey, amount) }
    }

    @Test
    fun shouldHandleAnySolanaJException() {
        val senderPrivateKey = Base58.encode(keyPair.secretKey)
        val recipientPublicKey = Base58.encode(keyPair.publicKey)
        val amount = 2.0

        every { rpcClient.api.sendTransaction(any(), any()) } throws Exception()

        assertThatExceptionOfType(Exception::class.java)
            .isThrownBy { solana.transfer(senderPrivateKey, recipientPublicKey, amount) }

        verify { rpcClient.api.sendTransaction(any(), any()) }

    }

    @Test
    fun shouldGetBalance() {
        val publicKey = Base58.encode(keyPair.publicKey)

        every { rpcClient.api.getBalance(any()) } returns 34

        assertThat(34 / 1_000_000_000.0).isEqualTo(solana.getBalance(publicKey))

        verify { rpcClient.api.getBalance(any()) }

    }

    @Test
    fun shouldHandleGetBalanceError() {
        val publicKey = Base58.encode(keyPair.publicKey)

        every { rpcClient.api.getBalance(any()) } throws Exception()

        assertThatExceptionOfType(Exception::class.java)
            .isThrownBy { solana.getBalance(publicKey) }

        verify { rpcClient.api.getBalance(any()) }
    }

    @Test
    fun shouldNotGetTheBalanceForInvalidKeys() {
        val publicKey = "invalidkey"

        assertThatExceptionOfType(InvalidPublicKey::class.java)
            .isThrownBy { solana.getBalance(publicKey) }

    }

}
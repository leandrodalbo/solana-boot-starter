package io.solana.boot.solana

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClient
import java.security.KeyPairGenerator


class SolanaTest {
    private val client: RestClient = mockk()
    private val solana: io.solana.boot.solana.Solana = io.solana.boot.solana.Solana(client)

    private val testKeyPair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()

    @Test
    fun shouldFetchAccountBalance() {
        val mockResponse = io.solana.boot.response.AccountBalanceResponse(io.solana.boot.response.AccountBalance(1000L))

        val mockRequestSpec = mockk<RestClient.RequestBodyUriSpec>()
        val mockRetrieveSpec = mockk<RestClient.ResponseSpec>()

        every { client.post() } returns mockRequestSpec
        every { mockRequestSpec.uri(any<String>()) } returns mockRequestSpec
        every { mockRequestSpec.header(any(), any()) } returns mockRequestSpec
        every { mockRequestSpec.body(any<io.solana.boot.request.RpcRequest>()) } returns mockRequestSpec
        every { mockRequestSpec.retrieve() } returns mockRetrieveSpec
        every { mockRetrieveSpec.body(io.solana.boot.response.AccountBalanceResponse::class.java) } returns mockResponse


        val result = solana.getBalance(io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.public.encoded))

        assertThat(io.solana.boot.response.AccountBalance(1000L)).isEqualTo(result)

        verify { client.post() }
        verify { mockRetrieveSpec.body(io.solana.boot.response.AccountBalanceResponse::class.java) }
    }

    @Test
    fun shouldHandleGetBalanceError() {
        every { client.post() } throws io.solana.boot.errors.AccountBalanceRequestException("Getting Balance Failed")

        assertThatExceptionOfType(io.solana.boot.errors.AccountBalanceRequestException::class.java)
            .isThrownBy { solana.getBalance("aafdgarubishgfq3dfwr") }

        verify { client.post() }
    }

    //COMPLETE TRANSACTION TEST

}
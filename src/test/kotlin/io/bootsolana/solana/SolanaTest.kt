package io.bootsolana.solana

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.bootsolana.errors.AccountBalanceRequestException
import io.bootsolana.request.RpcRequest
import io.bootsolana.response.AccountBalance
import io.bootsolana.response.AccountBalanceResponse
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClient
import java.security.KeyPairGenerator


class SolanaTest {
    private val client: RestClient = mockk()
    private val solana: Solana = Solana(client)

    private val testKeyPair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()

    @Test
    fun shouldFetchAccountBalance() {
        val mockResponse = AccountBalanceResponse(AccountBalance(1000L))

        val mockRequestSpec = mockk<RestClient.RequestBodyUriSpec>()
        val mockRetrieveSpec = mockk<RestClient.ResponseSpec>()

        every { client.post() } returns mockRequestSpec
        every { mockRequestSpec.uri(any<String>()) } returns mockRequestSpec
        every { mockRequestSpec.header(any(), any()) } returns mockRequestSpec
        every { mockRequestSpec.body(any<RpcRequest>()) } returns mockRequestSpec
        every { mockRequestSpec.retrieve() } returns mockRetrieveSpec
        every { mockRetrieveSpec.body(AccountBalanceResponse::class.java) } returns mockResponse


        val result = solana.getBalance("aafdgarubishgfq3h5j65sfgh6h8fghdfwrsdsg5a6eg")

        assertThat(AccountBalance(1000L)).isEqualTo(result)

        verify { client.post() }
        verify { mockRetrieveSpec.body(AccountBalanceResponse::class.java) }
    }

    @Test
    fun shouldHandleGetBalanceError() {
        every { client.post() } throws AccountBalanceRequestException("Getting Balance Failed")

        assertThatExceptionOfType(AccountBalanceRequestException::class.java)
            .isThrownBy { solana.getBalance("aa3f432dgaru678bish4346gf678q3dfw6r") }

        verify { client.post() }
    }

    @Test
    fun shouldCompleteATransaction() {
        //TODO
    }

    @Test
    fun shouldHandleATransactionError() {
        //TODO
    }

}
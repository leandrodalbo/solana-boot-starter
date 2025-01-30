package io.solana.boot.solana

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.solana.boot.errors.AccountBalanceRequestException
import io.solana.boot.request.RpcRequest
import io.solana.boot.response.AccountBalance
import io.solana.boot.response.AccountBalanceResponse
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClient
import java.security.KeyPairGenerator


class SolanaTest {
    private val client: RestClient = mockk()
    private val solana: Solana = Solana(client)

    private val testKeyPair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()
//
//    @Test
//    fun shouldFetchAccountBalance() {
//        val mockResponse = AccountBalanceResponse(AccountBalance(1000L))
//
//        val mockRequestSpec = mockk<RestClient.RequestBodyUriSpec>()
//        val mockRetrieveSpec = mockk<RestClient.ResponseSpec>()
//
//        every { client.post() } returns mockRequestSpec
//        every { mockRequestSpec.uri(any<String>()) } returns mockRequestSpec
//        every { mockRequestSpec.header(any(), any()) } returns mockRequestSpec
//        every { mockRequestSpec.body(any<RpcRequest>()) } returns mockRequestSpec
//        every { mockRequestSpec.retrieve() } returns mockRetrieveSpec
//        every { mockRetrieveSpec.body(AccountBalanceResponse::class.java) } returns mockResponse
//
//
//        val result = solana.getBalance(io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.public.encoded))
//
//        assertThat(AccountBalance(1000L)).isEqualTo(result)
//
//        verify { client.post() }
//        verify { mockRetrieveSpec.body(AccountBalanceResponse::class.java) }
//    }
//
//    @Test
//    fun shouldHandleGetBalanceError() {
//        every { client.post() } throws AccountBalanceRequestException("Getting Balance Failed")
//
//        assertThatExceptionOfType(AccountBalanceRequestException::class.java)
//            .isThrownBy { solana.getBalance("aafdgarubishgfq3dfwr") }
//
//        verify { client.post() }
//    }
//
    //COMPLETE TRANSACTION TEST

}
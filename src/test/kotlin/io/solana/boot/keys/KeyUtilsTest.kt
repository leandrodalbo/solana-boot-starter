package io.solana.boot.keys

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.security.KeyPairGenerator
import java.security.interfaces.EdECPrivateKey
import java.security.interfaces.EdECPublicKey

class KeyUtilsTest {

    private val testKeyPair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()

    @Test
    fun shouldDecodeABase64PrivateKey() {
        val encoded = io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.private.encoded)

        val result = io.solana.boot.keys.KeyUtils.decodePrivateKey(encoded)

        assertThat(result).isInstanceOf(EdECPrivateKey::class.java)
    }

    @Test
    fun shouldDecodeABase64PublicKey() {
        val encoded = io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.public.encoded)

        val result = io.solana.boot.keys.KeyUtils.decodePublicKey(encoded)

        assertThat(result).isInstanceOf(EdECPublicKey::class.java)
    }

    @Test
    fun shouldEncodeKeyToBase64() {
        assertThat(io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.public.encoded)).isNotNull()
    }

    @Test
    fun shouldEncodeKeyToBase58() {
        assertThat(io.solana.boot.keys.KeyUtils.encodeToBase58(io.solana.boot.keys.KeyUtils.encodeToBase64(testKeyPair.public.encoded))).isNotNull()
    }

    @Test
    fun shouldSignATransaction() {
        //TODO
    }

}
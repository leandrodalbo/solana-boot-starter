package io.bootsolana.keys

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.security.KeyPairGenerator
import java.security.interfaces.EdECPrivateKey

class KeyUtilsTest {

    private val testKeyPair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()

    @Test
    fun shouldDecodeABase64PrivateKey() {
        val encoded = KeyUtils.encodeToBase64(testKeyPair.private.encoded)

        val result = KeyUtils.decodePrivateKey(encoded)

        assertThat(result).isInstanceOf(EdECPrivateKey::class.java)
    }

    @Test
    fun shouldEncodeKeyToBase64() {
        assertThat(KeyUtils.encodeToBase64(testKeyPair.public.encoded)).isNotNull()
    }

    @Test
    fun shouldEncodeKeyToBase58() {
        assertThat(KeyUtils.encodeToBase58(KeyUtils.encodeToBase64(testKeyPair.public.encoded))).isNotNull()
    }

    @Test
    fun shouldSignATransaction() {
        //TODO
    }

}
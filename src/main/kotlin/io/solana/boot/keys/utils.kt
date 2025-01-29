package io.solana.boot.keys

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Base64
import org.bitcoinj.core.Base58
import java.security.Signature


object KeyUtils {
    val ALGORITHM = "Ed25519"

    fun decodePrivateKey(base64Key: String): PrivateKey {
        val privateKeyBytes = Base64.getDecoder().decode(base64Key)
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        return keyFactory.generatePrivate(java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes))
    }

    fun decodePublicKey(base64Key: String): PublicKey {
        val publicKeyBytes = Base64.getDecoder().decode(base64Key)
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        val publicKeySpec = java.security.spec.X509EncodedKeySpec(publicKeyBytes)
        return keyFactory.generatePublic(publicKeySpec)
    }

    fun encodeToBase58(base64Key: String): String {
        val publicKeyBytes = Base64.getDecoder().decode(base64Key)
        return Base58.encode(publicKeyBytes)
    }

    fun encodeToBase64(bytes: ByteArray) = Base64.getEncoder().encodeToString(bytes)

    fun signTransaction(transactionMessage: ByteArray, key: PrivateKey): String {
        val signature = Signature.getInstance(ALGORITHM)
        signature.initSign(key)
        signature.update(transactionMessage)

        val signedData = signature.sign()
        return Base64.getEncoder().encodeToString(signedData)
    }

}
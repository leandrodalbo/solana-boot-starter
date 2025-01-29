package io.solana.boot.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("solana")
data class SolanaProperties(val endpoint: String)

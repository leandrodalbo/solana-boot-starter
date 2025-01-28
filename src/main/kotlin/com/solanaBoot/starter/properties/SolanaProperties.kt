package com.solanaBoot.starter.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("solana")
data class SolanaProperties(val endpoint: String)

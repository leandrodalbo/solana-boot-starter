package com.solanaBoot.starter.configuration

import com.solanaBoot.starter.properties.SolanaProperties
import com.solanaBoot.starter.solana.Solana
import org.p2p.solanaj.rpc.RpcClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "solana", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SolanaProperties::class)
class SolanaAutoConfiguration {

    @Bean
    fun solana(properties: SolanaProperties): Solana = Solana(RpcClient(properties.endpoint))
}
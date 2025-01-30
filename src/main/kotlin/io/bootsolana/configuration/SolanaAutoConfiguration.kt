package io.bootsolana.configuration

import io.bootsolana.properties.SolanaProperties
import io.bootsolana.solana.Solana
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

@Configuration
@ConditionalOnProperty(prefix = "solana", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SolanaProperties::class)
class SolanaAutoConfiguration {

    @Bean
    fun solana(builder: RestClient.Builder, properties: SolanaProperties): Solana =
        Solana(restClient(builder, properties))

    private fun restClient(builder: RestClient.Builder, properties: SolanaProperties): RestClient {
        return builder.baseUrl(properties.endpoint)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }
}
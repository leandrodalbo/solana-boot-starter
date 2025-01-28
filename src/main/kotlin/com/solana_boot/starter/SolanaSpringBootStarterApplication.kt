package com.solana_boot.starter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SolanaSpringBootStarterApplication

fun main(args: Array<String>) {
	runApplication<SolanaSpringBootStarterApplication>(*args)
}

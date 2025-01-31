# Solana Boot Starter

A **Spring Boot Starter** to interact with the Solana blockchain.
Simplifies blockchain development for Java/Kotlin developers by seamlessly integrating Solana into Spring Boot, reducing boilerplate and speeding up development.

## Features
- 🛠 **Simple Integration**: Easily connect to Solana RPC nodes.
- 📡 **RPC Client Support**: Access Solana's JSON-RPC endpoints with Solanaj.
- 🔑 **Transaction Support**: Send transfers, interact with accounts, and execute on-chain transactions.
- 🚀 **Spring Boot Auto-Configuration**: Automatically set up a Solana bean for blockchain interaction.

## Solanaj
- https://github.com/skynetcap/solanaj

## Getting Started

### Prerequisites
- A Solana RPC endpoint (e.g., https://api.mainnet-beta.solana.com or a devnet endpoint).

### Installation
Add the starter to your Spring Boot project's dependencies:

#### Gradle (Kotlin DSL)
```kotlin
dependencies {
    implementation("io.bootsolana:solana-boot-starter:1.0.0")
}
```

#### Maven
```xml
<dependency>
    <groupId>io.bootsolana</groupId>
    <artifactId>solana-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>

```

## Properties Configuration
#### Add the following properties to your application.yml or application.properties
```yml
solana:
  enabled: true
  endpoint: "https://api.devnet.solana.com" # Replace with your preferred RPC endpoint
```




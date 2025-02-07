plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("maven-publish")
    id("signing")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.bitcoinj:bitcoinj-core:0.15.10")
    implementation("com.mmorrell:solanaj:1.19.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.13.7")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    enabled = true
    archiveBaseName.set("solana-boot-starter")
    archiveClassifier.set("")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

publishing {
    repositories {

        maven {
            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_CENTRAL_USERNAME") ?: ""
                password = System.getenv("MAVEN_CENTRAL_PASSWORD") ?: ""
            }
        }


        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bootsolana/solana-boot-starter")
            credentials {
                username = System.getenv("BOOTSOLANA_USER") ?: ""
                password = System.getenv("BOOTSOLANA_TOKEN") ?: ""
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "io.bootsolana"
            artifactId = "solana-boot-starter"
            version = "1.0.0"

            pom {
                name.set("Solana Boot Starter")
                description.set("Integrate Spring Boot with Solana")
                inceptionYear.set("2025")
                url.set("https://bootsolana.io")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("bootsolana")
                        name.set("Boot Solana")
                        url.set("https://github.com/bootsolana")
                    }
                }
                scm {
                    url.set("https://github.com/bootsolana/solana-boot-starter")
                    connection.set("scm:git:git://github.com:bootsolana/solana-boot-starter.git")
                    developerConnection.set("scm:git:ssh://git@github.com:bootsolana/solana-boot-starter.git")
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSPHRASE")
    )
    sign(publishing.publications["mavenJava"])
}



//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            from(components["java"])
//            groupId = "io.bootsolana"
//            artifactId = "solana-boot-starter"
//            version = "1.0.0"
//        }
//    }
//    repositories {
//        maven {
//            mavenLocal()
//        }
//    }
//}
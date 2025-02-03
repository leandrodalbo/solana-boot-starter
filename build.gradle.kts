plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.vanniktech.maven.publish") version "0.30.0"
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

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL, true)
    if (!project.gradle.startParameter.taskNames.any { it.contains("publishToMavenLocal") }) {
        signAllPublications()
    }

    coordinates("io.bootsolana", "solana-boot-starter",  "1.0.0")

    pom {
        name = "Solana Boot Starter"
        description = "Integrate Spring Boot with Solana"
        inceptionYear = "2025"
        url = "https://bootsolana.io"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                id = "bootsolana"
                name = "bootsolana"
                url = "https://github.com/bootsolana"
            }
        }
        scm {
            url = "https://github.com/bootsolana/solana-boot-starter"
            connection = "scm:git:git://github.com:bootsolana/solana-boot-starter.git"
            developerConnection = "scm:git:ssh://git@github.com:bootsolana/solana-boot-starter.git"
        }
    }
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
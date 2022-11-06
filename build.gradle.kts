plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "logalytics"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
//    testImplementation 'org.springframework.boot:spring-boot-starter-test'
//    testImplementation 'io.projectreactor:reactor-test'
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("logalytics.Main")
}

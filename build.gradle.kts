plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.project"
version = "app"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.opencsv:opencsv:5.7.1")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.project.ApplicationStarter"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

jacoco {
    toolVersion = "0.8.8"
}

// Генерация отчета
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }

}

// Проверка покрытия кода
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.75".toBigDecimal() // Минимальное покрытие 75%
            }
        }
    }
}
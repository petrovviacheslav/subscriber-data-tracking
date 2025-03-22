plugins {
    java
    jacoco
    war
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.project"
version = "app"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:3.0.0")
    implementation("junit:junit:4.13.1")
    implementation("com.opencsv:opencsv:5.7.1")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")

    annotationProcessor("org.projectlombok:lombok")
//    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

jacoco {
    toolVersion = "0.8.7"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

//tasks.jacocoTestReport {
//    reports {
//        xml. = true
//        html.isEnabled = true
//        csv.isEnabled = false
//    }
//}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.75".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

tasks.register("testCoverage") {
    group = "verification"
    description = "Runs the unit tests with coverage."
    dependsOn(tasks.test, tasks.jacocoTestReport, tasks.jacocoTestCoverageVerification)

//    tasks.jacocoTestReport?.mustRunAfter(tasks.test)
//    tasks.jacocoTestCoverageVerification?.mustRunAfter(tasks.jacocoTestReport)
}
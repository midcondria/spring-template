plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.commerce"
version = "0.0.1-SNAPSHOT"
val queryDslVersion = "5.0.0" // QueryDSL Version Setting

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven(url="https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// actuator
	implementation ("org.springframework.boot:spring-boot-starter-actuator")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// AWS SDK
	implementation(platform("software.amazon.awssdk:bom:2.25.35"))
	implementation("software.amazon.awssdk:s3")

	// 스프링 시큐리티
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.session:spring-session-jdbc")

	// 소셜 로그인
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	// HTTP 요청
	implementation ("org.springframework.boot:spring-boot-starter-webflux")

	// 아임포트
	implementation("com.github.iamport:iamport-rest-client-java:0.2.23")

	// 웹소켓
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.1")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.1")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.1")
	implementation("org.springframework.security:spring-security-crypto")
	implementation("org.bouncycastle:bcprov-jdk15on:1.70")

	// QueryDSL
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
	implementation ("com.querydsl:querydsl-core")
	implementation ("com.querydsl:querydsl-collections")
	annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val querydslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile

sourceSets.getByName("main") {
	java.srcDir(querydslDir)
}

tasks.withType<JavaCompile> {
	options.generatedSourceOutputDirectory.set(file(querydslDir))
}

// clean 이후에 querydsl 폴더를 지움
tasks.named("clean") {
	doLast {
		file(querydslDir).delete()
	}
}

tasks.jar{
	enabled = false
}

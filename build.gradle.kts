plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    // plugin to generate open api classes from yaml
    id("org.openapi.generator") version "7.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        java {
            srcDir("build/generated/src/main/java")
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

// configure open api generator
openApiGenerate {
    val buildDir = layout.buildDirectory.get()
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/books.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.example.camelpocagain.spec.api")
    modelPackage.set("com.example.camelpocagain.spec.model")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("delegatePattern", "true")
    configOptions.put("annotationLibrary", "swagger2")
    configOptions.put("useTags", "true")
    configOptions.put("useBeanValidation", "true")
    configOptions.put("useOptional", "true")
    configOptions.put("useJava8", "true")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("java8", "true")
    // generate spring boot 3 code
    configOptions.put("useSpringBoot3", "true")
}

dependencies {
    implementation(platform("org.apache.camel.springboot:camel-spring-boot-bom:4.1.0"))
    implementation(platform("org.apache.camel:camel-bom:4.1.0"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.camel.springboot:camel-spring-boot-starter")
//    implementation("org.apache.camel.springboot:camel-spring-http-starter")
    implementation("org.apache.camel:camel-http")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

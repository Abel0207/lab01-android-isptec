// build.gradle.kts do exemplo2 - Projecto Java com Gradle Java Plugin
// O plugin "java" adiciona automaticamente tarefas para compilar, testar e empacotar código Java

plugins {
    java                    // Plugin Java: adiciona tasks compile, test, jar, build, etc.
    application             // Plugin Application: adiciona task "run" para executar a app
}

// Classe principal da aplicação (necessário para o plugin application)
application {
    mainClass = "ao.co.isptec.aplm.quota.Quota"
}

// Repositório onde o Gradle vai buscar dependências externas
repositories {
    mavenCentral()          // Maven Central é o repositório público mais popular
}

// Dependências do projecto
dependencies {
    // Dependência de teste - JUnit para escrever testes unitários
    testImplementation("junit:junit:4.13.2")
}

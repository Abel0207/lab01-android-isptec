// build.gradle.kts raiz do exemplo3
// Configurações comuns aplicadas a TODOS os subprojectos
subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }
}

// build.gradle.kts do subprojecto app
plugins {
    application
}

application {
    mainClass = "ao.co.isptec.aplm.app.Main"
}

// Depende de lib E de api (porque usa SaudacaoService directamente)
dependencies {
    implementation(project(":lib"))
    implementation(project(":api"))
}

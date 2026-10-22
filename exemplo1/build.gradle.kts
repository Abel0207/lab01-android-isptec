// build.gradle.kts do exemplo1 (Gradle 9 + Java 25)

// Definição de uma tarefa simples chamada "hello"
tasks.register("hello") {
    doLast {
        println("Olá, Gradle!")
    }
}

// Tarefa com dependência noutra tarefa
tasks.register("saudacao") {
    dependsOn("hello")
    doLast {
        println("Esta é uma tarefa que depende da tarefa hello.")
    }
}

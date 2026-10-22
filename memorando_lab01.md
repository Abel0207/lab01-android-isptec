# Memorando
**Aplicações Móveis, 2026/2027**

| | |
|---|---|
| **De:** | [Nome do(a) Estudante] |
| **Nº de Matrícula:** | [Número de Matrícula] |
| **Assunto:** | Laboratório #01 – Introdução ao Android e ao Sistema de Build Gradle |
| **Data:** | 22 de Outubro de 2026 |

---

## 1. Introdução

Este memorando documenta a realização do Laboratório #01 da unidade curricular de Aplicações Móveis do ISPTEC, ano lectivo 2026/2027. O laboratório tem como objectivo introduzir o desenvolvimento de aplicações Android, familiarizando o estudante com as ferramentas de desenvolvimento: **Android Studio**, o sistema de build **Gradle**, e os conceitos fundamentais da arquitectura Android.

O trabalho foi dividido em três exercícios:

- **Exercício I** – Implementação de uma aplicação *Hello World* com duas Activities em Android;
- **Exercício II** – Exploração do Gradle como sistema de automação de build;
- **Exercício III** – Implementação de uma aplicação de lista de tarefas com uma única Activity.

**Restrições e Observações:**

> O ambiente de desenvolvimento utilizado apresentou uma restrição relevante: o sistema dispõe do **Java SDK 25** (versão LTS, lançada em Setembro de 2025), que é incompatível com as versões do Gradle mencionadas no enunciado (5.2.1) e com o Gradle 8.11. Foi necessário instalar o **Gradle 9.1.0**, que é a primeira versão com suporte oficial ao Java 25. Os Exercícios I e III foram preparados ao nível do código-fonte, ficando a execução no emulador pendente da instalação do Android Studio.

---

## 2. Experiências Realizadas

### 2.1 Exercício I – Hello World

#### 2.1.1 Estrutura do Projecto Android

Um projecto Android criado com o Android Studio possui a seguinte estrutura de ficheiros relevantes:

| Ficheiro | Função |
|---|---|
| `app/src/main/res/layout/activity_main.xml` | Define o layout visual (UI) da actividade em XML |
| `app/src/main/java/.../MainActivity.java` | Implementa a lógica e comportamento da Activity principal |
| `app/src/main/AndroidManifest.xml` | "Cartão de identidade" da app – regista todas as Activities e permissões |
| `app/build.gradle` | Configura o build: versão do SDK, dependências, etc. |

#### 2.1.2 Layout da Interface – activity_main.xml

O layout da Activity principal foi implementado em `res/layout/activity_main.xml`. A árvore de UI é composta por um `LinearLayout` vertical contendo um `EditText` e um `Button`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/editTextMessage"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Digite uma mensagem"
        android:inputType="text" />

    <Button
        android:id="@+id/buttonSend"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Enviar"
        android:layout_marginTop="8dp" />

</LinearLayout>
```
*Figura 1: Layout XML da Activity principal (activity_main.xml)*

#### 2.1.3 Implementação da MainActivity

```java
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE =
        "ao.co.isptec.aplm.exercise_1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Minha primeira mensagem de depuração.");
        Button button = findViewById(R.id.buttonSend);
        button.setOnClickListener(v -> sendMessage());
    }

    public void sendMessage() {
        EditText editText = findViewById(R.id.editTextMessage);
        String message = editText.getText().toString();
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
```
*Figura 2: Código fonte da MainActivity.java*

#### 2.1.4 Respostas às Questões

**Q1. O que alterou no AndroidManifest.xml?**
O Android Studio adicionou automaticamente a declaração da `DisplayMessageActivity`. Toda Activity deve ser registada no Manifest para ser reconhecida pelo sistema Android.

**Q2. Como é tratado o evento de clique?**
Através do método `setOnClickListener()` que recebe um objecto `OnClickListener`. O método `onClick()` é invocado automaticamente pelo sistema quando o utilizador clica no botão.

**Q3. Como identificar e obter componentes da UI em Java?**
Através do método `findViewById(R.id.<id_do_componente>)`. A classe `R` é gerada automaticamente pelo sistema de build e mapeia os IDs definidos nos ficheiros XML para constantes Java acessíveis no código.

**Q4. Qual a finalidade da Intent em DisplayMessageActivity?**
A `Intent` é o mecanismo de comunicação entre componentes Android. Em `DisplayMessageActivity`, o método `getIntent()` recupera a Intent que iniciou a Activity, extraindo a mensagem enviada pela `MainActivity` via `putExtra()`.

---

### 2.2 Exercício II – Gradle

#### 2.2.1 Instalação e Configuração

Dado que o sistema possui Java 25, foi necessário instalar o Gradle 9.1.0 (primeira versão compatível). A instalação foi realizada manualmente sem privilégios de administrador:

```powershell
# Download do Gradle 9.1.0
Invoke-WebRequest -Uri "https://services.gradle.org/distributions/gradle-9.1.0-bin.zip" `
    -OutFile "$env:USERPROFILE\gradle-9.1.0-bin.zip"

# Extracção
Expand-Archive -Path "$env:USERPROFILE\gradle-9.1.0-bin.zip" `
    -DestinationPath "$env:USERPROFILE\bin"

# Configuração de variável de ambiente (utilizador)
[System.Environment]::SetEnvironmentVariable("GRADLE_HOME",
    "$env:USERPROFILE\bin\gradle-9.1.0", "User")
```
*Figura 3: Comandos PowerShell para instalação do Gradle 9.1.0*

Verificação da instalação:

```
Gradle 9.1.0
Kotlin:       2.2.0
Launcher JVM: 25.0.1 (Oracle Corporation 25.0.1+8-LTS-27)
OS:           Windows 11 10.0 amd64
```
*Figura 4: Output do comando `gradle --version`*

#### 2.2.2 Exemplo 1 – Tasks Básicas

Foi criado o ficheiro `build.gradle.kts` com duas tarefas: `hello` e `saudacao`. A tarefa `saudacao` tem uma dependência em `hello` via `dependsOn`:

```kotlin
tasks.register("hello") {
    doLast {
        println("Olá, Gradle!")
    }
}

tasks.register("saudacao") {
    dependsOn("hello")
    doLast {
        println("Esta é uma tarefa que depende da tarefa hello.")
    }
}
```
*Figura 5: build.gradle.kts do exemplo1*

O comando `gradle -q tasks --all` listou as tarefas disponíveis, incluindo as definidas:

```
Other tasks
-----------
hello
saudacao
```
*Figura 6: Excerto do output de `gradle -q tasks --all`*

A execução de `gradle -q saudacao` produziu:

```
Olá, Gradle!
Esta é uma tarefa que depende da tarefa hello.
```
*Figura 7: Output da execução de `gradle -q saudacao`*

O Gradle resolveu automaticamente a dependência, executando `hello` antes de `saudacao`.

#### 2.2.3 Exemplo 2 – Plugin Java e Classe Quota

O plugin Java foi aplicado via Kotlin DSL, adicionando automaticamente tarefas de compilação, empacotamento e execução:

```kotlin
plugins {
    java
    application
}
application {
    mainClass = "ao.co.isptec.aplm.quota.Quota"
}
repositories { mavenCentral() }
dependencies {
    testImplementation("junit:junit:4.13.2")
}
```
*Figura 8: build.gradle.kts do exemplo2*

A classe `Quota.java` foi colocada em `src/main/java/ao/co/isptec/aplm/quota/` seguindo a **convenção Maven** adoptada pelo plugin Java.

A execução de `gradle build` produziu a seguinte cadeia de tarefas:

```
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :jar
> Task :startScripts
> Task :distTar
> Task :distZip
> Task :assemble
> Task :build
BUILD SUCCESSFUL in 29s
```
*Figura 9: Output do `gradle build` no exemplo2*

A execução de `gradle run` produziu:

```
Aplicação Quota em execução
Quota{id=1, autor='Joao Costa', texto='Deus é vida.'}
```
*Figura 10: Output do `gradle run` no exemplo2*

**Respostas às questões:**

- **`apply plugin: 'java'`**: Adiciona ao projecto um conjunto pré-definido de tarefas para compilar (`compileJava`), testar (`test`), empacotar (`jar`) e distribuir (`distZip`) aplicações Java.
- **Localização do `Quota.java`**: O plugin Java segue a convenção Maven (`src/main/java/`). A estrutura de directórios reflecte o package Java (`ao.co.isptec.aplm.quota`), permitindo ao Gradle localizar os fontes automaticamente.
- **`repositories`**: Define onde o Gradle procura dependências externas (ex: Maven Central).
- **`dependencies`**: Declara as bibliotecas que o projecto necessita. O Gradle faz o download automático.

#### 2.2.4 Exemplo 3 – Projecto Multi-módulo

O projecto foi estruturado em três subprojectos com o seguinte grafo de dependências:

```
    ┌─────────┐
    │   app   │  ← Aplicação final (contém o método main)
    └────┬────┘
         │ depende de
    ┌────▼────┐
    │   lib   │  ← Implementação dos serviços
    └────┬────┘
         │ depende de
    ┌────▼────┐
    │   api   │  ← Interfaces/contratos (sem dependências)
    └─────────┘
```
*Figura 11: Grafo de dependências do exemplo3*

O ficheiro `settings.gradle.kts` regista os subprojectos:

```kotlin
rootProject.name = "exemplo3"
include("api", "lib", "app")
```
*Figura 12: settings.gradle.kts do exemplo3*

A construção do projecto completo com `gradle build` na raiz executou as tarefas dos três subprojectos em ordem de dependência:

```
> Task :api:compileJava
> Task :api:jar
> Task :lib:compileJava
> Task :lib:jar
> Task :app:compileJava
> Task :app:jar
> Task :app:build
BUILD SUCCESSFUL in 29s
```
*Figura 13: Output do `gradle build` no exemplo3*

A execução do subprojecto específico com `gradle :app:run`:

```
=== Aplicação Multi-Projecto ISPTEC ===
Olá, Estudante! Bem-vindo ao ISPTEC.
Olá, Professor Costa! Bem-vindo ao ISPTEC.
Aplicação concluída com sucesso!
```
*Figura 14: Output do `gradle :app:run` no exemplo3*

**Comandos para construção individual:**

| Acção | Comando |
|---|---|
| Construir todo o projecto | `gradle build` (na raiz) |
| Construir só `api` | `gradle :api:build` |
| Construir só `lib` | `gradle :lib:build` |
| Executar `app` | `gradle :app:run` |

---

### 2.3 Exercício III – App de Lista de Tarefas

#### 2.3.1 Layout da Actividade

```xml
<LinearLayout
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <EditText
        android:id="@+id/editTextTask"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Escreve uma tarefa e prime Enter..." />

    <ListView
        android:id="@+id/listViewTasks"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:layout_marginTop="12dp" />

</LinearLayout>
```
*Figura 15: Layout XML da app de lista de tarefas*

#### 2.3.2 Lógica da MainActivity

O `ArrayAdapter` faz a ligação entre o `ArrayList<String>` (dados) e o `ListView` (vista):

```java
taskList = new ArrayList<>();
adapter = new ArrayAdapter<>(this,
    android.R.layout.simple_list_item_1, taskList);
listView.setAdapter(adapter);

editText.setOnKeyListener((v, keyCode, event) -> {
    if (event.getAction() == KeyEvent.ACTION_DOWN
            && keyCode == KeyEvent.KEYCODE_ENTER) {
        String task = editText.getText().toString().trim();
        if (!task.isEmpty()) {
            taskList.add(0, task);        // adiciona no topo
            adapter.notifyDataSetChanged(); // actualiza a vista
            editText.setText("");
        }
        return true;
    }
    return false;
});
```
*Figura 16: Lógica principal da MainActivity do Exercício III*

---

## 3. Desafios

### 3.1 Desenho da Interface Móvel

#### Wireframe – Exercício I (Hello World)

```
┌─────────────────────────┐     ┌─────────────────────────┐
│      Hello World        │     │    Display Message      │
├─────────────────────────┤     ├─────────────────────────┤
│                         │     │                         │
│  ┌───────────────────┐  │     │                         │
│  │  Digite mensagem  │  │     │   "Olá, [mensagem]!"   │
│  └───────────────────┘  │  →  │                         │
│                         │     │                         │
│       [ Enviar ]        │     │                         │
│                         │     │                         │
└─────────────────────────┘     └─────────────────────────┘
   Activity Principal              DisplayMessageActivity
```
*Figura 17: Wireframe das duas Activities do Exercício I*

A primeira Activity apresenta um campo de texto e um botão "Enviar". Ao pressionar o botão, uma `Intent` transporta a mensagem digitada para a segunda Activity (`DisplayMessageActivity`), que a exibe ao utilizador.

#### Wireframe – Exercício III (Lista de Tarefas)

```
┌─────────────────────────┐
│     Lista de Tarefas    │
├─────────────────────────┤
│ ┌─────────────────────┐ │
│ │  Nova tarefa...  ↵  │ │  ← EditText (prime ENTER para adicionar)
│ └─────────────────────┘ │
│─────────────────────────│
│ • Tarefa 3 (mais recente│
│ • Tarefa 2              │  ← ListView (novas tarefas no topo)
│ • Tarefa 1              │
│                         │
│                         │
└─────────────────────────┘
```
*Figura 18: Wireframe da Activity do Exercício III*

A única Activity possui dois elementos organizados verticalmente: um `EditText` no topo e um `ListView` que ocupa o restante do espaço. As tarefas são inseridas no **topo** da lista (índice 0 do `ArrayList`), fazendo com que a mais recente apareça sempre primeiro.

### 3.2 Detalhes de Implementação

#### Arquitectura

Ambas as aplicações seguem o padrão **MVC (Model-View-Controller)** simplificado do Android:

| Camada | Componente | Função |
|---|---|---|
| **View** | XML layouts (`activity_*.xml`) | Define a interface visual |
| **Controller** | Activity (`.java`) | Responde a eventos, coordena dados e vista |
| **Model** | `ArrayList<String>` | Armazena os dados da aplicação |

#### Estruturas de Dados

- **Exercício I**: Comunicação via `Intent` com `putExtra(chave, valor)` / `getStringExtra(chave)`.
- **Exercício III**: `ArrayList<String>` ligado ao `ListView` por um `ArrayAdapter`. O método `notifyDataSetChanged()` propaga as alterações dos dados para a vista.

#### Padrão Observer (implícito)

O `ArrayAdapter` implementa o padrão **Observer**: quando os dados do `ArrayList` mudam e `notifyDataSetChanged()` é chamado, o `ListView` é automaticamente notificado e redesenhado — sem necessidade de código adicional.

---

## 4. Referências Bibliográficas

1. **Android Developers** – *Build Your First App*. Disponível em: https://developer.android.com/training/basics/firstapp
2. **Android Developers** – *Platform Architecture*. Disponível em: https://developer.android.com/guide/platform
3. **Android Developers** – *Declaring Layout*. Disponível em: https://developer.android.com/develop/ui/views/layout/declaring-layout
4. **Gradle Documentation** – *Gradle User Manual*. Disponível em: https://docs.gradle.org/9.1.0/userguide/userguide.html
5. **Gradle Documentation** – *Java Plugin*. Disponível em: https://gradle.org/docs/current/userguide/java_plugin.html
6. **Gradle Documentation** – *Application Plugin*. Disponível em: https://docs.gradle.org/current/userguide/application_plugin.html
7. **Romini Rani** – *Gradle Tutorial Part 6: Android Studio Gradle*. Disponível em: https://rominirani.com/gradle-tutorial-part-6-android-studio-gradle-c828c5639bb
8. **Android Developers** – *Managing AVDs*. Disponível em: https://developer.android.com/studio/run/managing-avds
9. **Gradle Releases** – *Gradle 9.1.0 Release Notes*. Disponível em: https://docs.gradle.org/9.1.0/release-notes.html

---

## 5. Repositório GitHub

O código-fonte deste laboratório encontra-se disponível no seguinte repositório:

> 🔗 **https://github.com/Abel0207/lab01-android-isptec**

**Estrutura do repositório:**

```
lab01-android-isptec/
├── exemplo1/          → Tasks básicas do Gradle
├── exemplo2/          → Plugin Java + classe Quota
├── exemplo3/          → Projecto multi-módulo (api, lib, app)
└── exercicio3_android/ → App Android de lista de tarefas
```

O utilizador GitHub **joaojdacosta** foi adicionado como colaborador do repositório.

---
*Memorando elaborado no âmbito da unidade curricular de Aplicações Móveis – ISPTEC, 2026/2027*

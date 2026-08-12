# Atividade de integração com o Banco de Dados do Firebase (Real Time Database) da disciplina de PDM (Programação para dispositivos móveis)

##Especificações do projeto: 
Ele é um sistema de fichário de livros, no qual o usuário pode criar itens contendo o nome do livro, do autor e escrever uma breve sinopse, além de marcar o livro como "Não lido", "Lido" ou "Lendo agora". Apresenta 3 telas e implementação do CRUD completo e vinculação com o banco de dados do Firebase.

O projeto apresentava diversos erros de compilação relacionados a incompatibilidades entre as versões do Gradle, do Android Gradle Plugin (AGP) e das bibliotecas AndroidX.

## Problemas Corrigidos

### 1. Suporte a AndroidX
*   **Problema**: O projeto usava bibliotecas AndroidX, mas a propriedade `android.useAndroidX` não estava habilitada em `gradle.properties`.
*   **Solução**: Adicionadas as linhas `android.useAndroidX=true` e `android.enableJetifier=true`.

### 2. Incompatibilidade de Versões (SDK e AGP)
*   **Problema**: Algumas bibliotecas (como `core-ktx:1.19.0`) exigiam o `compileSdk 37`, mas o projeto estava no 35. Além disso, o AGP `8.2.2` era antigo demais para a versão do Gradle `9.5.0` e para as bibliotecas mais recentes.
*   **Solução**:
    *   Atualizado o Android Gradle Plugin (AGP) de `8.2.2` para `8.7.3` em `libs.versions.toml`.
    *   Ajustadas as versões das bibliotecas AndroidX para versões estáveis compatíveis com o SDK 35 (`core-ktx:1.13.1`, `appcompat:1.6.1`, `activity-ktx:1.9.3`).

### 3. Conflito de Alvo da JVM
*   **Problema**: O compilador Java estava usando o alvo 11, mas o Kotlin estava tentando usar o 21, causando erro de inconsistência.
*   **Solução**: Configurado explicitamente o `jvmTarget = "11"` para o Kotlin no arquivo `app/build.gradle.kts`.

### 4. Cache de Configuração
*   **Problema**: A versão do Kotlin utilizada apresentava erros de compatibilidade com o Cache de Configuração do Gradle 9.5.0.
*   **Solução**: Desabilitado temporariamente o `org.gradle.configuration-cache` em `gradle.properties` para garantir estabilidade.

## Estado Atual
*   **Build**: O projeto agora compila com sucesso (`Build finished successfully`).
*   **Funcionalidade**: O CRUD com Firebase continua operacional

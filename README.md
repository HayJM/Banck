

# Banck

## Sobre o Projeto

O Banck é um sistema simples de gerenciamento bancário desenvolvido em Java, com foco em operações de contas, carteiras e investimentos. O objetivo é proporcionar uma base para estudos de orientação a objetos, tratamento de exceções e organização de projetos Java utilizando Gradle.

Principais funcionalidades:
- Cadastro e gerenciamento de contas bancárias
- Operações de depósito, saque e transferência
- Gerenciamento de carteiras (Wallets) e investimentos
- Tratamento de exceções customizadas para regras de negócio

---

> Projeto Java para gerenciamento de contas, carteiras e investimentos.

## Estrutura do Projeto

- `src/main/java/`: código-fonte principal
- `src/main/java/br/com/dio/exception/`: exceções customizadas
- `src/main/java/br/com/dio/model/`: modelos de domínio (AccountWallet, BanckService, etc.)
- `src/main/java/br/com/dio/repository/`: repositórios
- `bin/`: arquivos compilados
- `lib/`: dependências externas
- `build.gradle` e `gradle.properties`: configuração do Gradle

## Como compilar e executar

1. Certifique-se de ter o Java (JDK 17+) e o Gradle instalados.
2. Para compilar o projeto:
	```bash
	./gradlew build
	```
3. Para executar a aplicação principal:
	```bash
	./gradlew run
	```
	Ou, diretamente pela linha de comando:
	```bash
	java -cp bin Main
	```

## Observações

- O projeto utiliza Gradle para gerenciamento de dependências e build.
- O arquivo `.gitignore` já está configurado para ignorar arquivos de build, IDEs e temporários.

---
Desenvolvido para fins didáticos.

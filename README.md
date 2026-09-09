# FATEC — Programação Orientada a Objetos (Exercícios Práticos)

Repositório dedicado aos **exercícios práticos e suítes de testes automatizados** do curso de Programação Orientada a Objetos (Java) da FATEC.

Aqui você colocará a mão na massa para fixar os conceitos discutidos no material teórico através de problemas reais de design, refatoração, caça a bugs e modelagem orientada a objetos.

---

## 🎯 Filosofia e Metodologia

Cada exercício deste repositório foi planejado para fornecer **feedback imediato** através de testes de unidade automatizados (JUnit 5 + AssertJ).

Você saberá exatamente quando sua solução estiver correta: **quando todos os testes ficarem verdes!**

### Estrutura Padrão de Cada Exercício

Cada exercício vive em sua própria pasta e segue rigorosamente o seguinte formato:

```text
exemplo-exercicio/
├── README.md               # Enunciado detalhado, regras de negócio e critérios de aceite
├── pom.xml                 # Configuração do módulo Maven do exercício
├── base/                   # Cópia limpa do exercício inicial (para reiniciar do zero, se desejar)
├── src/                    # Espaço de trabalho do aluno
│   ├── main/java/...       # Código de produção a ser implementado/corrigido
│   └── test/java/...       # Suíte de testes automatizados (JUnit 5 + AssertJ)
└── solucao-proposta/       # Gabarito de referência com explicação dos trade-offs técnicos
```

---

## 🚀 Como Executar os Exercícios

### Pré-requisitos
- **JDK 21 LTS** ou superior (o curso utiliza e recomenda Java 21 ou 25).
- Git instalado.

> **Dica:** Você **não precisa** ter o Maven instalado globalmente na sua máquina! Este repositório já inclui o Maven Wrapper (`mvnw`).

### 1. Executando os Testes pelo Terminal

Para rodar todos os testes de todos os exercícios:
```bash
# Linux / macOS
./mvnw test

# Windows (Prompt de Comando ou PowerShell)
mvnw.cmd test
```

Para rodar os testes de um módulo ou exercício específico:
```bash
# Rodar apenas um exercício específico a partir da raiz:
./mvnw test -pl :nome-do-modulo-do-exercicio

# Ou entre na pasta do exercício e execute:
cd 02-oo/ex01-conta-bancaria
../../mvnw test
```

### 2. Executando na sua IDE Favorita

- **IntelliJ IDEA**: Abra a pasta raiz deste repositório (`File > Open...`). O IntelliJ detectará o `pom.xml` raiz e importará todos os submódulos automaticamente. Você pode clicar com o botão direito em qualquer classe de teste e selecionar **Run '...Test'**.
- **VS Code**: Abra a pasta raiz com a extensão **Extension Pack for Java** instalada. A aba *Testing* listará todos os testes dos exercícios.
- **Eclipse**: Use `File > Import... > Existing Maven Projects` e selecione o diretório raiz.

---

## 📚 Mapeamento dos Módulos

Os exercícios estão organizados em três módulos principais:

1. **`01-java-basico/`**: Exercícios integradores de lógica, casting, algoritmos clássicos, manipulação de texto e estruturas de dados fundamentais (`List`, `Set`, `Map`).
2. **`02-oo/`**: O coração do curso. Exercícios de encapsulamento, invariantes de domínio, composição, polimorfismo, interfaces, herança, refatoração de code smells e depuração de armadilhas clássicas de OO na JVM.
3. **`03-java-in-depth/`**: Exercícios avançados explorando Generics, `Optional`, Expressões Lambda, Method References e Pipelines de `Streams`.

---

## 💡 Dicas para Aproveitar ao Máximo

1. **Leia o `README.md` do exercício com atenção**: Entenda o problema e as invariantes exigidas antes de digitar código.
2. **Execute os testes antes de começar**: Veja os testes falharem (fase *Red* do TDD) para entender quais expectativas de negócio estão pendentes.
3. **Não modifique a suíte de testes**: O arquivo em `src/test/` é o contrato a ser cumprido. Altere apenas o código de produção em `src/main/` (exceto se o exercício for explicitamente sobre escrita de testes).
4. **Compare com a `solucao-proposta/` após concluir**: Veja como o professor resolveu, quais decisões de design foram tomadas e quais alternativas existiam.

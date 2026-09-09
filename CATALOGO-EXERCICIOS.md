# Catálogo e Rastreamento de Exercícios

Este documento serve como mapa de bordo e controle de ciclo de vida de todos os exercícios práticos do repositório.

---

## 🚦 Ciclo de Vida dos Exercícios

Cada exercício evolui através do seguinte fluxo de estados:

```
[ DRAFT ] ➔ [ BACKLOG ] ➔ [ EXECUTANDO ] ➔ [ REVISANDO ] ➔ [ PRONTO ]
```

- **`DRAFT`**: Proposta inicial de tema e objetivos conceituais ainda em definição/discussão.
- **`BACKLOG`**: Escopo, requisitos e tipologia aprovados; aguardando implementação dos arquivos.
- **`EXECUTANDO`**: Em desenvolvimento ativo (`README.md`, `pom.xml`, `base/`, `src/` com código e testes JUnit 5 / AssertJ, e `solucao-proposta/`).
- **`REVISANDO`**: Implementação concluída; em fase de validação da compilação, execução da suíte de testes (`mvn test`) e checagem didática.
- **`PRONTO`**: Exercício testado, validado, documentado e liberado para os alunos.

---

## 📊 Painel Geral de Progresso

| Módulo | Total de Exercícios | DRAFT | BACKLOG | EXECUTANDO | REVISANDO | PRONTO |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **01 — Java Básico** | 19 | 16 | 0 | 0 | 0 | 3 |
| **02 — Orientação a Objetos** | 16 | 16 | 0 | 0 | 0 | 0 |
| **03 — Java In-Depth** | 8 | 8 | 0 | 0 | 0 | 0 |
| **Total Geral** | **43** | **40** | **0** | **0** | **0** | **3** |

---

## 📦 Módulo 01 — Java Básico: Fundamentos & Coleções

| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `01-java-basico/ex01-conversor-temperatura` | Conversor Térmico | Service | `PRONTO` | Conversões Celsius, Fahrenheit e Kelvin. Precedência, divisão de ponto flutuante (`5.0/9.0` vs `5/9`) e validação de zero absoluto ($-273.15^\circ\text{C}$). |
| `01-java-basico/ex02-calculadora-consumo-viagem` | Calculadora de Consumo de Viagem | Service | `PRONTO` | Distância, autonomia e combustível. Casting explícito (`int` para `double`) e composição de relatório imutável com `record`. |
| `01-java-basico/ex03-pegadinha-tipos-referencia` | O Mistério do Aliasing de Referências | Bugfix | `PRONTO` | Diagnóstico de mutação indesejada por compartilhamento de referência em memória vs cópia de primitivos. |
| `01-java-basico/ex04-classificador-triangulos` | Classificador de Triângulos | Service | `DRAFT` | Desigualdade geométrica ($a < b + c$) e operadores lógicos (`&&`, `||`, `!`) para Equilátero, Isósceles ou Escaleno. |
| `01-java-basico/ex05-calculadora-tarifa-progressiva` | Tarifador Progressivo de Energia | Service | `DRAFT` | Fatura por faixas cumulativas de consumo (kWh) com `if` / `else if` sem condições redundantes. |
| `01-java-basico/ex06-gerador-tabuada-fatorial` | Gerador de Tabuada e Fatorial | Service | `DRAFT` | Laços `for` e `while`, contadores, acumuladores de produto e controle de overflow com `long`. |
| `01-java-basico/ex07-verificador-numero-primo` | Verificador de Número Primo | Algoritmo | `DRAFT` | Verificação com laço otimizado até $\sqrt{N}$ e tratamento de casos especiais ($n \le 1$, pares). |
| `01-java-basico/ex08-sequencia-fibonacci` | Sequência de Fibonacci | Algoritmo | `DRAFT` | Geração dos $N$ primeiros termos com variáveis de estado (`a`, `b`, `proximo`) em laço iterativo. |
| `01-java-basico/ex09-validador-senha-forte` | Validador de Senha Forte | Service | `DRAFT` | Métodos de `String` (`length`, `charAt`) e inspeção de caracteres com `Character.isUpperCase`, `Character.isDigit`. |
| `01-java-basico/ex10-analisador-palindromos` | Analisador de Palíndromos | Algoritmo | `DRAFT` | Verificação com dois ponteiros, sanitização de espaços e pontuação (`toLowerCase`, `Character.isLetterOrDigit`). |
| `01-java-basico/ex11-estatisticas-vetor` | Estatísticas de Vetor | Service | `DRAFT` | Arrays `double[]`: média, menor valor, maior valor e tratamento de arrays vazios ou nulos. |
| `01-java-basico/ex12-ordenacao-bubble-sort` | Ordenação com Bubble Sort | Algoritmo | `DRAFT` | Implementação manual do Bubble Sort em `int[]`, trocas (*swap*) e flag de interrupção precoce. |
| `01-java-basico/ex13-busca-linear-binaria` | Busca Linear vs Busca Binária | Algoritmo | `DRAFT` | Comparação prática entre busca sequencial e binária em vetor ordenado com contagem de comparações. |
| `01-java-basico/ex14-tabuleiro-jogo-velha` | Tabuleiro de Jogo da Velha | Service | `DRAFT` | Matrizes bidimensionais (`char[][]`), verificação de vitórias em linhas, colunas, diagonais e empate. |
| `01-java-basico/ex15-gerenciador-tarefas-lista` | Gerenciador de Tarefas com List | Service | `DRAFT` | Operações essenciais da interface `List` (`ArrayList`): adicionar, remover por índice vs valor, `contains`. |
| `01-java-basico/ex16-filtro-duplicados-conjunto` | Filtro de Itens Únicos com Set | Service | `DRAFT` | Eliminação natural de duplicatas com `HashSet`, união (`addAll`), interseção (`retainAll`) e diferença. |
| `01-java-basico/ex17-dicionario-frequencia-mapa` | Dicionário de Frequência de Palavras | Service | `DRAFT` | Mapeamento chave-valor com `HashMap`: contagem com `getOrDefault`, `put` e iteração por `Map.Entry`. |
| `01-java-basico/ex18-fila-atendimento-historico` | Fila de Atendimento com Histórico | Service | `DRAFT` | Fila FIFO (`Queue`) integrada a pilha LIFO (`Deque`) com suporte a "Desfazer última chamada". |
| `01-java-basico/ex19-sandbox-performance-colecoes` | Laboratório de Coleções | Sandbox | `DRAFT` | Experimentos práticos comparando `ArrayList` vs `LinkedList` e `HashSet` vs `TreeSet` com preenchimento de `RELATORIO.md`. |

---

## 🏛️ Módulo 02 — Orientação a Objetos (Carro-Chefe)

### Fase A: Estado, Construtores e Invariantes
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex01-conta-bancaria` | Conta Bancária com Limite | Design | `DRAFT` | Modelagem de saldo, limite de cheque especial e extrato; bloqueio de operações inválidas e validação de construtor. |
| `02-oo/ex02-relogio-ponto-periodo` | Período de Ponto Imutável | Design / Value Object | `DRAFT` | Modelagem de intervalo de tempo (`Periodo`); garantia de invariante (início anterior ao fim) e métodos gerando novas instâncias. |
| `02-oo/ex03-catalogo-produtos-dimensoes` | Produto e Cubagem | Design | `DRAFT` | Separação de conceitos coesos: criação do colaborador `Dimensoes` para cálculo de volume e frete em `Produto`. |

### Fase B: Encapsulamento Rígido & Defesa da Memória
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex04-turma-matriculas` | Turma e Matrículas de Alunos | Bugfix / Design | `DRAFT` | Correção de vulnerabilidade de encapsulamento com vazamento de referências mutáveis; adoção de *defensive copying* e listas não-modificáveis. |
| `02-oo/ex05-contrato-equals-hashcode` | O Mistério do HashSet Vazio | Bugfix / JVM | `DRAFT` | Identificação e correção de quebra do contrato `equals` e `hashCode` onde objetos cadastrados "desaparecem" de coleções hash. |

### Fase C: Composição sobre Herança
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex06-carrinho-estrategias-desconto` | Carrinho com Descontos Plugáveis | Design / Composição | `DRAFT` | Interface `RegraDesconto` acoplada ao carrinho via composição, permitindo combinar cupom percentual, desconto progressivo e frete grátis. |
| `02-oo/ex07-personagens-rpg-equipamentos` | Personagem de RPG e Armas | Design / Composição | `DRAFT` | Evitar herança combinatória (`GuerreiroComArco`): armas e habilidades equipadas dinamicamente via composição em tempo de execução. |

### Fase D: Polimorfismo & Contratos com Interfaces
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex08-gateway-pagamentos` | Checkout de Pagamentos Multimeios | Design / Polimorfismo | `DRAFT` | Checkout dependente da interface `MeioPagamento` (Pix, Cartão, Boleto), respeitando o Princípio Aberto/Fechado (OCP). |
| `02-oo/ex09-central-notificacoes` | Central Multicanal de Notificações | Design / Polimorfismo | `DRAFT` | Disparo de alertas em canais com contratos próprios (Email, SMS, Push, WhatsApp), com limites de caracteres e retentativas polimórficas. |
| `02-oo/ex10-folha-pagamento-polimorfica` | Folha de Pagamento Corporativa | Design / Polimorfismo | `DRAFT` | Cálculo de salários e encargos para CLT, PJ, Estágio e Comissionado eliminando qualquer condicional `if-else` ou `instanceof`. |

### Fase E: Herança Bem Aplicada & Classes Abstratas
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex11-pipeline-processamento-arquivos` | Processador em Lote (Template Method) | Design / Herança | `DRAFT` | Superclasse abstrata ditando o fluxo (abrir -> validar -> transformar -> salvar) e subclasses implementando os formatos específicos (CSV, JSON). |

### Fase F: Refatoração & Clean OO
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex12-refatoracao-monolito-procedural` | Da Monolito Procedural a Objetos | Refatoração | `DRAFT` | Classe utilitária estática de 300 linhas com *Primitive Obsession* e *Feature Envy*; o aluno refatora para modelo orientado a objetos mantendo a suíte de testes 100% verde. |
| `02-oo/ex13-eliminando-switch-smell` | Calculador de Frete com Switch Smell | Refatoração | `DRAFT` | Eliminação de cadeias de `switch (tipoVeiculo)` espalhadas por múltiplos métodos, convertendo para hierarquia polimórfica coesa. |

### Fase G: Sandboxes & Modern Java
| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `02-oo/ex14-identidade-vs-igualdade` | Identidade vs Igualdade na JVM | Sandbox / JVM | `DRAFT` | Experimentos com String Pool, Integer Cache (-128 a 127) e diferença entre ponteiro de memória (`==`) e igualdade lógica (`equals`). |
| `02-oo/ex15-imutabilidade-e-records` | Modernizando Entidades com Records | Design / Modern Java | `DRAFT` | Refatoração de classe legada com getters/setters e mutabilidade acidental para `record` com *compact constructor* e validação de invariantes. |
| `02-oo/ex16-comparable-comparator-ranking` | Ranking e Ordenação de Domínio | Design | `DRAFT` | Implementação de ordem natural com `Comparable<T>` e criação de critérios múltiplos encadeados com `Comparator<T>` (`thenComparing`). |

---

## ⚡ Módulo 03 — Java In-Depth: Generics & Funcional

| ID / Diretório | Tema | Tipo | Status | Resumo da Proposta |
| :--- | :--- | :--- | :---: | :--- |
| `03-java-in-depth/ex01-repositorio-generico` | Repositório em Memória Genérico | Generics | `DRAFT` | Interface `Repository<T, ID>` genérica com implementação em memória thread-safe básica e type safety em tempo de compilação. |
| `03-java-in-depth/ex02-pilha-fila-generica` | Estrutura de Dados Genérica | Generics | `DRAFT` | Pilha com redimensionamento dinâmico e tipagem genérica sem gerar *unchecked cast warnings*. |
| `03-java-in-depth/ex03-utilitarios-colecoes-pecs` | Utilitários com Bounded Wildcards | Generics (PECS) | `DRAFT` | Métodos para copiar, filtrar e mesclar coleções aplicando o princípio PECS (*Producer `extends`, Consumer `super`*). |
| `03-java-in-depth/ex04-buscador-seguro-optional` | Busca e Navegação sem NullPointer | Funcional / Optional | `DRAFT` | Refatoração de código com checagens profundas de `null` usando pipelines de `Optional` (`map`, `flatMap`, `filter`, `orElseThrow`). |
| `03-java-in-depth/ex05-validador-regras-predicados` | Motor de Validação com Predicados | Funcional / Interfaces | `DRAFT` | Composição dinâmica de regras de validação de formulários encadeando `Predicate<T>` com `.and()`, `.or()` e `.negate()`. |
| `03-java-in-depth/ex06-relatorio-vendas-streams` | Relatório de Vendas com Streams | Funcional / Streams | `DRAFT` | Pipeline de dados usando `filter`, `map`, `sorted`, `distinct` e operações terminais como `reduce` e `collect`. |
| `03-java-in-depth/ex07-estatisticas-agrupamento-avancado` | Agrupamento e Sumarização Avançada | Funcional / Collectors | `DRAFT` | Relatório corporativo com `Collectors.groupingBy`, `mapping`, `averagingDouble` e `partitioningBy`. |
| `03-java-in-depth/ex08-pipeline-texto-concorrente` | Processamento Paralelo de Textos | Funcional / Streams | `DRAFT` | Comparativo entre `stream()` e `parallelStream()` na análise de grande volume de dados, compreendendo concorrência e coletores concorrentes. |

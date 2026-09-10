# ex11 — Estatísticas de Vetor

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Manipulação de Vetores Primitivos & Agregação Estatística  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Vetores Primitivos (`double[]`), Varredura Linear em Passo Único ($O(N)$), Validação Defensiva de Entradas, Armadilhas de Inicialização (`Double.MIN_VALUE` vs `values[0]`), Composição de Resultados com Objeto Pré-Pronto  

---

## 1. Contexto & Cenário

Estações meteorológicas e sensores IoT coletam leituras contínuas de grandezas físicas como temperatura, pressão atmosférica e umidade relativa. Esses fluxos de dados são armazenados na memória em arrays primitivos unidimensionais (`double[]`).

Antes de encaminhar os dados para dashboards ou disparar alertas de contingência, o sistema precisa processar a série amostral e extrair métricas de resumo:
- **Quantidade de amostras ($N$):** contagem dos pontos válidos coletados.
- **Soma total:** somatório acumulado de todas as medições.
- **Média aritmética simples ($\mu$):** valor central típico da amostra.
- **Menor e Maior valor:** extremos térmicos ou de pressão registrados.
- **Amplitude:** a dispersão total entre o extremo máximo e o mínimo ($\text{max} - \text{min}$).
- **Itens acima da média:** quantidade de leituras que superam o valor médio do período.

Para agrupar essas métricas em um único retorno, o projeto já fornece pronta a estrutura `VectorStatistics`. Você deve focar exclusivamente na lógica algorítmica dentro da classe utilitária `VectorAnalyzer`.

---

## 2. Objetivos de Aprendizagem

- Manipular **vetores primitivos (`double[]`)** sem recorrer a bibliotecas prontas de agregação.
- Aplicar o padrão de **varredura em passo único ($O(N)$)**: acumular soma, localizar o menor valor e localizar o maior valor em um único laço `for`.
- Evitar a armadilha clássica da JVM: inicializar `min` com `0.0` ou com `Double.MIN_VALUE` (que em Java representa o menor número **positivo** subnormal $4.9 \times 10^{-324}$, e não um número negativo).
- Instanciar a estrutura de transporte pré-fornecida (`VectorStatistics`) para consolidar múltiplos resultados de retorno.
- Praticar validações defensivas: lançamento de `IllegalArgumentException` para entradas nulas ou vetores de tamanho zero.

---

## 3. Regras de Negócio & Contratos da API

### 3.1. Estrutura de Retorno Fornecida (`VectorStatistics`)

> ℹ️ **Estrutura Pré-Pronta:** O arquivo `VectorStatistics.java` já vem implementado e **não precisa ser modificado**. Ele funciona como um agregador de dados que reúne as 6 métricas apuradas:

```java
public record VectorStatistics(
    int count,
    double sum,
    double average,
    double min,
    double max,
    double amplitude
)
```

No método `calculateStatistics`, basta criar e retornar uma nova instância passando as variáveis calculadas:

```java
return new VectorStatistics(values.length, sum, average, min, max, amplitude);
```

---

### 3.2. Contratos da Classe Utilitária (`VectorAnalyzer`)

Todas as operações devem validar os parâmetros de entrada:
1. Se `values == null`: lançar `IllegalArgumentException("O vetor informado não pode ser nulo")`.
2. Se `values.length == 0`: lançar `IllegalArgumentException("O vetor não pode ser vazio para o cálculo estatístico")`.

| Método | Assinatura | Descrição da Regra |
| :--- | :--- | :--- |
| **`calculateSum`** | `double calculateSum(double[] values)` | Percorre o vetor acumulando o somatório de todos os elementos. |
| **`calculateAverage`** | `double calculateAverage(double[] values)` | Retorna a média aritmética simples (`sum / values.length`). |
| **`findMin`** | `double findMin(double[] values)` | Retorna o menor valor numérico presente no vetor. |
| **`findMax`** | `double findMax(double[] values)` | Retorna o maior valor numérico presente no vetor. |
| **`calculateStatistics`** | `VectorStatistics calculateStatistics(double[] values)` | Executa a agregação completa em **passo único linear $O(N)$** e retorna o `record`. |
| **`countAboveAverage`** | `int countAboveAverage(double[] values)` | Calcula a média e conta quantos elementos são **estritamente maiores** (`>`) que a média. |

---

### 3.3. Armadilha de Inicialização de Extremos (Atenção Técnica)

Muitos desenvolvedores iniciantes cometem o erro de inicializar a busca do menor valor com `0.0` ou `Double.MIN_VALUE`:

```java
// ❌ ERRADO: se todos os valores forem negativos (ex: [-10.0, -5.0]), min permanecerá 0.0!
double min = 0.0;

// ❌ ERRADO: em Java, Double.MIN_VALUE é 4.9E-324 (positivo)! Não é o menor double possível.
double min = Double.MIN_VALUE;
```

A estratégia correta e canônica consiste em inicializar os extremos com o **primeiro elemento válido do próprio vetor**:

```java
//  CORRETO: inicializa com o primeiro elemento garantido da coleção
double min = values[0];
double max = values[0];
```

---

## 4. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex11/VectorAnalyzer.java`.  
   *(O arquivo `VectorStatistics.java` já está pronto e não precisa de nenhuma modificação).*
2. Implemente os métodos de cálculo respeitando os contratos e validações defensivas:
   - `calculateSum(double[] values)`
   - `calculateAverage(double[] values)`
   - `findMin(double[] values)`
   - `findMax(double[] values)`
   - `calculateStatistics(double[] values)` — instanciando `new VectorStatistics(...)`
   - `countAboveAverage(double[] values)`
3. Execute a suíte de testes automatizados para verificar sua solução:
   ```bash
   ./mvnw test -pl :ex11-estatisticas-vetor
   ```

---

## 5. Dica de Reflexão / Desafio Opcional

> ### 💡 Passo Único ($O(N)$) vs Múltiplos Passos
>
> Para implementar `calculateStatistics`, é tentador chamar internamente `calculateSum(values)`, `findMin(values)` e `findMax(values)`.  
> Embora isso funcione e reutilize código, o vetor será percorrido **três vezes** na memória ($3N$ iterações).  
> 
> - **Desafio:** Implemente `calculateStatistics` percorrendo o vetor **uma única vez** (laço de passo único), atualizando cumulativamente a soma, o menor e o maior valor. Para conjuntos de dados com milhões de leituras de sensores, essa redução no acesso à memória melhora sensivelmente o uso do cache L1/L2 da CPU.
> - **Extensão:** Como calcular o **Desvio Padrão Populacional** ($\sigma = \sqrt{\frac{\sum (x_i - \mu)^2}{N}}$)? Veja a resolução em `solucao-proposta/README.md`.

---

## 6. Critérios de Aceite

1. Todos os testes unitários em `VectorAnalyzerTest.java` devem compilar e passar com sucesso (**100% verdes**).
2. O método `calculateStatistics` deve processar corretamente vetores unitários (tamanho 1), vetores homogêneos (todos elementos iguais) e vetores com números negativos.
3. As mensagens de erro para entradas inválidas em `VectorAnalyzer` devem respeitar estritamente as constantes:
   - `"O vetor informado não pode ser nulo"`
   - `"O vetor não pode ser vazio para o cálculo estatístico"`

---

## 8. Execução dos Testes

Para executar a suíte de testes deste módulo isoladamente pelo terminal, utilize:

```bash
./mvnw test -pl :ex11-estatisticas-vetor
```

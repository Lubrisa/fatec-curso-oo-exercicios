# Solução Proposta — ex01: Simulador Financeiro & Amortização

Este diretório contém a resolução de referência do professor acompanhada da análise dos trade-offs técnicos e pedagógicos envolvidos na solução.

## 1. Código da Solução

O arquivo resolvido está em:
`solucao-proposta/src/main/java/br/com/fatec/basic/ex01/FinancialCalculator.java`

## 2. Decisões de Design & Trade-offs Pedagógicos

### 2.1. Ponto Flutuante (`double`) vs. Alta Precisão (`BigDecimal`)

Em sistemas bancários de missão crítica em produção, a recomendação formal da indústria é utilizar `java.math.BigDecimal` para cálculos monetários, pois o padrão IEEE 754 de tipos `double` e `float` introduz dízimas binárias periódicas (ex: `0.1 + 0.2 = 0.30000000000000004`).

**Por que usamos `double` neste exercício introdutório?**
- O foco do Módulo 01 é fixar o uso de tipos primitivos da linguagem, métodos da classe `java.lang.Math` (`Math.pow`, `Math.round`), casting explícito e controle de laços fundamentais.
- O arredondamento centralizado em duas casas decimais com `Math.round(val * 100.0) / 100.0` e o uso de margem de tolerância em testes (`Offset.offset(0.01)`) ilustram aos alunos a natureza e os limites dos cálculos com primitivos na JVM.

### 2.2. Prevenção de Erros Acumulados no Último Mês

Em cronogramas de amortização (especialmente no Price), pequenos arredondamentos em cada parcela podem deixar centavos residuais no saldo devedor final (ex: `0.01` ou `-0.01`).
Na solução proposta:
- Para o último mês ($k = n$), o saldo devedor remanescente é expressamente forçado a `0.0`, e a amortização é ajustada para liquidar exatamente o saldo anterior (`amortization = previousBalance`). Isso garante a invariante contábil de que ao final do contrato a dívida está 100% quitada.

### 2.3. Imutabilidade com Records e `List.copyOf`

O `FinancialReport` utiliza o recurso de `record` (introduzido a partir do Java 14/16) combinado com um *compact constructor*:

```java
public FinancialReport {
    Objects.requireNonNull(installments, "Installments list cannot be null");
    installments = List.copyOf(installments);
}
```

O método `List.copyOf(...)` produz uma lista não-modificável e desconectada da lista mutável utilizada durante a construção no loop, garantindo encapsulamento rigoroso e evitando qualquer alteração do relatório após sua geração.

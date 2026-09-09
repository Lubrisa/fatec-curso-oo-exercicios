# [ID] — [Nome do Exercício em Português]

> **Módulo:** 01 — Java Básico (ou 03 — Java In-Depth)  
> **Tipologia:** Service / Algorítmico  
> **Dificuldade:** ⭐⭐☆ (Intermediário)  
> **Conceitos:** [Ex: Algoritmos, Streams, Coleções, Precisão Numérica]

## 1. Contexto & Cenário

[Apresentação de um problema de processamento de dados, cálculo computacional ou manipulação de coleções exigindo lógica sólida e domínio da biblioteca padrão do Java.]

## 2. Objetivos de Aprendizagem

- [Objetivo 1, ex: Implementar pipeline fluente de transformação e agregação de dados]
- [Objetivo 2, ex: Controlar precisão numérica e conversão explícita de tipos]
- [Objetivo 3, ex: Tratar fluxos com dados ausentes ou inválidos sem interrupções abruptas]

## 3. Especificação Funcional & Regras

1. [Descrição da assinatura do serviço e contrato de entrada/saída]:

   ```java
   /**
    * Calculates the interest amortization schedule for a given loan request.
    *
    * @param principal the initial borrowed capital, must be strictly positive
    * @param annualRate the annual interest rate expressed as a decimal
    * @param termInMonths the loan duration in months
    * @return an unmodifiable list of monthly payment records
    * @throws IllegalArgumentException if any numeric parameter violates constraints
    */
   List<PaymentSchedule> calculateSchedule(BigDecimal principal, BigDecimal annualRate, int termInMonths);
   ```

2. [Regras de negócio, casos limites e cálculos esperados].
3. [Restrições de complexidade, limites de memória ou estruturas permitidas/proibidas].

## 4. O que Deve Ser Entregue

- Pacote base: `br.com.fatec.basic.[id]` ou `br.com.fatec.indepth.[id]`
- Código implementado em inglês sob `src/main/java`.
- Documentação Javadoc padrão com `@param`, `@return` e `@throws` em toda a API pública.

## 5. Critérios de Aceite

- Suíte de testes automatizados presente em `src/test/java` executando com 100% de sucesso.
- Cobertura completa de casos normais e cenários limites especificados.

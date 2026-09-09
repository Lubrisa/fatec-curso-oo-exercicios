# [ID] — [Nome do Exercício em Português]

> **Módulo:** 02 — Orientação a Objetos  
> **Tipologia:** Sandbox / Laboratório  
> **Dificuldade:** ⭐⭐☆ (Intermediário)  
> **Conceitos:** [Ex: String Pool, Integer Cache, Identidade vs Igualdade na JVM]

## 1. Contexto & Premissa Teórica

[Apresentação de uma questão fundamental de engenharia de software na JVM onde intuição ingênua pode levar a conclusões erradas. Explica o objetivo de investigar o comportamento em tempo de execução.]

## 2. Roteiro Guiado de Experimentos

Execute os experimentos práticos contidos nas classes de laboratório sob `src/main/java`:

1. **Experimento 1 — [Título do Experimento 1]:**

   - Execute o método principal ou classe de benchmark correspondente.
   - Observe a saída no console ou o resultado das asserções de teste.
   - Anote os valores retornados e o tempo de execução (se aplicável).

2. **Experimento 2 — [Título do Experimento 2]:**

   - Varie os parâmetros de entrada conforme indicado nas anotações do código.
   - Compare o comportamento entre as diferentes abordagens testadas.

3. **Experimento 3 — [Título do Experimento 3]:**

   - Introduza o cenário limite ou de estresse indicado para avaliar a consistência da memória.

## 3. Perguntas Norteadoras de Análise

Ao analisar os resultados gerados pela JVM, responda às seguintes questões técnicas:

1. [Pergunta norteadora 1, ex: Por que a comparação com `==` retornou `true` para valores entre -128 e 127, mas falhou para 128?]
2. [Pergunta norteadora 2, ex: O que ocorre na memória Heap e no String Pool ao utilizar literais versus o construtor `new String(...)`?]
3. [Pergunta norteadora 3, ex: Qual é o risco de utilizar referências mutáveis como chave em coleções baseadas em tabela hash?]

## 4. O que Deve Ser Entregue

- Arquivo `RELATORIO.md` preenchido na pasta raiz do exercício.
- O relatório deve conter:
  - Tabela com os dados e resultados observados em cada experimento.
  - Justificativa técnica embasada na arquitetura da JVM para cada uma das perguntas norteadoras.
  - Conclusão prática com recomendações para o código de produção.
- Código auxiliar implementado em inglês com comentários explicativos e Javadoc na API pública.

## 5. Critérios de Aceite

- Execução completa de todos os passos previstos no roteiro de experimentos.
- Preenchimento do arquivo `RELATORIO.md` com explicações tecnicamente precisas e sem contradições conceituais.

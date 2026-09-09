# Solução Proposta — ex04: Classificador de Triângulos

Este diretório contém a implementação de referência do professor e a análise didática dos operadores lógicos e da validação geométrica.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex04/TriangleType.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex04/TriangleClassifier.java`

## 2. Análise Técnica & Decisões Didáticas

### 2.1. Centralização da Validação e Não Redundância

O método `isValidTriangle` atua como a única fonte de verdade sobre a geometria:
1. Valida se os lados são estritamente positivos (`sideA > 0 && sideB > 0 && sideC > 0`).
2. Avalia a desigualdade triangular: $(a + b > c) \land (a + c > b) \land (b + c > a)$.

Os métodos `classify` e `calculatePerimeter` delegam a verificação a `isValidTriangle`:

```java
if (!isValidTriangle(sideA, sideB, sideC)) {
    throw new IllegalArgumentException(ERROR_INVALID_TRIANGLE);
}
```

Isso impede que as regras de negócio se repitam de maneira redundante em diferentes métodos da classe utilitária.

### 2.2. Ordem das Condições de Classificação

Para classificar o triângulo, a ordem das verificações importa:
- Um triângulo equilátero tem 3 lados iguais e, por consequência, também tem 2 lados iguais.
- Se testássemos a condição de isósceles primeiro (`a == b || a == c || b == c`), um triângulo equilátero seria falsamente classificado como isósceles!
- Portanto, testamos primeiro a condição mais estrita (Equilátero: $a = b = c$), depois a intermediária (Isósceles: $a = b \lor a = c \lor b = c$) e, por eliminação direta, qualquer outro caso válido é Escaleno.

# ex04 — Classificador de Triângulos

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Desigualdade Geométrica, Estruturas Condicionais (`if` / `else if`), Operadores Relacionais e Operadores Lógicos (`&&`, `||`, `!`)  

## 1. Contexto & Cenário

Em sistemas de computação gráfica, softwares de engenharia civil e mecanismos de física computacional para jogos, figuras geométricas tridimensionais são frequentemente decompostas em malhas poligonais formadas por triângulos (*triangular meshes*). 

Antes de processar qualquer cálculo físico ou renderização, o motor gráfico precisa validar se três coordenadas de comprimento formam um triângulo geometricamente realizável no espaço euclidiano e categorizá-lo conforme a simetria de seus lados.

Você foi designado para implementar o validador e classificador geométrico `TriangleClassifier`.

## 2. Objetivos de Aprendizagem

- Praticar a formulação de condições lógicas compostas com operadores relacionais (`>`, `<`, `==`) e operadores booleanos (`&&`, `||`, `!`).
- Aplicar o **Teorema da Desigualdade Triangular**: para que três segmentos formem um triângulo, o comprimento de qualquer lado deve ser estritamente menor que a soma dos outros dois.
- Compreender a avaliação de curto-circuito (*short-circuit evaluation*) em expressões lógicas.
- Classificar entidades utilizando constantes enumeradas (`enum TriangleType`).
- Aplicar proteção *fail-fast* com `IllegalArgumentException` ao detectar dimensões inválidas.

## 3. Regras de Negócio & Classificação Geométrica

### 3.1. Condição de Existência (Desigualdade Triangular)

Três valores reais positivos `a`, `b` e `c` formam um triângulo válido se, e somente se, todas as três condições forem satisfeitas simultaneamente:

```text
(a + b > c) && (a + c > b) && (b + c > a)
```

Além disso, todos os lados devem ter comprimentos estritamente positivos (`a > 0`, `b > 0`, `c > 0`). Se qualquer lado for menor ou igual a zero, ou se a soma de dois lados for menor ou igual ao terceiro lado (por exemplo: `1, 2, 3` ou `1, 2, 5`), a figura não fecha e o triângulo **não existe**.

### 3.2. Classificação quanto aos Lados

Uma vez comprovada a existência do triângulo:
- **Equilátero (`EQUILATERAL`):** Os três lados possuem comprimentos iguais (`a == b && b == c`).
- **Isósceles (`ISOSCELES`):** Pelo menos dois lados possuem comprimentos iguais (`a == b || a == c || b == c`).
  > *Nota:* Todo triângulo equilátero possui dois lados iguais; por convenção de classificação exclusiva, se os três forem iguais, deve ser classificado como **Equilátero**.
- **Escaleno (`SCALENE`):** Todos os três lados possuem comprimentos distintos (`a != b && a != c && b != c`).

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex04` na classe `TriangleClassifier`:

### 4.1. `isValidTriangle`

```java
public static boolean isValidTriangle(double sideA, double sideB, double sideC)
```

- Retorna `true` se os três lados satisfazem as condições de existência (todos positivos e cumprindo a desigualdade triangular).
- Retorna `false` caso contrário (não lança exceção).

### 4.2. `classify`

```java
public static TriangleType classify(double sideA, double sideB, double sideC)
```

- Valida inicialmente se os lados formam um triângulo válido chamando `isValidTriangle`.
- Se não for válido, lança `IllegalArgumentException("Os lados fornecidos não formam um triângulo válido")`.
- Retorna o tipo correspondente: `TriangleType.EQUILATERAL`, `TriangleType.ISOSCELES` ou `TriangleType.SCALENE`.

---

### 4.3. Enumeração Fornecida (`TriangleType`)

> ℹ️ **Estrutura Pré-Pronta:** O arquivo `TriangleType.java` já vem implementado e **não precisa ser modificado**. Ele define as constantes de classificação geométrica:

```java
public enum TriangleType {
    EQUILATERAL,
    ISOSCELES,
    SCALENE
}
```

---

## 5. O que Você Deve Fazer

1. Abra o arquivo `src/main/java/br/com/fatec/basic/ex04/TriangleClassifier.java`.  
   *(O arquivo `TriangleType.java` já está pronto e não precisa de nenhuma modificação).*
2. Implemente a verificação geométrica no método `isValidTriangle`.
3. Complete o método `classify` utilizando estruturas `if` / `else if` organizadas com base no `isValidTriangle`.
4. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex04-classificador-triangulos
   ```

---

## 6. Critérios de Aceite

- Todos os testes da classe `TriangleClassifierTest` devem passar com sucesso.
- As tentativas de classificar triângulos degenerados ou inválidos devem lançar `IllegalArgumentException` com a mensagem `"Os lados fornecidos não formam um triângulo válido"`.
- As classificações de `EQUILATERAL`, `ISOSCELES` e `SCALENE` devem ser estritamente corretas.

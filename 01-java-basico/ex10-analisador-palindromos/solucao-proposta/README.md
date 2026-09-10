# Solução Proposta — ex10: Analisador de Palíndromos

Este diretório contém a implementação de referência do professor e a fundamentação técnica sobre a técnica de dois ponteiros, normalização Unicode e otimização de memória.

## 1. Código da Solução

O arquivo resolvido está em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex10/PalindromeAnalyzer.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. A Técnica dos Dois Ponteiros (Two Pointers)

Para verificar se uma cadeia de caracteres é simétrica, a abordagem ingênua que muitos iniciantes adotam é inverter a string:

```java
// Menos eficiente:
return new StringBuilder(text).reverse().toString().equals(text);
```

Desvantagens dessa abordagem:
1. **Alocação de Memória Desnecessária:** Cria uma nova instância de `StringBuilder`, aloca um novo buffer de caracteres, cria uma nova `String` invertida e depois realiza a comparação.
2. **Execução Completa:** Percorre a string até o fim para inverter, mesmo que o primeiro e o último caractere já sejam diferentes (ex: `"xararaz"`).

A **Técnica dos Dois Ponteiros** resolve o problema sem duplicar memória:
- Mantém dois ponteiros inteiros simples (`left = 0`, `right = length - 1`).
- Move-os simultaneamente em direção ao centro (`left++`, `right--`).
- **Interrupção Imediata (Fail-Fast):** Ao menor sinal de divergência (`charAt(left) != charAt(right)`), retorna `false` na primeira comparação ($O(1)$ no melhor caso).
- No pior caso (quando a palavra é um palíndromo), realiza no máximo $N / 2$ iterações, operando em tempo estritamente linear **$O(N)$** e espaço auxiliar **$O(1)$**.

### 2.2. Decomposição Unicode NFD e Remoção de Diacríticos

No padrão Unicode, um caractere acentuado como `'ô'` pode ser representado de duas maneiras:
1. **Forma Pré-composta (NFC - Normalization Form C):** Um único ponto de código Unicode (U+00F4).
2. **Forma Decomposta (NFD - Normalization Form D):** A letra base `'o'` (U+006F) seguida pelo caractere combinatório de acento circunflexo `'\u0302'` (U+0302).

Ao aplicar:
```java
Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "")
```
- A classe `java.text.Normalizer` separa a letra base de suas marcas diacríticas.
- A expressão regular `\p{M}` (*Mark/Diacritic*) captura e remove exclusivamente os acentos combinatórios, transformando `"ônibus"` em `"onibus"` e `"maçã"` em `"maca"` de maneira universal e elegante.

### 2.3. Resolução do Desafio: Verificação On-the-Fly em O(1) de Memória com Suporte a Acentos e Cedilha

Na implementação de dois ponteiros *on-the-fly*, apenas chamar `Character.toLowerCase(c)` **não é suficiente** para textos em língua portuguesa. Em Java:
- `Character.isLetterOrDigit('ô')` e `Character.isLetterOrDigit('ç')` retornam `true`.
- Porém, `Character.toLowerCase('ô')` permanece `'ô'` (`\u00F4`), que é numericamente diferente de `'o'` (`\u006F`). O mesmo ocorre para `'ç'` (`\u00E7`) versus `'c'` (`\u0063`).

Se a verificação comparasse apenas `Character.toLowerCase`, frases célebres como *"Socorram-me, subi no ônibus em Marrocos!"* falhariam na comparação entre o `'ô'` e o `'o'`.

Para manter a solução **estritamente em espaço auxiliar $O(1)$** (sem instanciar uma nova string no Heap), combinamos os dois ponteiros com uma função pura de desacentuação direta caractere a caractere (`switch` expression do Java moderno):

```java
public static boolean isPalindromeOnTheFly(String text) {
    if (text == null) {
        throw new IllegalArgumentException("O texto informado não pode ser nulo");
    }

    int left = 0;
    int right = text.length() - 1;

    while (left < right) {
        // Avança o ponteiro da esquerda enquanto não for letra ou dígito
        while (left < right && !Character.isLetterOrDigit(text.charAt(left))) {
            left++;
        }
        // Recua o ponteiro da direita enquanto não for letra ou dígito
        while (left < right && !Character.isLetterOrDigit(text.charAt(right))) {
            right--;
        }

        char cLeft = normalizeChar(text.charAt(left));
        char cRight = normalizeChar(text.charAt(right));

        if (cLeft != cRight) {
            return false;
        }

        left++;
        right--;
    }

    return true;
}

/**
 * Normaliza o caractere convertendo para minúsculo e removendo marcas diacríticas
 * em tempo constante O(1) e sem alocação de objetos no Heap.
 */
private static char normalizeChar(char c) {
    char lower = Character.toLowerCase(c);
    return switch (lower) {
        case 'á', 'à', 'â', 'ã', 'ä' -> 'a';
        case 'é', 'è', 'ê', 'ë' -> 'e';
        case 'í', 'ì', 'î', 'ï' -> 'i';
        case 'ó', 'ò', 'ô', 'õ', 'ö' -> 'o';
        case 'ú', 'ù', 'û', 'ü' -> 'u';
        case 'ç' -> 'c';
        case 'ñ' -> 'n';
        default -> lower;
    };
}
```

Essa solução combina o melhor dos dois mundos:
- **Zero Alocações de Memória Auxiliar ($O(1)$ espaço):** Não cria instâncias intermediárias de `String`, `StringBuilder` ou arrays no Heap.
- **Suporte Integral ao Português:** Reconhece equivalências fonéticas e ortográficas de acentos (`á`, `à`, `ã`, `â`, `é`, `ê`, `í`, `ó`, `ô`, `õ`, `ú`, etc.) e da cedilha (`ç`).
- **Eficiência Máxima ($O(N)$ tempo):** Avalia e descarta discrepâncias na primeira divergência encontrada.

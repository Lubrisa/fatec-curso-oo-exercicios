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

### 2.3. Resolução do Desafio: Verificação On-the-Fly em O(1) de Memória

A abordagem canônica aloca uma nova string sanitizada no Heap via `sanitize(text)`. Para textos muito grandes, isso gera consumo auxiliar desnecessário de memória ($O(N)$). 

Para verificar se um texto é palíndromo diretamente sobre a string original em espaço auxiliar **$O(1)$**, a técnica de dois ponteiros precisa solucionar simultaneamente **3 fatores**:

1. **Caracteres Ignorados (Pulo de Ruído):**
   - Espaços, pontuações, hífens e símbolos especiais não devem fazer parte da análise.
   - **Solução:** Em vez de filtrar e criar uma nova string, os laços internos simplesmente avançam (`left++`) ou recuam (`right--`) enquanto `!Character.isLetterOrDigit(...)`.
2. **Caracteres Convertidos (Case-Insensitivity):**
   - Letras maiúsculas e minúsculas devem ser consideradas equivalentes (ex: `'A'` e `'a'`).
   - **Solução:** Conversão direta do caractere inspecionado via `Character.toLowerCase(c)` no momento exato da comparação.
3. **Normalização (Acentos e Cedilha):**
   - Em Java, `Character.toLowerCase('ô')` permanece `'ô'` (`\u00F4`), que é numericamente diferente de `'o'` (`\u006F`). O mesmo ocorre para `'ç'` (`\u00E7`) e `'c'` (`\u0063`).
   - **Solução:** Para evitar o uso de `Normalizer` (que alocaria um novo objeto no Heap), utilizamos uma função pura `normalizeChar(char c)` com `switch` inline, mapeando os caracteres acentuados para sua letra base em tempo $O(1)$ e sem alocação de memória.

#### Implementação de Referência:

```java
public static boolean isPalindromeOnTheFly(String text) {
    if (text == null) {
        throw new IllegalArgumentException("O texto informado não pode ser nulo");
    }

    int left = 0;
    int right = text.length() - 1;

    while (left < right) {
        // 1. Pula caracteres que devem ser ignorados
        while (left < right && !Character.isLetterOrDigit(text.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(text.charAt(right))) {
            right--;
        }

        // 2 e 3. Converte maiúsculas e normaliza acentos/cedilha
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
 * Converte para minúscula e remove acentos/cedilha em O(1) de tempo e memória.
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
- **Zero Alocações no Heap ($O(1)$ de espaço auxiliar):** Não gera novas strings ou arrays intermediários.
- **Suporte Completo à Língua Portuguesa:** Reconhece pontuações complexas, diferenças de caixa e equivalências ortográficas.
- **Interrupção Rápida ($O(N)$ no pior caso, $O(1)$ no melhor caso):** Falha imediatamente na primeira divergência encontrada.

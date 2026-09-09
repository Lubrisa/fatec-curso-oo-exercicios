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

Para evitar gerar strings intermediárias no Heap, é possível avançar os dois ponteiros sobre a string original ignorando caracteres inválidos em tempo de execução:

```java
public static boolean isPalindromeOnTheFly(String text) {
    if (text == null) throw new IllegalArgumentException("O texto informado não pode ser nulo");
    
    // Normalização inicial apenas se houver acentuação, ou varredura direta com mapeamento de caracteres
    int left = 0;
    int right = text.length() - 1;

    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(text.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(text.charAt(right))) {
            right--;
        }
        
        char cLeft = Character.toLowerCase(text.charAt(left));
        char cRight = Character.toLowerCase(text.charAt(right));

        if (cLeft != cRight) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```

Essa solução reduz o consumo de memória auxiliar a zero ($O(1)$), sendo a implementação padrão utilizada em motores de busca e bibliotecas de alta performance.

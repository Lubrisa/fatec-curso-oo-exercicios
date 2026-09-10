# ex10 — Analisador de Palíndromos

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Algoritmo / Manipulação de Cadeias de Caracteres & Técnica de Dois Ponteiros  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Técnica dos Dois Ponteiros (*Two Pointers*), Sanitização de Strings, Normalização Unicode com `java.text.Normalizer`, Métodos da Classe `Character` e Eficiência de Memória  

## 1. Contexto & Cenário

Um **palíndromo** é uma sequência de caracteres que pode ser lida da mesma forma tanto da esquerda para a direita quanto da direita para a esquerda. Na literatura e na língua portuguesa, frases palindrômicas famosas encantam pela simetria:
- *"Socorram-me, subi no ônibus em Marrocos!"*
- *"A cara rajada da jararaca"*
- *"Ame o poema"*
- *"A base do teto desaba"*

Em ciência da computação, a análise de palíndromos é um dos problemas fundamentais de processamento de texto e bioinformática (por exemplo, na detecção de sequências palindrômicas no DNA onde certas enzimas de restrição realizam cortes moleculares).

Você deve desenvolver o módulo utilitário `PalindromeAnalyzer`, projetado para validar tanto palíndromos exatos/estritos quanto frases complexas em linguagem natural que contêm acentos, maiúsculas, pontuações e espaços.

## 2. Objetivos de Aprendizagem

- Compreender e aplicar a **Técnica dos Dois Ponteiros** (*Two-Pointers Technique*), movendo índices das extremidades em direção ao centro com complexidade de tempo linear $O(N)$.
- Diferenciar verificação **estrita** (onde maiúsculas, pontuações e espaços contam) de verificação **flexível/natural** (onde a frase é normalizada).
- Utilizar `java.text.Normalizer` para decomposição de caracteres acentuados (NFD) e remoção de marcas diacríticas (`\p{M}`).
- Utilizar métodos utilitários da classe `java.lang.Character` (`isLetterOrDigit`, `toLowerCase`).
- Tratar parâmetros nulos com lançamento de `IllegalArgumentException` e validar casos de borda (strings vazias ou com apenas um caractere).

## 3. Regras de Negócio & Algoritmo de Verificação

### 3.1. Regra Arquitetural Obrigatória: A Técnica dos Dois Ponteiros (*Two Pointers*)

Para garantir eficiência de processamento e economia de memória, **é obrigatório** que todas as verificações de simetria (`isStrictPalindrome` e `isPalindrome`) utilizem a **técnica dos dois ponteiros**:
- Inicializa dois índices inteiros nas extremidades da cadeia: `left = 0` e `right = length - 1`.
- Compara os caracteres: se `charAt(left) != charAt(right)`, encerra a execução e retorna `false` imediatamente (*interrupção precoce / fail-fast*).
- Se forem iguais, converge os ponteiros em direção ao centro (`left++`, `right--`).
- O laço é concluído com sucesso quando `left >= right`, confirmando a simetria com retorno `true`.
- **Restrição de Implementação:** É expressamente desaconselhado/proibido o uso de `new StringBuilder(text).reverse()` ou duplicação reversa de strings para validação, pois isso consome memória adicional desnecessária no Heap ($O(N)$).

---

### 3.2. Validação Estrita (`isStrictPalindrome`)

Uma string é considerada um palíndromo estrito quando todos os seus caracteres coincidem exatamente de ponta a ponta, sem nenhuma normalização ou descarte:
- `"arara"` $\to$ `true`
- `"radar"` $\to$ `true`
- `"Arara"` $\to$ `false` (o caractere `'A'` é diferente de `'a'`)
- `"aba aba"` $\to$ `true`
- `"aba   aba "` $\to$ `false` (espaço final não correspondido)
- Uma string vazia `""` ou de tamanho 1 é considerada palíndromo por vacuidade/simetria trivial.
- Deve aplicar os dois ponteiros diretamente sobre o texto original.

---

### 3.3. Sanitização de Texto (`sanitize`)

Para permitir a validação de frases e expressões em linguagem natural, o método `sanitize(String text)` deve:
1. Validar se `text == null` (lançando `IllegalArgumentException("O texto informado não pode ser nulo")`).
2. Remover acentuações utilizando `Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "")` (exemplo: `"ônibus"` vira `"onibus"`, `"maçã"` vira `"maca"`).
3. Converter todos os caracteres para minúsculas.
4. Manter **apenas** caracteres alfanuméricos (`Character.isLetterOrDigit`), descartando espaços, pontuações, hífens e símbolos especiais.

**Exemplo:**
- Entrada: `"Socorram-me, subi no ônibus em Marrocos!"`
- Saída Sanitizada: `"socorrammesubinoonibusemmarrocos"`

---

### 3.4. Validação em Linguagem Natural (`isPalindrome`)

Avalia se o texto informado forma um palíndromo quando sanitizado:
1. Valida se `text == null` (lançando `IllegalArgumentException("O texto informado não pode ser nulo")`).
2. Se o texto sanitizado estiver vazio (por exemplo, uma entrada composta apenas por `"   !@#  "`), o método deve retornar `true` (já que não há caracteres conflitantes).
3. Aplica a técnica dos dois ponteiros (`left = 0`, `right = sanitized.length() - 1`) sobre o texto sanitizado (podendo reaproveitar internamente a chamada a `isStrictPalindrome(sanitized)`).

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex10` na classe utilitária `PalindromeAnalyzer`:

### 4.1. `isStrictPalindrome`

```java
public static boolean isStrictPalindrome(String text)
```
- Valida se `text != null`, lançando `IllegalArgumentException("O texto informado não pode ser nulo")` caso contrário.
- Aplica os dois ponteiros diretamente sobre a string original sem modificações.

### 4.2. `sanitize`

```java
public static String sanitize(String text)
```
- Valida se `text != null`.
- Decompõe acentuações com `Normalizer.Form.NFD`, converte para minúsculas e filtra apenas letras e dígitos.
- Retorna a string pura e normalizada.

### 4.3. `isPalindrome`

```java
public static boolean isPalindrome(String text)
```
- Valida se `text != null`.
- Sanitiza o texto de entrada e verifica a simetria com dois ponteiros.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex10/PalindromeAnalyzer.java`.
2. Implemente a validação defensiva de `null` em todos os métodos públicos.
3. Implemente `isStrictPalindrome` utilizando dois ponteiros inteiros (`left` e `right`).
4. Implemente `sanitize` empregando `java.text.Normalizer` e um `StringBuilder` para acumular apenas letras e dígitos em minúsculo.
5. Implemente `isPalindrome` reaproveitando `sanitize` e a lógica de dois ponteiros.
6. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex10-analisador-palindromos
   ```

---

> ### 💡 Desafio Opcional de Eficiência: Dois Ponteiros "On-the-Fly" (Zero Alocação)
>
> A abordagem canônica cria uma nova string sanitizada intermediária na memória Heap com `sanitize(text)` antes de testar a simetria. Para textos gigantescos, isso gera alocação desnecessária de memória ($O(N)$ de espaço auxiliar).
>
> 🔍 **Dica de Reflexão — Como resolver os 3 fatores em espaço $O(1)$:**  
> Seria possível verificar a simetria percorrendo a string original **diretamente**, sem criar nenhuma cópia intermediária?  
> 1. **Caracteres Ignorados:** Ao encontrar espaços ou pontuações, avançar `left++` e recuar `right--` enquanto `!Character.isLetterOrDigit(...)`.  
> 2. **Caracteres Convertidos:** Aplicar `Character.toLowerCase` em tempo real para igualar maiúsculas e minúsculas.  
> 3. **Normalização:** Lidar com acentos e cedilha (ex: `'ô'` vs `'o'`, `'ç'` vs `'c'`) via uma função pura com `switch` que mapeia caracteres acentuados para sua letra base em $O(1)$.  
> Essa estratégia combina os dois ponteiros com custo auxiliar nulo no Heap!

## 6. Critérios de Aceite

- Todos os testes da classe `PalindromeAnalyzerTest` devem passar com sucesso (`BUILD SUCCESS`).
- As verificações de simetria devem obrigatoriamente empregar a técnica dos dois ponteiros com convergência central e parada *fail-fast*.
- Parâmetros nulos devem lançar `IllegalArgumentException` com a mensagem exata `"O texto informado não pode ser nulo"`.
- `isStrictPalindrome` deve ser sensível a maiúsculas, espaços e pontuações.
- `isPalindrome` deve validar corretamente frases clássicas em língua portuguesa com acentos, pontuação variada e maiúsculas mistas.
- Frases que não são palíndromos devem ser rejeitadas rapidamente na primeira divergência encontrada.

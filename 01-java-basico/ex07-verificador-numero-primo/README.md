# ex07 — Verificador de Números Primos

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Algoritmo / Otimização Numérica  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Teste de Primalidade, Otimização Assintótica O(sqrt(N)), Laços com Interrupção Antecipada, Operador Módulo (`%`) e Alocação Exata de Arrays  

## 1. Contexto & Cenário

Números primos são a base fundamental da teoria dos números e da criptografia moderna (como os algoritmos RSA e curvas elípticas que protegem transações bancárias e chaves SSH). Um número primo é um inteiro natural maior que 1 que possui exatamente dois divisores positivos distintos: o número 1 e ele mesmo.

Em sistemas de segurança de informação e geração de chaves criptográficas, rotinas de verificação de primalidade precisam ser extremamente rápidas. Testar ingenuamente todos os números de 2 até N torna o sistema inviável para valores moderados ou grandes.

Sua missão é desenvolver a classe utilitária `PrimeNumberChecker`, implementando um algoritmo otimizado de verificação de primalidade e métodos auxiliares para busca e contagem de primos em intervalos numéricos.

## 2. Objetivos de Aprendizagem

- Compreender a definição matemática de números primos e as propriedades dos números compostos.
- Implementar a otimização de **interrupção na raiz quadrada** ($O(\sqrt{N})$), reduzindo drasticamente o número de iterações em relação ao laço ingênuo ($O(N)$).
- Eliminar de imediato números pares maiores que 2, permitindo que o laço principal avance de dois em dois (`divisor += 2`).
- Usar laços de repetição com saída antecipada (*early return*) assim que o primeiro divisor for encontrado.
- Coletar elementos filtrados em um array primitivo `long[]` redimensionado para o tamanho exato da quantidade encontrada.
- Proteger métodos contra parâmetros inconsistentes lançando `IllegalArgumentException`.

## 3. Regras de Negócio & Algoritmo de Primalidade

### 3.1. Definição Matemática & Casos Especiais

- Números **menores ou iguais a 1** (`... -2, -1, 0, 1`) **não são primos** por definição.
- O número **2** é o **menor número primo** e o **único primo que é par**.
- Qualquer número **par maior que 2** (`4, 6, 8, 10, ...`) **não é primo**, pois é divisível por 2.

```text
Entrada <= 1  -> false  (0, 1 e negativos não são primos)
Entrada == 2  -> true   (único primo par)
Entrada par > 2 -> false (qualquer outro par tem 2 como divisor)
```

---

### 3.2. A Otimização da Raiz Quadrada: Por que testar apenas até √N?

Se um número $N$ for composto (não primo), ele pode ser escrito como o produto de dois fatores inteiros:

```text
N = a * b
```

Se ambos os fatores $a$ e $b$ fossem estritamente maiores que $\sqrt{N}$, o produto $a \times b$ seria obrigatoriamente maior que $N$ ($\sqrt{N} \times \sqrt{N} = N$). Portanto, é matematicamente garantido que **pelo menos um dos divisores deve ser menor ou igual a $\sqrt{N}$**.

#### Comparação de Esforço Computacional:

Para verificar se o número `1.000.001` é primo:
- **Abordagem Ingênua:** Testaria até 1.000.000 iterações ($O(N)$).
- **Abordagem Otimizada com Raiz:** Testa divisores ímpares apenas até $\sqrt{1.000.001} \approx 1.000$ iterações ($O(\sqrt{N})$) — uma economia de **99,9% do tempo de processamento**!

---

### 3.3. Busca do Próximo Primo (`nextPrime`)

Dado qualquer número inteiro $N$, o método `nextPrime(N)` deve encontrar o menor número primo **estritamente maior que $N$**:

```text
nextPrime(-5) -> 2   (o menor primo existente é 2)
nextPrime(1)  -> 2
nextPrime(2)  -> 3
nextPrime(10) -> 11
nextPrime(11) -> 13
```

---

### 3.4. Varredura em Intervalo (`findPrimesInRange`)

Retorna um array primitivo contendo todos os números primos existentes no intervalo fechado `[start, end]`:

```text
Exemplo: start = 10, end = 25
Primos no intervalo: 11, 13, 17, 19, 23
Array retornado: [11L, 13L, 17L, 19L, 23L] (tamanho exato: 5 posições)
```

- O array retornado deve ter **exatamente o tamanho da quantidade de primos encontrados** (sem posições extras preenchidas com zero).
- Se não houver primos no intervalo (ex.: `start = 24, end = 28`), deve retornar um array vazio (`long[0]`).
- Regras de validação:
  - Se `start < 0`: lança `IllegalArgumentException("O início do intervalo não pode ser negativo")`.
  - Se `start > end`: lança `IllegalArgumentException("O início do intervalo não pode ser maior que o fim")`.

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex07` na classe utilitária `PrimeNumberChecker`:

### 4.1. `isPrime`

```java
public static boolean isPrime(long n)
```

- Retorna `false` se $n \le 1$.
- Retorna `true` se $n = 2$.
- Retorna `false` se $n$ for par e maior que 2.
- Itera com divisores ímpares a partir de 3 com passo 2 (`d += 2`) enquanto $d \times d \le n$ (ou $d \le \lfloor\sqrt{n}\rfloor$).
- Se encontrar qualquer divisor com resto zero (`n % d == 0`), retorna `false` imediatamente.
- Se o laço terminar sem encontrar divisores, retorna `true`.

### 4.2. `nextPrime`

```java
public static long nextPrime(long n)
```

- Se $n < 2$, o próximo primo é 2.
- Caso contrário, inicia a busca a partir de $n + 1$ (ou do próximo ímpar) e testa consecutivamente com `isPrime` até encontrar o primeiro primo.

### 4.3. `countPrimes`

```java
public static int countPrimes(long start, long end)
```

- Valida as regras de intervalo (`start >= 0` e `start <= end`).
- Itera de `start` até `end` contando quantos números satisfazem `isPrime`.

### 4.4. `findPrimesInRange`

```java
public static long[] findPrimesInRange(long start, long end)
```

- Valida as regras de intervalo (`start >= 0` e `start <= end`).
- Apura a quantidade exata de primos no intervalo (pode reaproveitar `countPrimes`).
- Aloca o array de tamanho exato e preenche com os números primos encontrados.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex07/PrimeNumberChecker.java`.
2. Implemente o método `isPrime(long n)` aplicando os atalhos para números pares e a otimização de parada em $\sqrt{n}$.
3. Implemente `nextPrime(long n)` realizando a busca ascendente.
4. Implemente `countPrimes` e `findPrimesInRange` alocando o array com tamanho exato.
5. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex07-verificador-numero-primo
   ```

---

> ### 💡 Dica de Eficiência no Laço de Raiz Quadrada
>
> Para evitar calcular a função de ponto flutuante `Math.sqrt(n)` a cada iteração, você pode testar diretamente a condição:
> ```java
> for (long d = 3; d * d <= n; d += 2) { ... }
> ```
> Multiplicar inteiros (`d * d`) é uma instrução que roda em um único ciclo de clock na CPU, sendo ordens de grandeza mais rápida que a radiciação de ponto flutuante! *(Apenas atente-se para números próximos de `Long.MAX_VALUE` onde `d * d` poderia estourar).*

## 6. Critérios de Aceite

- Todos os testes da classe `PrimeNumberCheckerTest` devem passar com sucesso (`BUILD SUCCESS`).
- O método `isPrime` deve validar corretamente números negativos, zero, um, dois e números compostos/primos grandes.
- O tempo de execução da suíte de testes deve ser quase instantâneo graças à otimização $O(\sqrt{N})$.
- Tentativas de passar intervalos inválidos (`start < 0` ou `start > end`) devem lançar `IllegalArgumentException` com as mensagens especificadas.

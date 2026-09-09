# ex01 — Conversor Térmico

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Algorítmico  
> **Dificuldade:** ⭐☆☆ (Iniciante)  
> **Conceitos:** Tipos Primitivos (`double`), Operadores Aritméticos, Precedência, Divisão de Ponto Flutuante e Validação de Limites  

## 1. Contexto & Cenário

Você está desenvolvendo o módulo de processamento de dados para uma estação meteorológica digital. Essa estação recebe leituras de sensores calibrados em diferentes escalas termométricas (Celsius, Fahrenheit e Kelvin) e precisa padronizar os dados para exibição aos meteorologistas.

Seu objetivo é implementar a classe utilitária `TemperatureConverter`, fornecendo conversões precisas entre as três escalas mais utilizadas no mundo científico e cotidiano.

## 2. Objetivos de Aprendizagem

- Declarar e utilizar variáveis e parâmetros de ponto flutuante de dupla precisão (`double`).
- Compreender a diferença fundamental entre divisão inteira (`5 / 9`) e divisão em ponto flutuante (`5.0 / 9.0`).
- Aplicar operadores aritméticos básicos (`+`, `-`, `*`, `/`) respeitando a ordem e precedência das operações com parênteses.
- Validar argumentos de entrada e lançar exceções da biblioteca padrão do Java (`IllegalArgumentException`) quando valores fisicamente impossíveis forem informados.

## 3. Especificação Funcional & Fórmulas

A classe deve conter quatro métodos utilitários estáticos para conversão entre escalas:

### 3.1. Celsius para Fahrenheit

Fórmula termométrica:

$$F = \left(C \times \frac{9}{5}\right) + 32$$

- Se a temperatura em Celsius for estritamente inferior a $-273.15^\circ\text{C}$ (zero absoluto), deve lançar `IllegalArgumentException`.

### 3.2. Fahrenheit para Celsius

Fórmula termométrica:

$$C = (F - 32) \times \frac{5}{9}$$

- Se a temperatura em Fahrenheit for estritamente inferior a $-459.67^\circ\text{F}$ (zero absoluto), deve lançar `IllegalArgumentException`.

### 3.3. Celsius para Kelvin

Fórmula termométrica:

$$K = C + 273.15$$

- Se a temperatura em Celsius for estritamente inferior a $-273.15^\circ\text{C}$, deve lançar `IllegalArgumentException`.

### 3.4. Kelvin para Celsius

Fórmula termométrica:

$$C = K - 273.15$$

- Se a temperatura em Kelvin for estritamente inferior a $0.0\text{ K}$ (zero absoluto), deve lançar `IllegalArgumentException`.

## 4. Regras de Validação & Armadilha Clássica do Java

1. **Atenção com a Divisão Inteira no Java:**

   - No Java, a expressão `5 / 9` envolve dois números inteiros (`int`). O resultado dessa divisão é truncado para `0` (zero)!
   - Para que a divisão ocorra com casas decimais, ao menos um dos operandos deve ser um número de ponto flutuante: `5.0 / 9.0` ou `5.0 / 9`.

2. **Validação do Zero Absoluto:**

   - A menor temperatura fisicamente possível no universo é o zero absoluto ($0\text{ K}$, equivalente a $-273.15^\circ\text{C}$ ou $-459.67^\circ\text{F}$).
   - Sempre que um valor de entrada violar essa restrição física, o método deve lançar:

   ```java
   throw new IllegalArgumentException("Temperatura abaixo do zero absoluto");
   ```

3. **Constantes:**

   - Declare constantes `private static final double` para representar os valores do zero absoluto nas três escalas, evitando números mágicos espalhados pelo código.

## 5. Estrutura da Classe & API Pública

O código pertence ao pacote `br.com.fatec.basic.ex01`:

```java
package br.com.fatec.basic.ex01;

public final class TemperatureConverter {

    private TemperatureConverter() {
        // Utility class: cannot be instantiated
    }

    public static double celsiusToFahrenheit(double celsius);

    public static double fahrenheitToCelsius(double fahrenheit);

    public static double celsiusToKelvin(double celsius);

    public static double kelvinToCelsius(double kelvin);
}
```

## 6. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex01/TemperatureConverter.java`.
2. Implemente os 4 métodos de conversão respeitando as fórmulas e validações.
3. Garanta que toda a API pública contenha documentação Javadoc (`@param`, `@return`, `@throws`).
4. Execute os testes automatizados para validar sua solução.

## 7. Critérios de Aceite

- Todos os casos de teste da classe `TemperatureConverterTest` devem passar com sucesso.
- O código deve tratar as entradas nos pontos notáveis (ponto de fusão da água $0^\circ\text{C} = 32^\circ\text{F} = 273.15\text{ K}$, ponto de ebulição da água $100^\circ\text{C} = 212^\circ\text{F} = 373.15\text{ K}$).
- Testes parametrizados devem confirmar o lançamento de `IllegalArgumentException` para temperaturas abaixo do zero absoluto.

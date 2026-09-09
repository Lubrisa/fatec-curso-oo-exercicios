# ex01 — Conversor Térmico

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Lógica Matemática  
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

## 3. Regras de Negócio & Fórmulas de Conversão

A estação opera com quatro conversões padronizadas:

### 3.1. Fórmulas de Conversão

1. **Celsius para Fahrenheit:**
   ```text
   F = (C * 9.0 / 5.0) + 32.0
   ```
2. **Fahrenheit para Celsius:**
   ```text
   C = (F - 32.0) * 5.0 / 9.0
   ```
3. **Celsius para Kelvin:**
   ```text
   K = C + 273.15
   ```
4. **Kelvin para Celsius:**
   ```text
   C = K - 273.15
   ```

### 3.2. Regras de Validação & Limite do Zero Absoluto

A menor temperatura fisicamente possível no universo é o **zero absoluto** (`0.0 K`, equivalente a `-273.15 °C` ou `-459.67 °F`):
- Se uma leitura de entrada for estritamente menor do que o zero absoluto na respectiva escala, o método deve lançar imediatamente:
  ```java
  throw new IllegalArgumentException("Temperatura abaixo do zero absoluto");
  ```
- Recomenda-se declarar constantes `private static final double` para os limites de zero absoluto nas três escalas, evitando números mágicos no código.

### 3.3. Atenção com a Divisão Inteira no Java

No Java, a expressão `5 / 9` envolve dois números inteiros (`int`), resultando no truncamento para `0` (zero). Para manter a precisão decimal correta, use literais em ponto flutuante: `5.0 / 9.0` ou `9.0 / 5.0`.

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex01` na classe utilitária `TemperatureConverter`:

### 4.1. `celsiusToFahrenheit`

```java
public static double celsiusToFahrenheit(double celsius)
```
- Converte Celsius para Fahrenheit. Lança `IllegalArgumentException` se `celsius < -273.15`.

### 4.2. `fahrenheitToCelsius`

```java
public static double fahrenheitToCelsius(double fahrenheit)
```
- Converte Fahrenheit para Celsius. Lança `IllegalArgumentException` se `fahrenheit < -459.67`.

### 4.3. `celsiusToKelvin`

```java
public static double celsiusToKelvin(double celsius)
```
- Converte Celsius para Kelvin. Lança `IllegalArgumentException` se `celsius < -273.15`.

### 4.4. `kelvinToCelsius`

```java
public static double kelvinToCelsius(double kelvin)
```
- Converte Kelvin para Celsius. Lança `IllegalArgumentException` se `kelvin < 0.0`.

## 5. O que Você Deve Fazer

1. Abra a classe `src/main/java/br/com/fatec/basic/ex01/TemperatureConverter.java`.
2. Implemente os 4 métodos de conversão respeitando as fórmulas e validações.
3. Garanta que toda a API pública contenha documentação Javadoc (`@param`, `@return`, `@throws`).
4. Execute os testes automatizados com:
   ```bash
   ./mvnw test -pl :ex01-conversor-temperatura
   ```

## 6. Critérios de Aceite

- Todos os casos de teste da classe `TemperatureConverterTest` devem passar com sucesso.
- O código deve converter com precisão nos pontos notáveis (fusão da água: `0.0 °C = 32.0 °F = 273.15 K`; ebulição da água: `100.0 °C = 212.0 °F = 373.15 K`).
- Testes parametrizados devem confirmar o lançamento de `IllegalArgumentException` para temperaturas abaixo do zero absoluto.

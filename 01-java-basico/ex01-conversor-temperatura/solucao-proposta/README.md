# Solução Proposta — ex01: Conversor Térmico

Este diretório contém a resolução de referência do professor e a análise conceitual dos pontos de atenção didáticos.

## 1. Código da Solução

O arquivo resolvido está em:
`solucao-proposta/src/main/java/br/com/fatec/basic/ex01/TemperatureConverter.java`

## 2. Decisões Didáticas & Armadilhas Comuns

### 2.1. Divisão Inteira vs Ponto Flutuante

A armadilha mais comum entre iniciantes no Java está na fórmula de conversão de Fahrenheit para Celsius:

```java
// ERRADO: (5 / 9) é divisão inteira e resulta em 0!
double celsius = (fahrenheit - 32.0) * (5 / 9); // Sempre resultará em 0.0

// CORRETO: Ao menos um literal deve ser double (5.0 ou 9.0)
double celsius = (fahrenheit - 32.0) * (5.0 / 9.0);
```

Mesmo que a variável de destino seja declarada como `double`, a subexpressão `(5 / 9)` é avaliada primeiro como uma operação entre inteiros primitivos de 32 bits (`int`), descartando a parte fracionária antes de qualquer atribuição.

### 2.2. Uso de Constantes contra "Números Mágicos"

Em vez de repetir literais soltos como `-273.15` ou `273.15` em múltiplos métodos, declaramos constantes estáticas com nomes expressivos:

```java
private static final double ABSOLUTE_ZERO_CELSIUS = -273.15;
private static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;
private static final double ABSOLUTE_ZERO_KELVIN = 0.0;
```

Isso facilita a leitura, a manutenção e permite que os testes e clientes da classe reutilizem esses limites físicos de forma segura.

### 2.3. Validação Fail-Fast

A validação de argumentos é executada no início do método (*guard clause*), interrompendo imediatamente a execução caso a temperatura informada seja fisicamente incoerente:

```java
if (celsius < ABSOLUTE_ZERO_CELSIUS) {
    throw new IllegalArgumentException("Temperatura abaixo do zero absoluto");
}
```

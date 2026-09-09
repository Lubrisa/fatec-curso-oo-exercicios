# Solução Proposta — ex02: Calculadora de Consumo de Viagem

Este diretório contém a resolução de referência do professor e a análise técnica dos conceitos trabalhados.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex02/TripSummary.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex02/TripCostCalculator.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. Métodos Especializados e Encadeamento de Dados

A arquitetura do exercício divide um problema em três etapas atômicas:
1. `calculateLitersNeeded`: depende apenas da distância e da autonomia.
2. `calculateTotalCost`: recebe diretamente a litragem já calculada e o preço por litro.
3. `calculateCostPerKm`: recebe o custo total já apurado e a distância.

Isso ensina ao aluno que um método não deve recalcular o que outro método já apurou, demonstrando como parâmetros e valores de retorno servem de canal de comunicação entre blocos lógicos.

### 2.2. Promoção de Tipos e Casting Explícito

No cálculo de litragem e de custo por quilômetro, temos operações envolvendo o inteiro `distanceInKm`:

```java
return (double) distanceInKm / fuelEfficiencyKmPerLiter;
```

```java
return totalCost / (double) distanceInKm;
```

Embora o Java promova automaticamente um `int` para `double` quando o outro operando já é `double`, habituar os alunos a explicitarem o casting desenvolve consciência de tipos e evita o erro clássico de divisão inteira (`5 / 2 = 2` em vez de `2.5`).

### 2.3. Facilitação Inicial com `record`

Como a orientação a objetos formal (criação e instanciação de classes/records com `new`) ainda será apresentada nos módulos seguintes, deixar o retorno `new TripSummary(distanceInKm, litersNeeded, totalCost, costPerKm)` previamente preparado permite ao aluno focar 100% na lógica procedural, variáveis e controle de fluxo sem gerar sobrecarga cognitiva prematura.

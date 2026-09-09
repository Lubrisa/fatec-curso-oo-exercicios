# Solução Proposta — ex09: Validador de Senhas Fortes

Este diretório contém a implementação de referência do professor e a fundamentação técnica sobre manipulação eficiente de Strings, conformidade de segurança e modelagem com records.

## 1. Código da Solução

Os arquivos resolvidos estão em:
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex09/PasswordValidator.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex09/PasswordValidationResult.java`
- `solucao-proposta/src/main/java/br/com/fatec/basic/ex09/PasswordStrength.java`

## 2. Decisões Didáticas & Análise Conceitual

### 2.1. Varredura Única O(N) vs Expressões Regulares (Regex)

Muitos desenvolvedores tentam resolver validação de senhas com múltiplas expressões regulares:

```java
// Menos eficiente e difícil de depurar:
boolean hasUpper = password.matches(".*[A-Z].*");
boolean hasLower = password.matches(".*[a-z].*");
boolean hasDigit = password.matches(".*[0-9].*");
boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?].*");
```

Esse padrão possui severas desvantagens:
1. **Múltiplas Varreduras:** Cada chamada a `matches()` compila a expressão e percorre a string inteira novamente do início ao fim (4 a 5 passagens completas na memória).
2. **Custo de Alocação:** A cada chamada, a JVM cria objetos internos do compilador de regex (`Pattern` e `Matcher`), gerando lixo no Heap.

A abordagem implementada percorre a cadeia de caracteres **uma única vez** (`for (int i = 0; i < password.length(); i++)`). Durante essa única passada, todas as métricas são apuradas simultaneamente em tempo linear estrito **$O(N)$** e com **$O(1)$** de memória adicional.

### 2.2. O Poder da Classe `java.lang.Character`

Em vez de comparar códigos numéricos da tabela ASCII manualmente (`c >= 'a' && c <= 'z'`), o código utiliza os métodos estáticos da classe `java.lang.Character`:

- `Character.isUpperCase(c)`
- `Character.isLowerCase(c)`
- `Character.isDigit(c)`
- `Character.isWhitespace(c)`

Além de tornar o código imensamente mais legível e idiomático, essa abordagem respeita as tabelas oficiais do padrão **Unicode**, tratando adequadamente caracteres internacionais e acentuados caso a política da organização venha a expandir.

### 2.3. Modelagem com `record` e Imutabilidade

O resultado da validação é retornado em um `record PasswordValidationResult`.  
Isso ilustra para os estudantes uma das adições mais importantes do Java moderno:
- Elimina código *boilerplate* (getters, `equals`, `hashCode`, `toString`).
- Garante semântica de **Portador de Dados Imutável** (*Data Carrier*): uma vez auditada, a análise não pode ser acidentalmente corrompida por efeitos colaterais de outros métodos.
- Permite que a camada de visualização (ex: interface web ou aplicativo móvel) destaque em tempo real para o usuário exatamente qual regra falta cumprir (ex: o ícone de "Mínimo 8 caracteres" fica verde enquanto "Falta caractere especial" permanece cinza).

# ex09 — Validador de Senhas Fortes

> **Módulo:** 01 — Java Básico  
> **Tipologia:** Service / Segurança da Informação & Validação de Strings  
> **Dificuldade:** ⭐⭐☆ (Intermediário Iniciante)  
> **Conceitos:** Inspeção de `String`, Métodos Utilitários de `Character`, Tratamento Defensivo de `null`, Enumerações (`enum`) e Modelagem de Relatórios Imutáveis (`record`)  

## 1. Contexto & Cenário

No desenvolvimento de sistemas web, serviços de autenticação e plataformas bancárias, a política de senhas é a primeira linha de defesa contra ataques de força bruta (*brute force*) e dicionário (*dictionary attacks*). Uma senha vulnerável expõe dados sensíveis e compromete a segurança de toda a aplicação.

Sua tarefa é desenvolver a classe utilitária `PasswordValidator`, capaz de auditar rigorosamente senhas fornecidas por usuários, inspecionar cada caractere individualmente, avaliar requisitos de complexidade e classificar a força da credencial em um relatório detalhado.

## 2. Objetivos de Aprendizagem

- Manipular `String` em baixo nível utilizando os métodos `length()` e `charAt(int index)`.
- Utilizar a classe utilitária `java.lang.Character` para inspecionar categorias de caracteres (`isUpperCase`, `isLowerCase`, `isDigit`, `isWhitespace`).
- Implementar verificação de caracteres especiais a partir de um conjunto delimitado de pontuações de segurança.
- Tratar entradas `null` e strings vazias de forma elegante e defensiva, sem lançar `NullPointerException`.
- Retornar um relatório estruturado de conformidade utilizando `record` do Java moderno.
- Classificar a pontuação da credencial utilizando o `enum PasswordStrength`.

## 3. Regras de Negócio & Critérios de Validação

### 3.1. Requisitos de Segurança para Validação

Uma senha é considerada **válida** (`isValid == true`) se, e somente se, satisfizer **todos** os seguintes requisitos simultaneamente:

1. **Comprimento Mínimo:** Possuir no mínimo **8 caracteres**.
2. **Letra Maiúscula:** Conter pelo menos uma letra maiúscula (`A-Z`).
3. **Letra Minúscula:** Conter pelo menos uma letra minúscula (`a-z`).
4. **Dígito Numérico:** Conter pelo menos um número (`0-9`).
5. **Caractere Especial:** Conter pelo menos um caractere especial do conjunto:
   ```text
   !@#$%^&*()-_=+[]{}|;:,.<>?
   ```
6. **Ausência de Espaços:** **Não pode conter** espaços em branco em nenhuma posição (incluindo espaços, quebras de linha ou tabulações — detectados por `Character.isWhitespace`).

Se a senha for `null` ou vazia, ela deve ser tratada como inválida, com todas as flags de atendimento marcadas como `false`.

---

### 3.2. Classificação de Força da Senha (`PasswordStrength`)

A força da senha deve ser classificada de acordo com a pontuação e os critérios abaixo:

- **`FRACA`**:
  - A senha contém espaços em branco; **OU**
  - Possui comprimento menor que 8 caracteres; **OU**
  - Atende a menos de 4 dos 5 requisitos de complexidade.
- **`MEDIA`**:
  - Não possui espaços em branco, tem pelo menos 8 caracteres e atende a exatamente 4 dos 5 requisitos de complexidade.
- **`FORTE`**:
  - Não possui espaços em branco, tem pelo menos 8 caracteres e atende a **todos os 5 requisitos** de complexidade.
- **`MUITO_FORTE`**:
  - Atende a todos os critérios da categoria `FORTE`, possui comprimento de no mínimo **12 caracteres**, além de conter **pelo menos 2 dígitos** e **pelo menos 2 caracteres especiais**.

---

### 3.3. O Relatório de Validação (`PasswordValidationResult`)

O relatório emitido pelo método `validate(String password)` é um `record` que resume o diagnóstico:

```java
public record PasswordValidationResult(
    boolean isValid,
    boolean hasMinLength,
    boolean hasUpperCase,
    boolean hasLowerCase,
    boolean hasDigit,
    boolean hasSpecialChar,
    boolean hasNoWhitespace,
    PasswordStrength strength
) {}
```

## 4. Estrutura da Classe & Especificação dos Métodos

Pertence ao pacote `br.com.fatec.basic.ex09`:

### 4.1. `PasswordStrength` (Enum)

```java
public enum PasswordStrength {
    FRACA,
    MEDIA,
    FORTE,
    MUITO_FORTE
}
```

### 4.2. `PasswordValidator` (Classe Utilitária)

```java
public final class PasswordValidator {
    public static final int MIN_LENGTH = 8;
    public static final int VERY_STRONG_MIN_LENGTH = 12;
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    // Construtor privado
    private PasswordValidator() {}

    public static PasswordValidationResult validate(String password)
    public static boolean isValid(String password)
    public static PasswordStrength estimateStrength(String password)
}
```

- `validate(String password)`: executa a varredura completa caractere a caractere e monta o relatório `PasswordValidationResult`.
- `isValid(String password)`: método de conveniência que retorna diretamente `validate(password).isValid()`.
- `estimateStrength(String password)`: método de conveniência que retorna diretamente `validate(password).strength()`.

## 5. O que Você Deve Fazer

1. Abra os arquivos no pacote `br.com.fatec.basic.ex09`.
2. Inspecione o enum `PasswordStrength` e o record `PasswordValidationResult`.
3. Abra a classe `PasswordValidator.java` e implemente o algoritmo de validação:
   - Trate `null` e strings vazias no início.
   - Percorra a string com um laço `for (int i = 0; i < password.length(); i++)`.
   - Inspecione cada caractere com `password.charAt(i)` e os métodos da classe `Character`.
   - Conte a ocorrência de dígitos e caracteres especiais para apoiar a classificação `MUITO_FORTE`.
   - Monte e retorne o resultado.
4. Execute a suíte de testes:
   ```bash
   ./mvnw test -pl :ex09-validador-senha-forte
   ```

---

> ### 💡 Dica de Engenharia: Inspeção Manual vs Expressões Regulares (Regex)
>
> Embora seja possível validar senhas com Regex (`^(?=.*[a-z])(?=.*[A-Z])...`), a inspeção manual por laço iterativo oferece vantagens cruciais:
> - **Performance:** Percorre a string **uma única vez** em tempo $O(N)$, sem a sobrecarga do compilador de autômatos de expressões regulares.
> - **Diagnóstico Claro:** Permite identificar com exatidão **qual requisito específico falhou**, possibilitando exibir mensagens amigáveis na interface do usuário (ex: *"Falta uma letra maiúscula"*).

## 6. Critérios de Aceite

- Todos os testes da classe `PasswordValidatorTest` devem passar com sucesso (`BUILD SUCCESS`).
- Strings nulas e vazias devem ser tratadas de forma segura, resultando em senhas inválidas com força `FRACA` sem disparar `NullPointerException`.
- Todos os 6 critérios de validação devem ser auditados com precisão.
- A classificação de força (`FRACA`, `MEDIA`, `FORTE`, `MUITO_FORTE`) deve respeitar rigorosamente as faixas definidas.

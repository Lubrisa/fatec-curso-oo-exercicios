# [ID] — [Nome do Exercício em Português]

> **Módulo:** 02 — Orientação a Objetos  
> **Tipologia:** Design  
> **Dificuldade:** ⭐⭐☆ (Intermediário)  
> **Conceitos:** [Ex: Encapsulamento, Invariantes, Exceções de Domínio]

## 1. Contexto & Cenário

[Apresentação sucinta de um cenário real de negócio onde o modelo de domínio será utilizado, contextualizando a necessidade das regras.]

## 2. Objetivos de Aprendizagem

- [Objetivo conceitual 1, ex: Modelar entidades com integridade de estado garantida na construção]
- [Objetivo conceitual 2, ex: Proteger coleções internas contra mutações externas]
- [Objetivo conceitual 3, ex: Lançar exceções de domínio padronizadas para cenários inválidos]

## 3. Regras de Negócio & Invariantes

1. [Descrição da regra de negócio 1]:

   ```java
   /**
    * Representation of a customer profile.
    *
    * @param id the unique customer identifier
    * @param name the customer's full name
    */
   public record Customer(String id, String name) {}
   ```

2. [Descrição da regra de negócio 2]:

   - [Sub-regra ou caso de borda]
   - [Condição de falha e exceção esperada]

3. [Descrição da regra de negócio 3 com invariantes de cálculo ou limites operacionais].

## 4. O que Deve Ser Entregue

- Pacote base: `br.com.fatec.oo.[id]`
- Código de produção sob `src/main/java`.
- Toda a API pública (`public class`, `public interface`, métodos e construtores públicos) devidamente documentada com Javadoc (`@param`, `@return`, `@throws`).

## 5. Critérios de Aceite

- O código deve compilar sem warnings críticos de tipagem.
- A suíte de testes automatizados presente em `src/test/java` deve ser executada com 100% de sucesso.

# [ID] — [Nome do Exercício em Português]

> **Módulo:** 02 — Orientação a Objetos  
> **Tipologia:** Refatoração  
> **Dificuldade:** ⭐⭐⭐ (Avançado)  
> **Conceitos:** [Ex: Substituição de Condicionais por Polimorfismo, Coesão, Princípio Aberto/Fechado]

## 1. Contexto & Cenário

[Apresentação de um serviço existente que funciona corretamente e possui cobertura de testes, mas cujo código-fonte acumulou dívida técnica e tornou-se rígido, frágil e difícil de estender.]

## 2. Dívida Técnica Identificada (Code Smells)

A análise estática e a revisão de código apontaram os seguintes problemas arquiteturais no código atual:

- **[Nome do Smell 1, ex: Switch Statements / Condicionais Complexas]:** [Descrição do odor no código, ex: múltiplos blocos condicionais avaliando o mesmo tipo discriminador espalhados pelo serviço].
- **[Nome do Smell 2, ex: Primitive Obsession]:** [Descrição, ex: uso excessivo de tipos primitivos e strings para representar conceitos complexos de negócio].
- **[Nome do Smell 3, ex: Long Method / Feature Envy]:** [Descrição, ex: métodos que inspecionam o estado interno de outras classes para tomar decisões que deveriam pertencer a elas].

## 3. Diretrizes de Design & Princípios a Aplicar

Seu objetivo nesta refatoração é elevar o nível de design da solução orientada a objetos, aplicando as seguintes diretrizes:

1. [Princípio a aplicar 1, ex: Aplicar o Princípio Aberto/Fechado (OCP) substituindo decisões baseadas em tipos por contratos polimórficos].
2. [Princípio a aplicar 2, ex: Encapsular dados e comportamentos relacionados em objetos de domínio coesos].
3. [Princípio a aplicar 3, ex: Favorecer composição sobre herança onde a flexibilidade dinâmica for exigida].

> **Nota:** As diretrizes acima indicam os princípios e metas de design esperados, mas a decisão de arquitetura exata de classes, interfaces e colaboradores é sua responsabilidade de modelagem.

## 4. Restrições da Refatoração

- O comportamento observável do sistema deve ser rigorosamente preservado.
- Todas as novas classes, interfaces e métodos públicos devem ser nomeados em inglês e documentados com Javadoc (`@param`, `@return`, `@throws`).
- A suíte de testes existente deve continuar passando do início ao fim sem qualquer modificação no código de teste.

## 5. Critérios de Aceite

- Eliminação demonstrada dos code smells apontados.
- 100% dos testes da suíte automatizada executando com sucesso.
- Código limpo, idiomático e com responsabilidades bem distribuídas.

# [ID] — [Nome do Exercício em Português]

> **Módulo:** 02 — Orientação a Objetos  
> **Tipologia:** Bugfix  
> **Dificuldade:** ⭐⭐☆ (Intermediário)  
> **Conceitos:** [Ex: Contrato equals/hashCode, Vazamento de Referência, Mutabilidade Acidental]

## 1. Contexto & Cenário

[Breve contextualização de um sistema em produção que vinha operando normalmente, mas começou a apresentar anomalias após a inclusão de uma nova funcionalidade ou volume de dados.]

## 2. Sintoma Reportado (O Incidente)

[Descrição do problema sob a ótica do usuário ou cliente da API, sem entregar a causa raiz]:

- **Comportamento observado:** [Ex: Itens adicionados a uma coleção não são encontrados posteriormente em buscas por chave, gerando inconsistências no estoque].
- **Impacto:** [Ex: Clientes não conseguem visualizar pedidos recém-gerados e relatórios apresentam contagens incorretas].
- **Falha nos testes:** A suíte de testes existente acusa falhas em cenários que antes eram considerados estáveis.

## 3. Comportamento Esperado

1. [Especificação exata do comportamento esperado segundo o contrato de negócio].
2. [Preservação da consistência dos dados após múltiplas operações].
3. [Manutenção das garantias de imutabilidade ou unicidade esperadas pela API].

## 4. Ponto de Partida para Investigação

- Pacote base: `br.com.fatec.oo.[id]`
- Classes envolvidas no incidente sob `src/main/java`.
- Analise com atenção as mensagens de erro emitidas pela execução da suíte de testes sob `src/test/java`.
- Investigue a interação entre as estruturas de dados utilizadas e o ciclo de vida dos objetos manipulados.

> **Atenção:** Seu papel como engenheiro de software é diagnosticar a causa raiz, corrigir o código de produção mantendo a API pública em inglês e devidamente documentada com Javadoc, sem enfraquecer nem alterar os testes existentes.

## 5. Critérios de Aceite

- O bug deve ser corrigido no código de produção sem alterar a assinatura dos métodos públicos esperados.
- Toda a suíte de testes automatizados fornecida deve passar com sucesso.
- O código corrigido não deve introduzir regressões ou efeitos colaterais.

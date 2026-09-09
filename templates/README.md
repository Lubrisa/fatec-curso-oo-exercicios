# Templates Padrão de Exercícios

Este diretório formaliza os padrões de documentação (`README.md`) para cada tipologia de exercício do curso de Orientação a Objetos (FATEC).

## Diretrizes Gerais Obrigatórias para Todo Exercício

1. **Código em Inglês:**
   - Todos os identificadores de código (nomes de classes, interfaces, records, métodos, variáveis, pacotes e exceções) devem ser estritamente em **inglês**.
   - Comentários e mensagens de exceção podem ser em português se agregarem valor didático.

2. **Documentação da API Pública (Javadoc):**
   - Todas as classes, interfaces, records, enums e métodos/construtores `public` devem conter comentários no padrão Javadoc (`/** ... */`).
   - Tags essenciais a incluir quando aplicável: `@param`, `@return`, `@throws`.

3. **Estilo Markdown:**
   - Não utilizar linhas horizontais divisórias (`---`) entre seções.
   - Deixar uma quebra de linha limpa entre os títulos e os blocos de texto subsequentes.
   - Blocos de código que pertencem a itens de lista devem possuir uma linha em branco antes e depois do bloco, mantendo o recuo alinhado ao item.

4. **Desacoplamento de Instruções Operacionais:**
   - Informações repetitivas de como rodar o Maven Wrapper (`./mvnw test`), proibição de editar `src/test/` e uso da pasta `base/` pertencem exclusivamente ao `README.md` raiz. O README do exercício deve ser conciso e focado no problema.

## Tipologias Disponíveis

- [Template de Design](./template-design.md): Criação de modelos e classes a partir de requisitos de domínio.
- [Template de Bugfix](./template-bugfix.md): Diagnóstico e correção de falhas sem dar spoiler da causa raiz.
- [Template de Refatoração](./template-refactoring.md): Melhoria de código funcional com code smells, indicando o que alcançar sem prescrever o como.
- [Template de Sandbox / Laboratório](./template-sandbox.md): Roteiro de experimentos com preenchimento de relatório de observações sobre a JVM.
- [Template de Service / Algorítmico](./template-service-algorithmic.md): Pipelines de dados, algoritmos clássicos e manipulação de coleções.

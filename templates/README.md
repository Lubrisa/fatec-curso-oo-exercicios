# Templates Padrão de Exercícios

Este diretório formaliza os padrões de documentação (`README.md`) para cada tipologia de exercício do curso de Orientação a Objetos (FATEC).

## Diretrizes Gerais Obrigatórias para Todo Exercício

1. **Código em Inglês (Identificadores):**
   - Todos os identificadores de código (nomes de classes, interfaces, records, métodos, variáveis, parâmetros, constantes, pacotes e exceções) devem ser estritamente em **inglês**.

2. **Documentação e Comentários em Português:**
   - Todos os comentários de código (tanto comentários explicativos quanto documentais Javadoc `/** ... */`) devem ser escritos em **português**.
   - As tags Javadoc (`@param`, `@return`, `@throws`) devem explicar os contratos claramente em português.
   - Mensagens de exceção de domínio e descrições de testes (`@DisplayName`) também devem ser em português para facilitar a leitura e o aprendizado dos alunos.

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

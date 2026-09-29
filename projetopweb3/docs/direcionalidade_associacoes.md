# Direcionalidade e propriedade das associações

Este documento registra as direções de navegação implementadas no modelo. A seta `A -> B` significa que a classe `A` possui uma referência ou coleção de `B`. A seta dupla `A <-> B` significa que a navegação está implementada nos dois sentidos.

O lado proprietário é o lado que controla a chave estrangeira ou a tabela de junção. Em associações bidirecionais, `mappedBy` identifica o lado inverso e não cria uma segunda associação no banco.

## Visão geral

```text
Caverna <-> Setor

Expedicao -> Caverna
Expedicao -> Setor
Expedicao <-> PlanoSeguranca

AutorizacaoAmbiental -> Expedicao
RelatorioFinal -> Expedicao

Participacao -> Expedicao
Participacao -> Pessoa

MovimentacaoEquipamento -> Expedicao
MovimentacaoEquipamento -> Equipamento
MovimentacaoEquipamento -> Pessoa

Coleta -> Expedicao
Coleta -> Setor
Coleta -> Pesquisador
Coleta <-> Amostra

Pesquisador --|> Pessoa
GuiaEspeleologia --|> Pessoa
```

## Associações bidirecionais

### Caverna e Setor

```text
Caverna <-> Setor
```

- `Caverna.setores` permite navegar da caverna para seus setores.
- `Setor.caverna` permite navegar do setor para sua caverna.
- Cardinalidade: uma `Caverna` possui zero ou muitos `Setor`; cada `Setor` pertence a exatamente uma `Caverna`.
- Proprietário: `Setor`, porque contém `@JoinColumn(name = "caverna_id")`.
- Lado inverso: `Caverna.setores`, definido com `mappedBy = "caverna"`.

### Expedicao e PlanoSeguranca

```text
Expedicao <-> PlanoSeguranca
```

- `Expedicao.planoSeguranca` permite acessar o plano da expedição.
- `PlanoSeguranca.expedicao` permite acessar a expedição correspondente.
- Cardinalidade: uma `Expedicao` possui exatamente um `PlanoSeguranca`; cada plano pertence a exatamente uma expedição.
- Proprietário: `PlanoSeguranca`, porque contém a coluna `expedicao_id` com restrição de unicidade.
- Lado inverso: `Expedicao.planoSeguranca`, definido com `mappedBy = "expedicao"`.

### Coleta e Amostra

```text
Coleta <-> Amostra
```

- `Coleta.amostras` permite acessar as amostras produzidas pela coleta.
- `Amostra.coleta` permite acessar a coleta de origem.
- Cardinalidade: uma `Coleta` possui zero ou muitas `Amostra`; cada amostra pertence a exatamente uma coleta.
- Proprietário: `Amostra`, porque contém `@JoinColumn(name = "coleta_id")`.
- Lado inverso: `Coleta.amostras`, definido com `mappedBy = "coleta"`.

## Associações unidirecionais

### Expedicao

```text
Expedicao -> Caverna
Expedicao -> Setor
```

- `Expedicao.caverna`: muitas expedições podem ocorrer em uma caverna. `Expedicao` é proprietária por conter `caverna_id`.
- `Expedicao.setores`: uma expedição pode abranger muitos setores e um setor pode participar de muitas expedições. `Expedicao` é proprietária da associação muitos-para-muitos e controla a tabela `expedicao_setor`.
- Não existem `Caverna.expedicoes` nem `Setor.expedicoes`, evitando coleções que não são necessárias aos casos de uso atuais.

### AutorizacaoAmbiental e RelatorioFinal

```text
AutorizacaoAmbiental -> Expedicao
RelatorioFinal -> Expedicao
```

- Cada entidade referencia exatamente uma expedição.
- `AutorizacaoAmbiental` e `RelatorioFinal` são proprietárias de suas associações porque cada uma contém uma coluna `expedicao_id` única.
- A navegação inversa não foi adicionada a `Expedicao`, pois as consultas específicas permitem obter autorização e relatório quando necessários, sem aumentar o estado sempre acessível da expedição.

### Participacao

```text
Participacao -> Expedicao
Participacao -> Pessoa
```

- `Participacao` é a entidade associativa entre `Expedicao` e `Pessoa` e armazena dados próprios, como papel, diária e presença.
- Ela é proprietária das duas associações porque contém `expedicao_id` e `pessoa_id`.
- A combinação dessas duas colunas é única, impedindo a mesma pessoa de ser cadastrada duas vezes na mesma expedição.
- Não existem coleções inversas em `Expedicao` e `Pessoa`; os participantes são carregados por consulta específica somente quando necessários.

### MovimentacaoEquipamento

```text
MovimentacaoEquipamento -> Expedicao
MovimentacaoEquipamento -> Equipamento
MovimentacaoEquipamento -> Pessoa
```

- A movimentação referencia a expedição, o equipamento e a pessoa responsável.
- `MovimentacaoEquipamento` é proprietária das três associações porque contém as respectivas chaves estrangeiras.
- Não há coleções inversas, pois movimentações formam um histórico potencialmente grande e devem ser consultadas conforme a necessidade.

### Coleta

```text
Coleta -> Expedicao
Coleta -> Setor
Coleta -> Pesquisador
```

- Cada coleta referencia exatamente uma expedição, um setor e um pesquisador responsável.
- `Coleta` é proprietária dessas associações porque contém as três chaves estrangeiras.
- Não há coleções inversas em `Expedicao`, `Setor` ou `Pesquisador`; as coletas são obtidas por consultas específicas, reduzindo o risco de carregamento excessivo.

## Entidades sem associações próprias

```text
Equipamento
Pessoa
Pesquisador
GuiaEspeleologia
```

- `Equipamento` não mantém uma coleção de movimentações.
- `Pessoa` não mantém coleções de participações ou movimentações.
- `Pesquisador` herda de `Pessoa` e não mantém uma coleção de coletas.
- `GuiaEspeleologia` herda de `Pessoa` e não declara associações.
- `Endereco` e `Localizacao` são tipos incorporáveis, não entidades associadas. Seus dados são persistidos diretamente nas tabelas de `Pessoa` e `Caverna`, respectivamente.

## Justificativa

A bidirecionalidade foi aplicada somente nas composições em que a navegação nos dois sentidos é natural e útil: caverna/setores, expedição/plano de segurança e coleta/amostras. As relações históricas ou acessadas apenas em consultas específicas permanecem unidirecionais. Essa escolha reduz coleções desnecessárias, simplifica a sincronização do modelo e ajuda a evitar carregamento acidental e consultas N+1.

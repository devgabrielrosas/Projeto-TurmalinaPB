# Relatório de Justificativa das Decisões Tomadas no Projeto

## Herança
    A herança presente no projeto é a de Pessoa que é herdada para Pesquisador e Guia de Espeleologia e JOINED foi a estratégia utilizada para mapeá-la de objeto java para o banco relacional; nessa estratégia todas as classes, concretas ou abstratas, possuem uma tabela e os IDs das subclasses são utilizados como PKs e FKs para a superclasse. Essa estratégia foi utilizada, pois se criássemos apenas uma tabela incluindo todos os dados haveriam muitos campos nulos, os quais precisam ser tratados em níveis mais baixos da aplicação e possuem certo custo. Além disso, não foi criada apenas entidades para as subclasses porque Pessoa (superclasse) possui muitos campos/atributos, logo seriam repetidos em cada uma das subclasses.

## Ownership das Associações
    O ownership das associações foi definido de acordo com a localização da chave estrangeira no banco relacional. Dessa forma, Setor e Expedicao são proprietários de suas associações com Caverna; PlanoSeguranca, AutorizacaoAmbiental e RelatorioFinal são proprietários das associações com Expedicao; Participacao é proprietária das associações com Expedicao e Pessoa; MovimentacaoEquipamento é proprietária das associações com Expedicao, Equipamento e Pessoa; Coleta é proprietária das associações com Expedicao, Setor e Pesquisador; e Amostra é proprietária da associação com Coleta. Na associação muitos-para-muitos entre Expedicao e Setor, Expedicao foi definida como proprietária e controla a tabela de junção expedicao_setor. Nas associações bidirecionais, o lado inverso utiliza mappedBy para indicar que não controla a chave estrangeira e evitar a criação de relacionamentos duplicados.

## Cascatas

    Cascatas foram aplicadas apenas onde a entidade dependente tem o ciclo de vida controlado pela principal: CascadeType.ALL de Caverna para Setor, de Expedicao para PlanoSeguranca e de Coleta para Amostra. Nessas três associações a parte não existe sem o todo. As demais associações não usam cascata porque relacionam entidades com identidade própria, compartilhadas ou históricas (Participacao, Coleta e MovimentacaoEquipamento, por exemplo, são persistidas de forma independente, e Expedicao não mantém coleções delas).

## Orphan Removal

    O orphanRemoval foi aplicado nas mesmas três composições (Caverna-Setor, Expedicao-PlanoSeguranca e Coleta-Amostra), pois, ao ser retirada da associação, a entidade dependente deve ser excluída do banco. Nas demais associações, retirar uma referência não deve excluir a entidade relacionada.

## Fetch
    O FetchType.LAZY foi utilizado em todas as associações para evitar o carregamento automático extenso de objetos, pois uma expedição pode possuir setores, participantes, plano de segurança, coletas e amostras, além de se relacionar com pessoas, equipamentos e documentos. Dessa forma, cada relacionamento é carregado apenas quando necessário. Nas consultas de listagem, devem ser utilizadas projeções contendo somente os dados exibidos; nas consultas de detalhes, os relacionamentos necessários devem ser obtidos por meio de fetch join, evitando o problema N+1. Os arquivos binários também foram configurados com carregamento tardio e devem ser recuperados por consultas específicas de download, impedindo que sejam carregados em consultas comuns.

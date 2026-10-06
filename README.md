# TurmalinaPB

Projeto Java para gestão de cavernas e expedições, com JPA/Hibernate e PostgreSQL.

## Comandos (PowerShell)

Execute na pasta do projeto:

```powershell
cd projetopweb3

# Compilar
mvn clean compile

# Popular o banco
mvn compile exec:java "-Dexec.mainClass=turmalina.pweb3.config.CreateInstances"

# Executar as consultas de demonstração
mvn compile exec:java "-Dexec.mainClass=turmalina.pweb3.config.TestNamedQueries"

# Executar Main (Hello world)
mvn exec:java

# Gerar o JAR
mvn package
```

A carga inicial verifica se já existe para evitar duplicação. O Hibernate atualiza as tabelas automaticamente.

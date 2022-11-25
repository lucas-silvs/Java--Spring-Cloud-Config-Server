# Java--Spring-Cloud-Config-Server

projeto para criação de um servidor de configuração centralizada Spring Cloud Config

## Caminho para busca de Configurações

O Caminho padrão para buscar as configurações é da seguinte forma:

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

- application: nome da aplicação. Ex: projeto--teste

- profile: súfixo que representa o ambiente da configuração. Ex: Development, Staging, Production

- label: é possivel selecionar uma branch especifica para pegar as configurações. Ex: feature_branch_teste/{application}--{profile}.yaml

Um exemplo de consulta de configuração no Config Server:

```
<<ambiente Config Server>>/projeto-teste/Development
```

## Referencia:

[Spring-Cloud-Config]("https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_spring_cloud_config_server")

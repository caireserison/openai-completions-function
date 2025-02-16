## Sobre a aplicação:
Trata-se de uma aplicação de exemplo que utiliza a API Completions da OpenAI para gerar conversação em texto interagindo de forma humanizada e fluida (outros parâmetros limitadores podem ser acrescentados. Veja em https://platform.openai.com/docs/guides/completions). 

É utilizada a opção Functions da OpenAI Completions, onde é possível criar uma função que permite interagir com APIs externas nos momentos certos da conversa.

Para essa aplicação de demonstração foi utilizada a API HGWeather, que retorna informações sobre o clima de uma cidade e pode ser substituída por APIs que acessam o banco de dados para seu contexto, como dados do cliente por exemplo.

A aplicação foi desenvolvida em Java 21 com o framework SpringBoot 3.2, sendo possível a utilização do RestClient, interface nativa para requisições RESTful. Para documentação e facilidade de teste, foi utilizado Swagger.

###### Instalando Java:
https://www.oracle.com/java/technologies/downloads/#java21

###### Instalando Maven:
` choco install maven `

###### Adicionando dependência:
````
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>6.2.1</version>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.7.0</version>
</dependency>
````

###### Executando:
` mvn package `

` java -jar openai.completions.function-1.0-SNAPSHOT.jar `

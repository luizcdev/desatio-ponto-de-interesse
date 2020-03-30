## Desafio Ponto de Interesse

### Sobre
Api desenvolvida para cadastro e pesquisa de Pontos de Interesse baseado em sua localização.
<br><br>
Os métodos disponíveis na Api, permitem cadastrar pontos de interesse com sua localização e horário de funcionamento, listar todos os pontos de interesse cadastrados e listar apenas pontos de interesse a uma distância desejada, exibindo ainda se estão abertos ou fechados.
<br>
Para a listagem dos pontos próximos, é utilizado o método de [distância euclidiana](https://pt.wikipedia.org/wiki/Dist%C3%A2ncia_euclidiana).
<br><br>
A Api conta com teste unitário e de integração para garantir o funcionamento correto das funcionalidades.

### Requisitos
 - [Docker](https://docs.docker.com/)

### Principais Tecnologias Utilizadas
 - [Java](https://www.java.com/pt_BR/)
 - [Spring Boot](https://spring.io/projects/spring-boot)
 - [JUnit](https://junit.org/junit5/)
 - [PostgreSQL](https://www.postgresql.org/)
 - [H2 Database](https://www.h2database.com/html/main.html)
 - [Swagger](https://swagger.io/)

### Tutorial de Instalação
  1) Baixe o projeto e descompacte em uma pasta de sua preferência
  2) Acesse a pasta do projeto
  3) Rode o seguinte comando: ```docker-compose up```
     - Esse processo irá compilar o projeto, rodar os testes, gerar o pacote e criar o banco de dados, por isso, é normal que demore alguns minutos
  4) Após finalizar todo o processo, o sistema estará disponível para uso
  
### Documentação
  - Disponível através do link ```http://localhost:8080/swagger-ui.html```

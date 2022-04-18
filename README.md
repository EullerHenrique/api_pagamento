# Api De Pagamento

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Postgresql
- Swagger
- Docker
- Junit
- Mockito
- Hamcrest

## Execução

  1. Clone o repósitorio e abra o em uma IDE (Ex: Intellij)
  2. Instale o docker (https://www.docker.com/products/docker-desktop/)
  3. Navegue pelo terminal até src/main/resources ou navegue pela IDE até src/main/resources/docker-compose.yaml
  4. Execute o comando docker-compose up -d ou aperte o botão play localizado ao lado do campo services
  5. Navegue pela IDE até ApiPagamentoApplication 
  6. Aperte o botão play localizado ao lado de "public class ApiPagamentoApplication"
  7. Acesse o swagger (http://localhost:8080/swagger-ui/index.html) ou realize as requisições por meio do postman

## EndPoints
  
  ### localhost:8080/transacao/v1/pagamento
  
  Request:
  
  ```
  { 
   "cartao": "4444********1234",
   "descricao": {
      "valor": "500.50",
      "dataHora": "01/05/2021 18",
      "estabelecimento": "PetShop Mundo cão"
    },
    "formaPagamento":{
        "tipo":"AVISTA",
        "parcelas": "1"
    }
  }
  ```

  Response:
  
  ```
  {
    "id": 1,
    "cartao": "4444********1234",
    "descricao": {
        "valor": "500.50",
        "dataHora": "01/05/2021 18",
        "estabelecimento": "PetShop Mundo cão",
        "nsu": "1234567890",
        "codigoAutorizacao": "147258369",
        "status": "AUTORIZADO"
    },
    "formaPagamento": {
        "tipo": "AVISTA",
        "parcelas": "1"
    }
  }
  ```
  
  
  - localhost:8080/transacao/v1/estorno/1
  
  - localhost:8080/transacao/v1/1
  
  - localhost:8080/transacao/v1

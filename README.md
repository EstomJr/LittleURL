
# LittleURL 🔗

Este é um projeto de encurtador de URLs desenvolvido com Spring Boot, MongoDB e JUnit. O objetivo é fornecer um serviço RESTful que recebe URLs longas e gera códigos curtos, armazenando as informações no MongoDB.


## Funcionalidades

- **Encurtar URL:** Recebe uma URL longa e retorna uma URL encurtada
- **Redirecionamento:** Ao acessar a URL curta, redireciona para a URL original
- **Estatísticas:** Fornece informações como total de URLs ativas, expiradas e acessos



## 🛠️ Tecnologias Utilizadas

- **Spring Boot:** Framework para desenvolvimento Java
- **MongoDB:** Banco de dados não relacional
- **Maven:** Gerenciador de dependências
- **JUnit 5**: Framework para testes automatizados
- **Lombok**: Biblioteca para redução de código boilerplate

## 📋 Pré-requisitos

- Java 21
- Maven
- MongoDB (local ou em outro ambiente)


## 🚀 Como Executar

Clone o repositório:

```bash
  git clone https://github.com/seu-usuario/LittleURL.git
```
Entre no diretório:   

```bash
cd LittleURL
```

Configure o MongoDB em src/main/resources/application.properties:

```bash
MONGO_URL= (Passar configuração mongodb)
spring.data.mongodb.uri=${MONGO_URL:mongodb://localhost:27017/little_url}
spring.data.mongodb.database=little_url
```

Execute o projeto:

```bash
Run LittleUrlApplication
```
## 🔍 Endpoints

**Encurtar URL**
- Endpoint: POST /v1/shorten
- Descrição: Cria uma URL curta a partir de uma URL longa
- Corpo da Requisição:
```bash
{
    "originalUrl": "https://www.exemplo.com.br/"
}
```
**Response:**
```bash
{
    "message": "URL encurtada com sucesso!",
    "shortCode": "abc123",
    "shortUrl": "http://localhost:8080/abc123"
}
```

**Redirecionar para URL Original**
- Endpoint: GET /{shortCode}
- Descrição: Redireciona para a URL original
- Exemplo: GET http://localhost:8080/abc123
- Resposta: Redirecionamento HTTP 302 para a URL original

**Listar Todas as URLs**
- Endpoint: GET /v1/Listar
- Descrição: Retorna todas as URLs cadastradas
- Resposta:
```bash
[
    {
        "originalUrl": "https://www.exemplo.com.br",
        "shortCode": "abc123",
        "shortUrl": "http://localhost:8080/abc123",
        "expiresAt": "14/04/2024",
        "accessCount": 5,
        "createdAt": "14/03/2024",
        "updatedAt": "14/03/2024"
    }
]
```
**Estatísticas Globais**
- Endpoint: GET /v1/stats
- Descrição: Retorna estatísticas gerais do sistema
- Resposta:
```bash
{
    "totalShortenedUrls": 10,
    "totalRedirects": 8,
    "activeUrls": 8,
    "expiredUrls": 2,
    "activeUrlStats": [
        {
            "urlAccessCount": 3,
            "averageAccessesPerDay": 1.5,
            "urlCreatedAt": "14/03/2024",
            "shortCode": "abc123",
            "shortUrl": "http://localhost:8080/abc123"
        }
    ]
}
```
**Estatísticas de URL Específica**
- Endpoint: GET /v1/stats/{shortCode}
- Descrição: Retorna estatísticas de uma URL específica
- Exemplo: GET /v1/stats/abc123
- Resposta:
```bash
{
    "urlAccessCount": 3,
    "averageAccessesPerDay": 1.5,
    "urlCreatedAt": "14/03/2024",
    "shortCode": "abc123",
    "shortUrl": "http://localhost:8080/abc123"
}
```
## 📚 Documentação | Swagger

#### Para acessar a documentação completa da API:
- Inicie o projeto
- Acesse: http://localhost:8080/swagger-ui/index.html

## 📝 Detalhes Importantes

- As URLs encurtadas expiram após 30 dias
- O código curto (shortCode) tem 6 caracteres
- O sistema mantém contagem de acessos para cada URL
- Todas as datas são retornadas no formato dia/mês/ano


### 🔒 Tratamento de Erros
O sistema retorna os seguintes códigos HTTP:
- 200: Sucesso
- 302: Redirecionamento
- 404: URL não encontrada
- 400: URL expirada ou inválida
- 500: Erro interno do servidor

# Auth App - Quarkus + Angular

Aplicação web full stack com:

- backend em **Java + Quarkus**
- frontend em **Angular**
- autenticação simples com **JWT**
- cadastro de usuário
- login
- recuperação e **redefinição completa de senha**
- integração com **CEP**
- campo livre
- campo adicional via **Bottom Sheet**
- mensagem pós-login:
  **SUCESSO! VOCÊ ESTÁ LOGADO**

---

## 1. Objetivo do projeto

Este projeto foi construído como uma aplicação de demonstração end-to-end, com foco em:

- simplicidade
- boas práticas
- didática para iniciantes
- execução local fácil
- organização entre backend, frontend e documentação

---

## 2. Tecnologias utilizadas

### Backend
- Java 21
- Quarkus
- Hibernate ORM com Panache
- H2 Database
- JWT
- Bean Validation
- BCrypt

### Frontend
- Angular
- Angular Material
- Reactive Forms
- HttpClient
- Router
- Guards
- Interceptors

### Integrações
- ViaCEP para preenchimento automático do endereço

---

## 3. Estrutura do projeto

```text
meu-projeto/
  backend/
  frontend/
  README.md 
  ``` 

## 4. Como executar o projeto

- Clonar o projeto localmente: git clone
- Acessar a pasta backend para instalar as dependências(cd .\backend\), executar o comando: mvn clean install
- Ainda na pasta do backend, executar o comando para rodar o backend: mvn quarkus:dev
- Sair da pasta do backend(cd ..), e entrar na pasta do front(cd .\frontend\), executar o comando: npm install
- Ainda na pasta do front, executar o comando para rodar o front: ng serve

### Importante
- Instalar JDK 21
- Instalar mvn
- Instalar Node
- Instalar npm
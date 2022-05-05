# Ci&T :: API :: Pizzaria

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/renatomak/challenge-ciandt-pizzaria/blob/main/LICENSE)

API para gerenciamento de sabores das pizzas.

## :dart: Swagger

Link de acesso ao Swagger.

- [Swagger Local](http://localhost:8080/swagger-ui/#/)

## :dart: Requisitos para rodar a aplicação localmente

### Java

* [Java LTS 11](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html);

```
  Para verificar se seu IntelliJ IDE está rodando com a LTS 11 do Java, você pode acessar  `File > Project Structure` onde abrirá uma modal e você poderá verificar se está configurada a SDK 11 do Java e a language level do Java 11.
```

### Banco Local

Aplicação utiliza banco MySQL local. Recomendados o uso do Workbench: [MySQL WorkBench](https://www.mysql.com/products/workbench/)

Em seu workbench, você pode acessar o banco local criado com os seguintes acessos:

```
- Database: ciandt_db_pizzaria
- Port: 3306
- Username: root
- Password: toor

Observação: Você pode usar outros dados de usuário, altere os respectivos campos em application.properties
```


### IDE

Recomendamos o uso do [IntelliJ IDE](https://www.jetbrains.com/pt-br/idea/download/) como IDE utilizada.

### Plugins

Já com o [IntelliJ IDE](https://www.jetbrains.com/pt-br/idea/download/) instalado, devemos instalar também o [Plugin Lombok](https://plugins.jetbrains.com/plugin/6317-lombok), em seu própio IntelliJ você pode acessar o Marketplace de plugin na aba `Preferências > Plugin > Marketplace` pesquisando por "Lombok".

## :dart: Rodando a aplicação local

Para rodarmos a aplicação local pela primeira vez, devemos já possuir este repositório clonado e aberto em seu IDE.


### Maven
Para compilar a aplicação via comand line, deve-se rodar o comando abaixo.

```
mvn clean install -U
```
## Endpoints disponíveis:

- [x] POST: /flavors - Cria um novo sabor. É preciso informar os campos name, description, price;
- [x] GET: /flavors - Recupera todos os sabores cadastrados;
- [x] GET: /flavors/{id} - Recupera um sabor, conforme id informado;
- [x] PUT: /flavors/{id} - Atualiza um sabor, conforme id informado. É preciso informar os campos a serem alterados.
- [x] DELETE: /flavors/{id} - Remove um sabor, conforme id informado.

## Atributos
- name: Obrigatório. Deve ter no mínimo 3 caracteres e no máximo 100.
- description: Obrigatório. Deve ter no mínimo 3 caracteres e no máximo 240.
- price: Obrigatório. Não pode ser menor que 1.


## Testes no Postman
Faça o import do arquivo Ci&T-Pizzaria.postman_collection.json que está dentro da pasta resorces para executar os testes dos endpoints.


# Tecnologias utilizadas

<img src="https://cdn.icon-icons.com/icons2/3053/PNG/512/intellij_alt_macos_bigsur_icon_190060.png" alt="Intellij IDE" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://spring.io/images/logo-spring-tools-gear-3dbfa4e3714afa9d58885422ec7ac8e5.svg" alt="spring" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://cdn.icon-icons.com/icons2/2415/PNG/512/java_original_wordmark_logo_icon_146459.png" alt="java" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://cdn.icon-icons.com/icons2/3053/PNG/512/postman_macos_bigsur_icon_189815.png" alt="postman" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://cdn.icon-icons.com/icons2/2415/PNG/512/mysql_original_wordmark_logo_icon_146417.png" alt="MySQL" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://cdn.icon-icons.com/icons2/1381/PNG/512/mysqlworkbench_93532.png" alt="Workbench" width="40" height="40" style="max-width:100%;" /> &nbsp; &nbsp; &nbsp; &nbsp;

</br>
</br>

---
Projeto criado na fase de desafio técnico do processo seletivo para vaga de Desenvolvedor Junior BackEnd Java na CI&T. 


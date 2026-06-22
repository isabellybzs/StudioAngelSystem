# Evidencias do banco de dados H2 em funcionamento

## 1. Configuracao do banco no projeto

Arquivo: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:h2:file:./data/studioangel
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
server.port=8080
```

Essa configuracao mostra que o sistema usa banco H2 em arquivo, com console habilitado no navegador.

## 2. Acesso ao console H2

Com o sistema aberto, acessar no navegador:

```text
http://localhost:8080/h2-console
```

Dados de conexao:

```text
JDBC URL: jdbc:h2:file:./data/studioangel
User Name: sa
Password: deixar em branco
```

## 3. Arquivo fisico do banco

O banco foi criado na pasta do projeto:

```text
data/studioangel.mv.db
```

Isso confirma que os dados nao ficam apenas na tela. Eles sao gravados em um arquivo de banco H2.

## 4. Tabelas existentes no banco

Consulta executada:

```sql
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME;
```

Resultado:

```text
TABLE_NAME
AGENDAMENTO
CLIENTE
PROFISSIONAL
SERVICO
```

## 5. Quantidade de registros salvos

Consulta:

```sql
SELECT COUNT(*) AS TOTAL_CLIENTES FROM CLIENTE;
```

Resultado:

```text
TOTAL_CLIENTES
3
```

Consulta:

```sql
SELECT COUNT(*) AS TOTAL_SERVICOS FROM SERVICO;
```

Resultado:

```text
TOTAL_SERVICOS
5
```

Consulta:

```sql
SELECT COUNT(*) AS TOTAL_PROFISSIONAIS FROM PROFISSIONAL;
```

Resultado:

```text
TOTAL_PROFISSIONAIS
3
```

Consulta:

```sql
SELECT COUNT(*) AS TOTAL_AGENDAMENTOS FROM AGENDAMENTO;
```

Resultado:

```text
TOTAL_AGENDAMENTOS
8
```

## 6. Exemplos de dados cadastrados

### Clientes

Consulta:

```sql
SELECT ID, NOME, TELEFONE, EMAIL
FROM CLIENTE
ORDER BY ID
LIMIT 3;
```

Resultado:

```text
ID | NOME           | TELEFONE        | EMAIL
1  | Ana Souza      | (11) 99999-1111 | ana@email.com
2  | Mariana Lima   | (11) 98888-2222 | mariana@email.com
3  | Beatriz Santos | (11) 97777-3333 | beatriz@email.com
```

### Servicos

Consulta:

```sql
SELECT ID, NOME, PRECO, DURACAO_MINUTOS
FROM SERVICO
ORDER BY ID
LIMIT 3;
```

Resultado:

```text
ID | NOME           | PRECO | DURACAO_MINUTOS
1  | Corte feminino | 85.00 | 60
2  | Escova         | 55.00 | 45
3  | Manicure       | 35.00 | 40
```

### Profissionais

Consulta:

```sql
SELECT ID, NOME, ESPECIALIDADE, ATIVO
FROM PROFISSIONAL
ORDER BY ID
LIMIT 3;
```

Resultado:

```text
ID | NOME     | ESPECIALIDADE       | ATIVO
1  | Camila   | Cabelos e escova    | TRUE
2  | Carolina | Corte e coloracao   | TRUE
3  | Vania    | Manicure e pedicure | TRUE
```

### Agendamentos

Consulta:

```sql
SELECT A.ID,
       C.NOME AS CLIENTE,
       S.NOME AS SERVICO,
       P.NOME AS PROFISSIONAL,
       A.DATA,
       A.HORARIO,
       A.STATUS
FROM AGENDAMENTO A
LEFT JOIN CLIENTE C ON A.CLIENTE_ID = C.ID
LEFT JOIN SERVICO S ON A.SERVICO_ID = S.ID
LEFT JOIN PROFISSIONAL P ON A.PROFISSIONAL_ID = P.ID
ORDER BY A.ID
LIMIT 5;
```

Resultado:

```text
ID | CLIENTE        | SERVICO               | PROFISSIONAL | DATA       | HORARIO  | STATUS
1  | Ana Souza      | Escova                | Camila       | 2026-06-16 | 09:00:00 | CONCLUIDO
2  | Ana Souza      | Manicure              | Camila       | 2026-06-21 | 14:00:00 | AGENDADO
3  | Mariana Lima   | Corte feminino        | Camila       | 2026-06-18 | 10:30:00 | CONCLUIDO
4  | Mariana Lima   | Escova                | Camila       | 2026-06-20 | 16:00:00 | AGENDADO
5  | Beatriz Santos | Design de sobrancelha | Camila       | 2026-06-17 | 11:00:00 | CANCELADO
```

## 7. Conclusao da evidencia

As consultas mostram que:

- O banco H2 esta configurado no projeto.
- O console H2 esta habilitado.
- O arquivo fisico do banco foi criado.
- Existem tabelas geradas pelo JPA.
- Existem dados salvos de clientes, servicos, profissionais e agendamentos.
- Os agendamentos estao relacionados com cliente, servico e profissional.

Com isso, o projeto atende ao requisito de demonstrar o banco de dados H2 em funcionamento.


# Conversor de Dados Multiformato (JSON, XML e YAML) 🔄

Aplicação desenvolvida em **Java 21** para captura dinâmica de dados estruturados via console no padrão `NOME_CAMPO;VALOR;TIPO;` e serialização automática para os três principais formatos de intercâmbio de dados do mercado: **JSON**, **XML** e **YAML**, sem a utilização de bibliotecas externas.

---

## 📌 Funcionalidades e Tipos de Dados Suportados

O programa lê $N$ entradas fornecidas pelo usuário em um laço contínuo até o comando de encerramento (`SAIR` ou linha em branco), realizando o parse das informações conforme os tipos declarados:

### 1. **Tipos de Dados Reconhecidos**

* **`texto` / `data` / `data e hora`:** Tratados como Strings e delimitados por aspas em JSON e YAML.
* **`inteiro` / `flutuante`:** Convertidos diretamente em valores numéricos literais (sem aspas em JSON/YAML).
* **`booleano`:** Convertidos para os literais `true` ou `false`.
* **`array`:** Processa listas separadas por vírgulas (ex: `Java, Python, SQL`), gerando a sintaxe de array apropriada em cada especificação:
* **JSON:** `[ "Java", "Python", "SQL" ]`
* **XML:** `<item>Java</item>` repetido dentro da tag pai.
* **YAML:** Lista indentada iniciada com hífens (`- Java`).



---

## 🏗️ Arquitetura do Projetos e Estrutura de Código

A aplicação utiliza o recurso de **Records** do Java para imutabilidade dos dados e métodos dedicados para o gerador de cada formato:

```text
               +----------------------------------+
               |          Record Campo            |
               | (nome, valor, tipo)              |
               +----------------------------------+
                                |
        +-----------------------+-----------------------+
        |                       |                       |
        v                       v                       v
  gerarJson()              gerarXml()              gerarYaml()
  (Aplica aspas,          (Gera estrutura         (Aplica indentação
  colchetes e chaves)     de tags e atributos)     e sintaxe YAML)

```

---

## 🧠 Conceitos Práticos Aplicados

* **Java Records (`record Campo(...)`):** Modelagem concisa e transparente de dados imutáveis sem necessidade de boilerplate de getters, setters e construtores.
* **Manipulação e Formatação de Strings (`StringBuilder`):** Construção eficiente e de alto desempenho para montagem de textos estruturados em memória.
* **Flexibilidade de Estruturas:** Tratamento de nomes de campos com espaços usando Regex (`replaceAll("\\s+", "_")`) para assegurar a criação de tags XML válidas.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21 (JDK 21)
* **IDE Recomendada:** IntelliJ IDEA
* **Versionamento:** Git & GitHub

---

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
```bash
git clone https://github.com/SEU-USUARIO/NOME-DO-REPOSITORIO.git

```


2. **Abra o projeto no IntelliJ IDEA**.
3. **Execute o arquivo principal:**
Navegue até `src/ConversorDadosMultiplos.java` e rode o método `main`.
4. **Exemplo de Execução no Console:**
**Entradas:**
```text
nome;Lucas;texto
idade;28;inteiro
ativo;true;booleano
habilidades;Java, SQL, Git;array
SAIR

```


**Saída JSON:**
```json
{
  "nome": "Lucas",
  "idade": 28,
  "ativo": true,
  "habilidades": [ "Java", "SQL", "Git" ]
}

```


**Saída XML:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<dados>
  <nome type="texto">Lucas</nome>
  <idade type="inteiro">28</idade>
  <ativo type="booleano">true</ativo>
  <habilidades type="array">
    <item>Java</item>
    <item>SQL</item>
    <item>Git</item>
  </habilidades>
</dados>

```


**Saída YAML:**
```yaml
nome: "Lucas"
idade: 28
ativo: true
habilidades: 
  - Java
  - SQL
  - Git

```



---

## ✒️ Autor

Desenvolvido por **[Vinicius Leandro]** durante os estudos de Java.

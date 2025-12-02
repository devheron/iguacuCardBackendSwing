# 💳 IguaçuCard – Backend  
Backend completo do sistema de gestão de cartões sociais **IguaçuCard**, desenvolvido em **Java + Swing + Hibernate + MySQL**.  
Este módulo implementa toda a lógica de negócios, persistência de dados, segurança e controle de sessão.

📄 Baseado na documentação oficial do projeto (Versão 1.0 – Novembro/2025).

---

## 📘 Visão Geral

O IguaçuCard é um sistema criado para gerenciar:

✔ Cartões sociais  
✔ Planos vinculados a empresas  
✔ Usuários (Admin, Empresa e Usuário Comum)  
✔ Transações financeiras  
✔ Benefícios  

O backend fornece toda a estrutura para garantir que as operações funcionem de forma segura e organizada.

---

## 🛠️ Tecnologias Utilizadas

- **Java 18**
- **Swing (para as telas administrativas e operacionais)**
- **Hibernate ORM**
- **MySQL**
- **Maven**
- **JPAUtil**
- **DBML** para modelagem visual
- **DAO + Services + Model** seguindo boas práticas de arquitetura

---

## 📁 Estrutura do Projeto

- org.example
- ├── model # Entidades JPA (Usuario, Empresa, Plano, Cartao, etc.)
- ├── DAO # Acesso ao banco via Hibernate
- ├── service # Regras de negócio
- ├── swing # Interfaces gráficas (telas)
- │ ├── usuarios
- │ ├── empresas
- │ ├── planos
- │ ├── cartoes
- │ ├── transacoes
- │ └── beneficios
- ├── security # Autenticação (hash de senha)
- ├── session # Controle de sessão (SessionContext)
- └── util # Classes auxiliares e configuração
- 
---

## 👤 Perfis do Sistema

### 👤 Usuário Comum
- Cadastro e login  
- Contratação de planos  
- Geração de cartão social  
- Visualização das suas transações  

### 🏢 Empresa
- Cadastro e login  
- Criação de planos  
- Listagem de cartões emitidos  
- Acompanhamento das transações de seus clientes  

### 🛠️ Administrador
- Gerencia:
  - Usuários  
  - Empresas  
  - Planos  
  - Cartões  
  - Benefícios  
  - Transações  
- Acesso ao painel completo  
- Controle de status (ativo/inativo)

---

## 📌 Principais Classes do Projeto

### 🔹 **Model**
- `Usuario.java`  
- `Empresa.java`  
- `Plano.java`  
- `Cartao.java`  
- `Transacao.java`  
- `Beneficio.java`  
- `Notificacao.java`

### 🔹 **Service**
- `AuthService.java` (login + hashing)  
- `UsuarioService.java`  
- `EmpresaService.java`  
- `PlanoService.java`  
- `CartaoService.java`  
- `TransacaoService.java`  
- `BeneficioService.java`

### 🔹 **Sessão**
- `SessionContext.java` — controla usuário atual logado

### 🔹 **Swing (Interface Administrativa)**
- `UsuarioFormFrame`  
- `EmpresaFormFrame`  
- `PlanoFormFrame`  
- `CartaoFormFrame`  
- `TransacaoListaFrame`  
- **Painéis principais:**  
  - `PainelAdmin`  
  - `PainelEmpresa`  
  - `PainelUsuario`
---

## 🔄 Fluxo Completo do Sistema

1. **Empresa se cadastra**
2. Administrador aprova ou cria usuários/empresas
3. Empresa cria *Planos*
4. Usuário Comum se cadastra e faz *login*
5. Usuário contrata um plano  
6. Sistema emite um *Cartão Social*
7. Cartão realiza *Transações*
8. Empresa e Usuário acompanham suas transações
9. Administrador tem relatório completo

---

## ⚙️ Como Rodar o Projeto

1. Configure o MySQL e importe o schema.
2. Atualize o arquivo `hibernate.cfg.xml` com seu usuário/senha.
3. Abra no IntelliJ IDEA.
4. Execute a classe principal das telas (ex: MainFrame).
5. O sistema iniciará com login (Admin/Empresa/Usuário).

---

## 📌 Melhorias Futuras

- Migração para **REST API (Spring Boot)**
- Dashboard com **relatórios gráficos**
- Sistema de notificações em tempo real
- Integração com pagamento externo
- Migração parcial para JavaFX ou Web

---

## 🧩 Conclusão

O backend do IguaçuCard entrega uma base sólida, escalável e totalmente funcional, implementando boas práticas de:

- Orientação a Objetos  
- Arquitetura em camadas  
- Persistência via Hibernate  
- Regras de negócio organizadas  
- Interface administrativa em Swing  
- Segurança com hash de senha  

Um projeto completo, maduro e ideal para portfólio profissional.

---

## 📄 Autores

- **Heron Felipe**  
- **Nicolas Gabriel**  
- *Projeto finalizado no final de 2025*



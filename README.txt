# CRUDBALL - Projeto de Técnicas de Programação
Implementação de uma aplicação CRUD com o tema "Futebol"
Linguagem: Java
Banco de Dados: Hypersonic SQL
GUI: Swing
Desenvolvido no primeiro semestre de 2024 - BSI023

# O que faz cada arquivo?
- Main.java: classe principal, responvável por criar os objeto que
irão inicializar o servidor, preencher as tabelas e invocar o
janela de seleção

- controller/DeleteForm.java: Formulário para exclusão de uma
tupla da tabela
- controller/InsertForm.java: Formulário para adiconar uma nova
tupla na tabela
- controller/UpdateForm.java: Formulário para atualizar uma tupla 
já existente na tabela

- domain/Leagues.java: Classe que implementa o conceito de 
Object-relational mapping (ORM, em português: Mapeamento objeto-relacional)
para facilitar a manipulação das tabelas. No caso, esta implementa
atributos e métodos que abstraem a tabela "Leagues"
- domain/Players.java: Implementaatributos e métodos que abstraem
a tabela "Players"
- domain/Teams.java: Implementaatributos e métodos que abstraem
a tabela "Teams"

- repository/ConnectDB.java: implementa apenas um método para
retornar a conexão do banco de dados
- repository/CreateTables.java: Cria as tabelas "Leagues", 
"Teams" e "Players"
- repository/PopulateTables.java: Preenche as tabelas

- service/DatabaseConfiguration: Estabelece a conexão com o banco
de dados
- service/DatabaseService: classe de serviço para facilitar a
criação e conexão do banco de dados

- view/PlayerSelection.java: janela que implementa a interface
para ver detalhes de jogadores de um determinado time
- view/Selection.java: janela que implementa a interface para
poder selecionar uma liga e um time

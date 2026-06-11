# 📖 Manual do Usuário

Este guia prático orienta como navegar pelo sistema, gerenciar os cadastros de clientes, animais e raças, e gerar os relatórios em `.txt`.

---

<br>

## 🖥️ Visão Geral

O sistema foi desenvolvido com o objetivo de consolidar habilidades práticas com CRUD, interligando três entidades principais: **Clientes**, **Animais** e **Raças**. 

Ao iniciar a aplicação, você será posto de frente com a região de cadastro. A navegação é dividida em três partes: cadastros, consultas e relatórios. Nas duas primeiras, é possível selecionar se a ação deverá ser feita para cliente, animal ou raça; na última, o usuário preenche as informações necessárias dependendo do relatório desejado.

---

<br>

## ⚙️ Como Utilizar as Funcionalidades

### 📃 1. Cadastros
* **Clientes:** Permite registrar as informações sobre os clientes. A tela possui os campos: nome (obrigatório), cpf (obrigatório), data de nascimento, telefone, endereço, bairro, cidade, estado e cep.
  
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/491bf229-b53c-42bf-a172-0ddc9de0c62e" />
</p>

<br>

* **Animais:** Ao cadastrar um animal, é necessário vinculá-lo a um **Cliente** (dono) e a uma **Raça** já existente. Os campos a serem preenchidos são: nome (obrigatório), cor, data de nascimento, sexo, cliente (obrigatório), raça (obrigatório).

<p align="center">
  <img width="769" height="450" alt="image" align="center" src="https://github.com/user-attachments/assets/f48e5157-4258-4f2a-8267-c930bbbdd42c" />
</p>

<br>

* **Raças:** Cadastro das raças que preencherão a ficha dos animais. Possui os campos nome e tipo (Cachorro ou Gato), sendo apenas o nome obrigatório no preenchimento.
<p align="center">
  <img width="759" height="450" alt="image" src="https://github.com/user-attachments/assets/bf88d058-6e7d-4d73-ab4a-6b5e36c731af" />
</p>

---

### 📝  2. Edições
* Para editar as informações cadastradas no aplicativo, basta ir em uma das tabelas e clicar no botão com ícone de lápis (assim como pode ser visualizado na imagem). No exemplo dessa seção, estamos editando informações sobre os Clientes, porém essa linha de procedimento também deve ser seguida para atualizar tanto animais quanto raças.
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/b8b66c4d-6a5b-462e-a16c-ed078031d030" />
</p>

<br>

* Após clicar no item desejado, a tela de cadastros aparecerá novamente, porém totalmente preenchida. Ao fazer as devidas alterações, é só clicar no botão de salvar e assim o item já será atualizado. Ao pressionar o botão de cancelar, o usuário é redirecionado para a tela de consultas novamente.

<p align="center">
  <img width="769" height="459" alt="image" src="https://github.com/user-attachments/assets/d367fd43-0a52-425a-a865-174e2745f0b1" />
</p>

---

### 🔒 3. Exclusão Lógica (Inativação)
> ⚠️ **Nota Importante:** O sistema utiliza o conceito de exclusão lógica. Isso significa que ao "excluir" um item, ele **não é apagado definitivamente** do banco de dados. 
> 
> O status do registro é alterado para **Inativo**, fazendo com que ele pare de aparecer nas listagens principais, preservando o histórico do banco de dados.

<br>

* Ainda na tela de exibição dos itens cadastrados, ao lado do botão de edição, temos a opção de inativar um item (o ícone de "X").
<p align="center">
  <img width="1231" height="117" alt="image" src="https://github.com/user-attachments/assets/f39cd6cd-ed44-4a4f-8315-7cb851c7a4a0" />
</p>
<br>

* Ao clica-lo, uma mensagem de confirmação aparecerá e só após a confirmação que a alteração realmente será feita no banco de dados.
<p align="center">
  <img width="1231" height="197" alt="image" src="https://github.com/user-attachments/assets/65c36a52-d982-406e-bedb-621eb86291dd" />
</p>

---

### 🔍 4. Consultas e Barra de Pesquisa
* Para localizar um registro rapidamente, utilize a **barra de pesquisas**. Os dados na tabela são exibidos em tempo real e, campos inativos não são contados na busca.
* Na tela de clientes, você pode pesquisar por nome e cpf:
<p align="center">
  <img width="800" height="448" alt="PesquisasCliente" src="https://github.com/user-attachments/assets/170a6cae-845f-4079-a3fe-ffb229239e2f" />
</p>
<br>

* Na tela de Animais, você pode pesquisar por nome do animal e raça, pelo nome ou cpf do cliente que o possui, como demonstrado abaixo:


* Na tela de Raças, você pode pesquisar por nome ou tipo da raça.
<p align="center">
  <img width="800" height="444" alt="PesquisasRaca" src="https://github.com/user-attachments/assets/cc297bd4-2563-4aef-afa2-29600a3d9528" />
</p>



## 📄 Geração de Relatórios

A aplicação possui uma função para a criação de relatórios a partir das consultas de:
* Todos os clientes e seus animais;
* Animais aniversariantes de um mês específico (Usuário precisa preencher o mês e ano de busca);
* Clientes aniversariantes de um mês específico (Usuário precisa preencher o mês e ano de busca);

1. No menu de navegação principal, selecione a opção **[Relatórios]**.
2. O sistema realizará as consultas necessárias e gerará um arquivo chamado `relatorio.txt`. 
3. Os arquivos conterão a especificação do que o relatório faz, quando ele foi gerado e as informações resultantes da consulta exbidas em "forma" de tabela.

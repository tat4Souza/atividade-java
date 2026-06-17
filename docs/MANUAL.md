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
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/879b30a3-cb40-4f7f-86aa-fc3befa5482b" />
</p>

<br>

* **Animais:** Ao cadastrar um animal, é necessário vinculá-lo a um **Cliente** (dono) e a uma **Raça** já existente. Os campos a serem preenchidos são: nome (obrigatório), cor, data de nascimento, sexo, cliente (obrigatório), raça (obrigatório).

<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/990a740d-e118-420d-bd21-9173f1da535d" />
</p>

<br>

* **Raças:** Cadastro das raças que preencherão a ficha dos animais. Possui os campos nome e tipo (Cachorro ou Gato), sendo apenas o nome obrigatório no preenchimento.
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/9e06f7a2-955b-4ff4-b7f4-06e0a82e330e" />
</p>

---

### 📝  2. Edições
* Para editar as informações cadastradas no aplicativo, basta ir em uma das tabelas e clicar no botão com ícone de lápis (assim como pode ser visualizado na imagem). No exemplo dessa seção, estamos editando informações sobre os Clientes, porém essa linha de procedimento também deve ser seguida para atualizar tanto animais quanto raças.
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/1dd7a186-d6af-461f-88d9-78369c1d369a" />
</p>

<br>

* Após clicar no item desejado, a tela de cadastros aparecerá novamente, porém totalmente preenchida. Ao fazer as devidas alterações, é só clicar no botão de salvar e assim o item já será atualizado. Ao pressionar o botão de cancelar, o usuário é redirecionado para a tela de consultas novamente.

<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/dd010521-b8c8-474f-9571-b2553953778b" />
</p>

---

### 🔒 3. Exclusão Lógica (Inativação)
> ⚠️ **Nota Importante:** O sistema utiliza o conceito de exclusão lógica. O status do registro é alterado para **Inativo**, fazendo com que ele pare de aparecer nas listagens principais, preservando o histórico do banco de dados.

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
<p align="center">
  <img width="800" height="443" alt="pesquisaAnimais-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/32e0f59d-2c36-4072-a24f-f1f1c3f0d13e" />
</p>

* Na tela de Raças, você pode pesquisar por nome ou tipo da raça.
<p align="center">
  <img width="800" height="444" alt="PesquisasRaca" src="https://github.com/user-attachments/assets/cc297bd4-2563-4aef-afa2-29600a3d9528" />
</p>



## 📄 Geração de Relatórios

1. No menu de navegação principal, selecione a opção **[Relatórios]**.
2. O sistema realizará as consultas necessárias e gerará um arquivo `.txt` com o nome especificado pelo usuário. 
3. Os arquivos conterão a especificação do tipo de relatório e as informações resultantes da consulta exbidas de maneira similar a uma tabela.

As possíveis consultas são:
* Todos os clientes e seus animais;
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/8666437c-0716-460c-8f98-06683be416e0" />
</p>

<br>

* Animais aniversariantes de um mês específico (Usuário precisa preencher o mês e ano de busca);
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/2dfba234-22dc-4f14-8068-0c75448c8472" />
</p>

<br>

* Clientes aniversariantes de um mês específico (Usuário precisa preencher o mês e ano de busca);
<p align="center">
  <img width="769" height="450" alt="image" src="https://github.com/user-attachments/assets/fa07e5f5-3a6c-45be-b942-23baea9aaa0d" />
</p>



package com.pratica4.application.models;

import java.sql.Date;

public class Animal implements Identificador {
    private int id;
    private String nome;
    private Date data_nascimento;
    private String sexo;
    private String cor;
    private String observacoes;
    private int id_raca;
    private int id_cliente;
    private boolean status;

    private Raca raca;

    public Animal ( String nome, Date data_nascimento, String sexo, String cor, String observacoes, int id_cliente, Raca raca, boolean status) {
        this.id = id;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.cor = cor;
        this.observacoes = observacoes;
        this.id_cliente = id_cliente;
        this.raca = raca;
        this.status = status;
        if (raca != null) {
            this.id_raca = raca.getId();
        }
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Date getData_nascimento() { return data_nascimento; }
    public void setData_nascimento(Date data_nascimento) { this.data_nascimento = data_nascimento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public int getId_cliente() { return id_cliente; }
    public void setId_cliente(int id_cliente) { this.id_cliente = id_cliente; }
    public int getId_raca() { return id_raca; }
    public void setId_raca(int id_raca) { this.id_raca = id_raca; }
    public Boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Raca getRaca() { return raca; }

    public void setRaca(Raca raca) { this.raca = raca; }
}

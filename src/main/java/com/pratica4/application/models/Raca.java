package com.pratica4.application.models;

public class Raca implements Identificador {
    private int id;
    private String nome;
    private String tipo;
    private boolean status;

    public Raca(String nome, String tipo, boolean status) {
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}

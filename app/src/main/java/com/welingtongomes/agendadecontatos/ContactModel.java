package com.welingtongomes.agendadecontatos;

public class ContactModel {
    private int id;
    private String nome;
    private String numero;

    public ContactModel(){
    }

    public ContactModel(String nome, String numero){
        this.nome = nome;
        this.numero = numero;
    };

    public ContactModel(int id, String nome, String numero) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.example.elaborato_ing;

public class Persona {
    private String email, nome, cognome, password;

    public Persona() {

    }

    public Persona(String email, String nome, String cognome, String password) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

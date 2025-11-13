package br.com.fiap.models;

import org.eclipse.parsson.api.BufferPool;

public class Funcionario {

    private Integer id;
    private String nome;
    private String cpf;
    private String cargo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Funcionario() {
    }

    public Funcionario(String cargo, String cpf, String nome, Integer id) {
        this.cargo = cargo;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
    }

    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }
}



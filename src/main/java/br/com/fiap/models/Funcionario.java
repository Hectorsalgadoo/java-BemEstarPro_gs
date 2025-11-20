package br.com.fiap.models;

import org.eclipse.parsson.api.BufferPool;

/**
 * Classe que representa um Funcionário no sistema.
 * Contém informações básicas do funcionário como identificação, cargo e CPF.
 * Inclui validação básica de CPF.
 */
public class Funcionario {

    private Integer id;
    private String nome;
    private String cpf;
    private String cargo;

    /**
     *  ID único do funcionário
     */
    public Integer getId() {
        return id;
    }

    /**
     *  id ID único do funcionário
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *  Nome completo do funcionário
     */
    public String getNome() {
        return nome;
    }

    /**
     *  nome Nome completo do funcionário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *  Cargo/função do funcionário na empresa
     */
    public String getCargo() {
        return cargo;
    }

    /**
     *  cargo Cargo/função do funcionário na empresa
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * CPF do funcionário (apenas números, 11 dígitos)
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *  cpf CPF do funcionário (apenas números, 11 dígitos)
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Construtor padrão que inicializa um funcionário vazio.
     * Útil para criar instâncias que serão preenchidas posteriormente.
     */
    public Funcionario() {
    }

    /**
     * Construtor completo com todas as informações do funcionário.
     */
    public Funcionario(String cargo, String cpf, String nome, Integer id) {
        this.cargo = cargo;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
    }

    /**
     * Valida se o CPF do funcionário está no formato correto.
     * Verifica se o CPF não é nulo e contém exatamente 11 dígitos numéricos.
     */
    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }
}
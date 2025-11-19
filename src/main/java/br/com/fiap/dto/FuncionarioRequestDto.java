
package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FuncionarioRequestDto {
    @JsonProperty("id_funcionario")
    private Integer id;

    @JsonProperty("nome_funcionario")
    private String nome;

    @JsonProperty("cpf_funcionario")
    private String cpf;

    @JsonProperty("cargo_funcionario")
    private String cargo;

    public FuncionarioRequestDto() {
    }

    public FuncionarioRequestDto(Integer id, String nome, String cpf, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
    }

    public FuncionarioRequestDto(String nome, String cpf, String cargo) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Valida se o CPF tem exatamente 11 dígitos numéricos
     */
    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }

    /**
     * Valida se todos os campos obrigatórios estão preenchidos
     */
    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() &&
                cpf != null && isCpfValido() &&
                cargo != null && !cargo.trim().isEmpty();
    }

    /**
     * Limpa e formata os dados
     */
    public void cleanData() {
        if (nome != null) {
            nome = nome.trim();
        }
        if (cpf != null) {
            cpf = cpf.trim().replaceAll("[^\\d]", ""); // Remove tudo que não é dígito
        }
        if (cargo != null) {
            cargo = cargo.trim();
        }
    }

    /**
     * Método de debug para verificar os dados
     */
    public void printDebug() {
        System.out.println("=== DEBUG FuncionarioRequestDto ===");
        System.out.println("ID: " + id);
        System.out.println("Nome (nome_funcionario): '" + nome + "'");
        System.out.println("CPF (cpf_funcionario): '" + cpf + "'");
        System.out.println("Cargo (cargo_funcionario): '" + cargo + "'");
        System.out.println("CPF válido: " + isCpfValido());
        System.out.println("isValid(): " + isValid());
        System.out.println("===============================");
    }

    @Override
    public String toString() {
        return "FuncionarioRequestDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
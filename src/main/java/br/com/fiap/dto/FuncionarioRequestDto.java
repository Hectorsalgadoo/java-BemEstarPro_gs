package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO utilizado para receber os dados de requisição relacionados a um funcionário.
 * <p>
 * Este objeto é usado nas operações de criação e atualização de funcionários,
 * representando as informações necessárias enviadas pelo cliente.
 * </p>
 */
public class FuncionarioRequestDto {

    /**
     * Identificador único do funcionário.
     * Pode ser nulo em casos de criação.
     */
    @JsonProperty("id_funcionario")
    private Integer id;

    /**
     * Nome completo do funcionário.
     */
    @JsonProperty("nome_funcionario")
    private String nome;

    /**
     * CPF do funcionário, representado como 11 dígitos numéricos.
     */
    @JsonProperty("cpf_funcionario")
    private String cpf;

    /**
     * Cargo ou função desempenhada pelo funcionário.
     */
    @JsonProperty("cargo_funcionario")
    private String cargo;

    /**
     * Construtor padrão.
     */
    public FuncionarioRequestDto() {
    }

    /**
     * Construtor completo para criação ou atualização de funcionários.
     */
    public FuncionarioRequestDto(Integer id, String nome, String cpf, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
    }

    /**
     * Construtor sem ID, usado geralmente em cadastros.
     */
    public FuncionarioRequestDto(String nome, String cpf, String cargo) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
    }

    /**
     *  ID do funcionário
     */
    public Integer getId() {
        return id;
    }

    /**
     *  id novo ID do funcionário
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *  nome completo do funcionário
     */
    public String getNome() {
        return nome;
    }

    /**
     *  nome novo nome do funcionário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *  CPF do funcionário
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *  cpf novo CPF do funcionário
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     *  cargo ou função do funcionário
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * cargo novo cargo do funcionário
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Verifica se o CPF possui exatamente 11 dígitos numéricos.
     */
    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }

    /**
     * Valida se todos os campos obrigatórios estão preenchidos corretamente.
     *
     */
    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() &&
                cpf != null && isCpfValido() &&
                cargo != null && !cargo.trim().isEmpty();
    }


    /**
     * Limpa e padroniza os dados do DTO.
     * <p>
     * - remove espaços extras do nome e cargo <br>
     * - remove caracteres não numéricos do CPF
     * </p>
     */
    public void cleanData() {
        if (nome != null) {
            nome = nome.trim();
        }
        if (cpf != null) {
            cpf = cpf.trim().replaceAll("[^\\d]", ""); // Remove tudo que não é número
        }
        if (cargo != null) {
            cargo = cargo.trim();
        }
    }

    /**
     * Imprime no console todas as informações do DTO
     * para fins de debug e verificação.
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

    /**
     * Retorna uma representação textual do DTO.
     *
     */
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

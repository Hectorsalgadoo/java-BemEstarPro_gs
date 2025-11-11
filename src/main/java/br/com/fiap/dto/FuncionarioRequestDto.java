package br.com.fiap.dto;

public class FuncionarioRequestDto {

    private Integer id;
    private String nome;
    private String cpf;
    private String cargo;

    // Construtor vazio
    public FuncionarioRequestDto() {
    }

    // Construtor com parâmetros


    public FuncionarioRequestDto(String cargo, String cpf, String nome, Integer id) {
        this.cargo = cargo;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
    }

    // Getters e Setters
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

    @Override
    public String toString() {
        return "FuncionarioRequestDto{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }


    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }

    /**
     * Valida se todos os campos obrigatórios estão preenchidos
     */
    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() &&
                cpf != null && isCpfValido();
    }

    /**
     * Limpa e formata os dados
     */
    public void cleanData() {
        if (nome != null) {
            nome = nome.trim();
        }
        if (cpf != null) {
            cpf = cpf.trim().replaceAll("[^\\d]", "");
        }
    }
}

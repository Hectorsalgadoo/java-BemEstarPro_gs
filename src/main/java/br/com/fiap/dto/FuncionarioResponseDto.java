package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DTO usado para retornar dados de {@link Funcionario} nas respostas da API.
 *
 * <p>Este DTO formata os campos conforme o padrão de saída JSON definido
 * pela aplicação, garantindo consistência entre o modelo de domínio
 * e o formato exposto externamente.</p>
 */
@XmlRootElement
public class FuncionarioResponseDto {

    /**
     * Identificador único do funcionário.
     */
    @JsonProperty("id_funcionario")
    private Integer id_funcionario;

    /**
     * Nome completo do funcionário.
     */
    @JsonProperty("nome_funcionario")
    private String nome_funcionario;

    /**
     * CPF do funcionário (somente números).
     */
    @JsonProperty("cpf_funcionario")
    private String cpf_funcionario;

    /**
     * Cargo ou função desempenhada pelo funcionário.
     */
    @JsonProperty("cargo_funcionario")
    private String cargo_funcionario;

    /**
     * Construtor padrão necessário para serialização JSON/XML.
     */
    public FuncionarioResponseDto() {
    }

    /**
     * Construtor completo para inicialização total do DTO.
     */
    public FuncionarioResponseDto(Integer id_funcionario, String nome_funcionario, String cpf_funcionario, String cargo_funcionario) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.cpf_funcionario = cpf_funcionario;
        this.cargo_funcionario = cargo_funcionario;
    }

    /**
     * Converte uma entidade {@link Funcionario} para um DTO de resposta.
     */
    public static FuncionarioResponseDto convertToDto(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }

        return new FuncionarioResponseDto(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getCargo()
        );
    }

    /**
     * Converte o DTO em uma entidade {@link Funcionario}.
     *
     *  dto objeto de transferência
     *  entidade {@code Funcionario} preenchida ou {@code null} se o DTO for nulo
     */
    public static Funcionario convertToEntity(FuncionarioResponseDto dto) {
        if (dto == null) {
            return null;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId_funcionario());
        funcionario.setNome(dto.getNome_funcionario());
        funcionario.setCpf(dto.getCpf_funcionario());
        funcionario.setCargo(dto.getCargo_funcionario());
        return funcionario;
    }

    /**
     *  ID do funcionário.
     */
    public Integer getId_funcionario() {
        return id_funcionario;
    }

    /**
     *  id_funcionario novo ID do funcionário
     */
    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    /**
     *  nome completo do funcionário.
     */
    public String getNome_funcionario() {
        return nome_funcionario;
    }

    /**
     *  nome_funcionario novo nome do funcionário
     */
    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    /**
     * CPF do funcionário.
     */
    public String getCpf_funcionario() {
        return cpf_funcionario;
    }

    /**
     *  cpf_funcionario novo CPF do funcionário
     */
    public void setCpf_funcionario(String cpf_funcionario) {
        this.cpf_funcionario = cpf_funcionario;
    }

    /**
     *  cargo do funcionário.
     */
    public String getCargo_funcionario() {
        return cargo_funcionario;
    }

    /**
     *  cargo_funcionario novo cargo do funcionário
     */
    public void setCargo_funcionario(String cargo_funcionario) {
        this.cargo_funcionario = cargo_funcionario;
    }

    @Override
    public String toString() {
        return "FuncionarioResponseDto{" +
                "id_funcionario=" + id_funcionario +
                ", nome_funcionario='" + nome_funcionario + '\'' +
                ", cpf_funcionario='" + cpf_funcionario + '\'' +
                ", cargo_funcionario='" + cargo_funcionario + '\'' +
                '}';
    }
}

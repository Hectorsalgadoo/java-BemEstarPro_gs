package br.com.fiap.dto;
import br.com.fiap.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DTO para resposta de paciente (sem relação com ConsultaOnline)
 */
@XmlRootElement
public class FuncionarioResponseDto {

    @JsonProperty("id_funcionario")
    private Integer id_funcionario;

    @JsonProperty("nome_funcionario")
    private String nome_funcionario;

    @JsonProperty("cpf_funcionario")
    private String cpf_funcionario;

    @JsonProperty("cargo_funcionario")
    private String cargo_funcionario;

    public FuncionarioResponseDto() {
    }

    public FuncionarioResponseDto(Integer id_funcionario, String nome_funcionario, String cpf_funcionario, String cargo_funcionario) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.cpf_funcionario = cpf_funcionario;
        this.cargo_funcionario = cargo_funcionario;
    }

    /**
     * Converte Funcionario para DTO
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
     * Converte DTO para Funcionario
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


    public String getCargo_funcionario() {
        return cargo_funcionario;
    }

    public void setCargo_funcionario(String cargo_funcionario) {
        this.cargo_funcionario = cargo_funcionario;
    }

    public String getCpf_funcionario() {
        return cpf_funcionario;
    }

    public void setCpf_funcionario(String cpf_funcionario) {
        this.cpf_funcionario = cpf_funcionario;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    public Integer getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
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

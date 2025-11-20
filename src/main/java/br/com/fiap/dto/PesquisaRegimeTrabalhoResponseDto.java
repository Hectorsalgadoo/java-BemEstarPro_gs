package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO utilizado para representar a resposta da pesquisa sobre regime de trabalho.
 *
 * <p>Esse objeto é retornado pelas requisições que consultam os registros da pesquisa,
 * contendo informações como satisfação, regime de trabalho, comentário e dados do funcionário.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisaRegimeTrabalhoResponseDto {

    /**
     * Identificador único da pesquisa.
     */
    private int id_pesquisa;

    /**
     * Nível de satisfação informado na pesquisa (ex.: 1 a 5).
     */
    private int satisfacao;

    /**
     * Regime de trabalho selecionado pelo funcionário (ex.: "Remoto", "Híbrido", "Presencial").
     */
    private String regime_trabalho;

    /**
     * Comentário opcional do funcionário sobre o regime de trabalho.
     */
    private String comentario;

    /**
     * Objeto contendo informações do funcionário associado à pesquisa.
     */
    private Funcionario funcionario;

    /**
     * Construtor padrão utilizado para inicialização via frameworks.
     */
    public PesquisaRegimeTrabalhoResponseDto() {}

    /**
     * Construtor completo que permite preencher todas as informações.
     */
    public PesquisaRegimeTrabalhoResponseDto(int id_pesquisa, int satisfacao, String regime_trabalho, String comentario, Funcionario funcionario) {
        this.id_pesquisa = id_pesquisa;
        this.satisfacao = satisfacao;
        this.regime_trabalho = regime_trabalho;
        this.comentario = comentario;
        this.funcionario = funcionario;
    }

    /**
     * Retorna o ID da pesquisa.
     */
    public int getId_pesquisa() {
        return id_pesquisa;
    }

    /**
     * Define o ID da pesquisa.
     */
    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    /**
     * Retorna o valor de satisfação.
     */
    public int getSatisfacao() {
        return satisfacao;
    }

    /**
     * Define o valor de satisfação.
     */
    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
    }

    /**
     * Retorna o regime de trabalho do funcionário.
     */
    public String getRegime_trabalho() {
        return regime_trabalho;
    }

    /**
     * Define o regime de trabalho.
     */
    public void setRegime_trabalho(String regime_trabalho) {
        this.regime_trabalho = regime_trabalho;
    }

    /**
     * Retorna o comentário do funcionário.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Define o comentário opcional do funcionário.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Retorna o objeto {@link Funcionario} associado.
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * Define o funcionário relacionado à pesquisa.
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * Converte um objeto {@link PesquisaRegimeTrabalho} em seu respectivo DTO de resposta.
     */
    public static PesquisaRegimeTrabalhoResponseDto convertToDto(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
        if (pesquisaRegimeTrabalho == null) {
            return null;
        }
        PesquisaRegimeTrabalhoResponseDto dto = new PesquisaRegimeTrabalhoResponseDto();
        dto.setId_pesquisa(pesquisaRegimeTrabalho.getId_pesquisa());
        dto.setFuncionario(pesquisaRegimeTrabalho.getFuncionario());
        dto.setSatisfacao(pesquisaRegimeTrabalho.getSatisfacao());
        dto.setRegime_trabalho(pesquisaRegimeTrabalho.getRegime_trabalho());
        dto.setComentario(pesquisaRegimeTrabalho.getComentario());
        return dto;
    }

    @Override
    public String toString() {
        return "PesquisaRegimeTrabalhoResponseDto{" +
                "id_pesquisa=" + id_pesquisa +
                ", satisfacao=" + satisfacao +
                ", regime_trabalho='" + regime_trabalho + '\'' +
                ", comentario='" + comentario + '\'' +
                ", funcionario=" + funcionario +
                '}';
    }
}

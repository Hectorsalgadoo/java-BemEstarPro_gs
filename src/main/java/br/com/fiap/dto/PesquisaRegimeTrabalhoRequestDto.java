package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO utilizado para receber dados de uma pesquisa relacionada ao regime de trabalho
 * e ao nível de satisfação de um funcionário.
 *
 * <p>Esse objeto é usado principalmente em requisições HTTP onde o cliente envia
 * respostas de pesquisas internas, incluindo comentários, satisfação e regime de trabalho.
 * Campos desconhecidos na requisição são ignorados devido à anotação
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisaRegimeTrabalhoRequestDto {

    /**
     * Identificador único da pesquisa.
     */
    private int id_pesquisa;

    /**
     * Nível de satisfação informado pelo funcionário (ex: 1 a 5).
     */
    private int satisfacao;

    /**
     * Regime de trabalho do funcionário (ex: "Presencial", "Remoto", "Híbrido").
     */
    private String regime_trabalho;

    /**
     * Comentário opcional enviado pelo funcionário.
     */
    private String comentario;

    /**
     * ID do funcionário que respondeu à pesquisa.
     */
    private Integer idfuncionario;

    /**
     * Retorna o ID do funcionário que respondeu à pesquisa.
     */
    public Integer getIdfuncionario() {
        return idfuncionario;
    }

    /**
     * Define o ID do funcionário que respondeu à pesquisa.
     */
    public void setIdfuncionario(Integer idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    /**
     * Retorna o comentário opcional do funcionário.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Define o comentário opcional da pesquisa.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Retorna o regime de trabalho informado.
     */
    public String getRegime_trabalho() {
        return regime_trabalho;
    }

    /**
     * Define o regime de trabalho do funcionário.
     */
    public void setRegime_trabalho(String regime_trabalho) {
        this.regime_trabalho = regime_trabalho;
    }

    /**
     * Retorna o nível de satisfação.
     *
     * @return valor de satisfação
     */
    public int getSatisfacao() {
        return satisfacao;
    }

    /**
     * Define o nível de satisfação informado.
     */
    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
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
     * Construtor padrão (necessário para serialização e frameworks).
     */
    public PesquisaRegimeTrabalhoRequestDto() {
    }

    /**
     * Construtor completo, utilizado para preencher todos os dados da pesquisa.
     */
    public PesquisaRegimeTrabalhoRequestDto(Integer idfuncionario, String comentario, String regime_trabalho, int satisfacao, int id_pesquisa) {
        this.idfuncionario = idfuncionario;
        this.comentario = comentario;
        this.regime_trabalho = regime_trabalho;
        this.satisfacao = satisfacao;
        this.id_pesquisa = id_pesquisa;
    }

    @Override
    public String toString() {
        return "PesquisaRegimeTrabalhoRequestDto{" +
                "id_pesquisa=" + id_pesquisa +
                ", satisfacao=" + satisfacao +
                ", regime_trabalho='" + regime_trabalho + '\'' +
                ", comentario='" + comentario + '\'' +
                ", idfuncionario=" + idfuncionario +
                '}';
    }
}

package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO para requisição de consulta online
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisaRegimeTrabalhoRequestDto {

    private int id_pesquisa;
    private int satisfacao;
    private String regime_trabalho;
    private String comentario;
    //private Funcionario funcionario;
    private Integer idfuncionario;


    public Integer getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(Integer idfuncionario) {
        this.idfuncionario = idfuncionario;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getRegime_trabalho() {
        return regime_trabalho;
    }

    public void setRegime_trabalho(String regime_trabalho) {
        this.regime_trabalho = regime_trabalho;
    }

    public int getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
    }

    public int getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public PesquisaRegimeTrabalhoRequestDto() {
    }

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

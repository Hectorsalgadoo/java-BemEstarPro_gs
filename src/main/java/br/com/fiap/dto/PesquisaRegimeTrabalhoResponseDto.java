package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * DTO para resposta da consulta de Pesquisa de Regime de Trabalho
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisaRegimeTrabalhoResponseDto {

    private int id_pesquisa;
    private int satisfacao;
    private String regime_trabalho;
    private String comentario;
    private Funcionario funcionario;

    public PesquisaRegimeTrabalhoResponseDto() {
    }

    public PesquisaRegimeTrabalhoResponseDto(int id_pesquisa, int satisfacao, String regime_trabalho, String comentario, Funcionario funcionario) {
        this.id_pesquisa = id_pesquisa;
        this.satisfacao = satisfacao;
        this.regime_trabalho = regime_trabalho;
        this.comentario = comentario;
        this.funcionario = funcionario;
    }

    public int getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public int getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
    }

    public String getRegime_trabalho() {
        return regime_trabalho;
    }

    public void setRegime_trabalho(String regime_trabalho) {
        this.regime_trabalho = regime_trabalho;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
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


    /**
     * Converte ConsultaOnline para DTO
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
}







package br.com.fiap.models;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Modelo de  pesquisa de regime de trabalho
 */
@XmlRootElement
public class PesquisaRegimeTrabalho {

    private int id_pesquisa;
    private int satisfacao;
    private String regime_trabalho;
    private String comentario;
    private Funcionario funcionario;
    private Integer idfuncionario;

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

    public Integer getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(Integer idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    public PesquisaRegimeTrabalho() {
    }

    public PesquisaRegimeTrabalho(Funcionario funcionario, int id_pesquisa, int satisfacao, String regime_trabalho, String comentario, Integer idfuncionario) {
        this.funcionario = funcionario;
        this.id_pesquisa = id_pesquisa;
        this.satisfacao = satisfacao;
        this.regime_trabalho = regime_trabalho;
        this.comentario = comentario;
        this.idfuncionario = idfuncionario;
    }

    @Override
    public String toString() {
        return "PesquisaRegimeTrabalho{" +
                "id_pesquisa=" + id_pesquisa +
                ", satisfacao=" + satisfacao +
                ", regime_trabalho='" + regime_trabalho + '\'' +
                ", comentario='" + comentario + '\'' +
                ", funcionario=" + funcionario +
                ", idfuncionario=" + idfuncionario +
                '}';
    }




}

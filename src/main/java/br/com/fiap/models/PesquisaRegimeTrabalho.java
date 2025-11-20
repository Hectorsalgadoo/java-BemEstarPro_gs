package br.com.fiap.models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Modelo que representa uma pesquisa sobre regime de trabalho.
 * Armazena informações de satisfação dos funcionários com seu regime atual de trabalho.
 * Pode conter comentários e está associada a um funcionário específico.
 */
@ApplicationScoped
@XmlRootElement
public class PesquisaRegimeTrabalho {

    private int id_pesquisa;
    private int satisfacao;
    private String regime_trabalho;
    private String comentario;
    private Funcionario funcionario;
    private Integer idfuncionario;

    /**
     *  ID único da pesquisa
     */
    public int getId_pesquisa() {
        return id_pesquisa;
    }

    /**
     *  id_pesquisa ID único da pesquisa
     */
    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    /**
     *  Nível de satisfação do funcionário (valor numérico)
     */
    public int getSatisfacao() {
        return satisfacao;
    }

    /**
     *  satisfacao Nível de satisfação do funcionário (valor numérico)
     */
    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
    }

    /**
     *  Tipo de regime de trabalho (ex: presencial, híbrido, remoto)
     */
    public String getRegime_trabalho() {
        return regime_trabalho;
    }

    /**
     *  regime_trabalho Tipo de regime de trabalho (ex: presencial, híbrido, remoto)
     */
    public void setRegime_trabalho(String regime_trabalho) {
        this.regime_trabalho = regime_trabalho;
    }

    /**
     *  Comentário adicional do funcionário sobre o regime de trabalho
     */
    public String getComentario() {
        return comentario;
    }

    /**
     *  comentario Comentário adicional do funcionário sobre o regime de trabalho
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     *  Objeto Funcionario associado à pesquisa
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     *  funcionario Objeto Funcionario associado à pesquisa
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     *  ID do funcionário associado à pesquisa
     */
    public Integer getIdfuncionario() {
        return idfuncionario;
    }

    /**
     * idfuncionario ID do funcionário associado à pesquisa
     */
    public void setIdfuncionario(Integer idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    /**
     * Construtor padrão que inicializa uma pesquisa vazia.
     * Útil para criar instâncias que serão preenchidas posteriormente.
     */
    public PesquisaRegimeTrabalho() {
    }

    /**
     * Construtor completo com todas as informações da pesquisa.
     */
    public PesquisaRegimeTrabalho(Funcionario funcionario, int id_pesquisa, int satisfacao,
                                  String regime_trabalho, String comentario, Integer idfuncionario) {
        this.funcionario = funcionario;
        this.id_pesquisa = id_pesquisa;
        this.satisfacao = satisfacao;
        this.regime_trabalho = regime_trabalho;
        this.comentario = comentario;
        this.idfuncionario = idfuncionario;
    }

    /**
     * Retorna uma representação em string dos dados da pesquisa.
     */
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
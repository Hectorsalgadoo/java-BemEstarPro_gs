package br.com.fiap.dto;

import br.com.fiap.models.PesquisaRegimeTrabalho;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioRequestDto {
    private Integer id_funcionario;
    private Integer id_pesquisa;
    private String resumo_feedback;
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;



    public RelatorioRequestDto() {
    }

    public RelatorioRequestDto(Integer id_funcionario, PesquisaRegimeTrabalho pesquisaRegimeTrabalho, String resumo_feedback, Integer id_pesquisa) {
        this.id_funcionario = id_funcionario;
        this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
        this.resumo_feedback = resumo_feedback;
        this.id_pesquisa = id_pesquisa;
    }

    public Integer getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public PesquisaRegimeTrabalho getPesquisaRegimeTrabalho() {
        return pesquisaRegimeTrabalho;
    }

    public void setPesquisaRegimeTrabalho(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
        this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
    }

    public String getResumo_feedback() {
        return resumo_feedback;
    }

    public void setResumo_feedback(String resumo_feedback) {
        this.resumo_feedback = resumo_feedback;
    }

    public Integer getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(Integer id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public void cleanData() {
        if (resumo_feedback != null) {
            resumo_feedback = resumo_feedback.trim();
        }

        if (id_funcionario == null || id_funcionario <= 0) {
            throw new IllegalArgumentException("ID do funcionário é obrigatório e deve ser maior que zero.");
        }

        if (id_pesquisa == null || id_pesquisa <= 0) {
            throw new IllegalArgumentException("ID da pesquisa é obrigatório e deve ser maior que zero.");
        }

        if (resumo_feedback == null || resumo_feedback.isEmpty()) {
            throw new IllegalArgumentException("Resumo do feedback é obrigatório.");
        }

        if (pesquisaRegimeTrabalho == null) {
            throw new IllegalArgumentException("Pesquisa Regime de Trabalho é obrigatório.");
        }
    }

    public boolean isValid() {
        return id_funcionario != null && id_funcionario > 0
                && id_pesquisa != null && id_pesquisa > 0
                && resumo_feedback != null && !resumo_feedback.isEmpty()
                && pesquisaRegimeTrabalho != null;
    }

    @Override
    public String toString() {
        return "RelatorioRequestDto{" +
                "id_funcionario=" + id_funcionario +
                ", id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                '}';
    }

}

package br.com.fiap.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioRequestDto {

    private Integer id_funcionario;
    private String resumo_feedback;
    private String nivel_bem_estar;
    private String tendencias_humor;

    public RelatorioRequestDto() {
    }

    public RelatorioRequestDto(Integer id_funcionario, String resumo_feedback,
                               String nivel_bem_estar, String tendencias_humor) {
        this.id_funcionario = id_funcionario;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    public Integer getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getResumo_feedback() {
        return resumo_feedback;
    }

    public void setResumo_feedback(String resumo_feedback) {
        this.resumo_feedback = resumo_feedback;
    }

    public String getNivel_bem_estar() {
        return nivel_bem_estar;
    }

    public void setNivel_bem_estar(String nivel_bem_estar) {
        this.nivel_bem_estar = nivel_bem_estar;
    }

    public String getTendencias_humor() {
        return tendencias_humor;
    }

    public void setTendencias_humor(String tendencias_humor) {
        this.tendencias_humor = tendencias_humor;
    }

    public void cleanData() {

        if (id_funcionario == null || id_funcionario <= 0) {
            throw new IllegalArgumentException("ID do funcionário é obrigatório e deve ser maior que zero.");
        }

        if (resumo_feedback == null || resumo_feedback.trim().isEmpty()) {
            throw new IllegalArgumentException("Resumo do feedback é obrigatório.");
        }

        if (nivel_bem_estar == null || nivel_bem_estar.trim().isEmpty()) {
            throw new IllegalArgumentException("Nível de bem-estar é obrigatório.");
        }

        if (tendencias_humor == null || tendencias_humor.trim().isEmpty()) {
            throw new IllegalArgumentException("Tendências de humor são obrigatórias.");
        }

        resumo_feedback = resumo_feedback.trim();
        nivel_bem_estar = nivel_bem_estar.trim();
        tendencias_humor = tendencias_humor.trim();
    }

    @Override
    public String toString() {
        return "RelatorioRequestDto{" +
                "id_funcionario=" + id_funcionario +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                '}';
    }
}

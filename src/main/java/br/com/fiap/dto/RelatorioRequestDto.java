package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioRequestDto {

    @JsonProperty("id_pesquisa")
    private int id_pesquisa;

    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    @JsonProperty("nivel_bem_estar")
    private String nivel_bem_estar;

    @JsonProperty("tendencias_humor")
    private String tendencias_humor;

    // Construtor padrão
    public RelatorioRequestDto() {
    }

    // Construtor com parâmetros
    public RelatorioRequestDto(int id_pesquisa, String resumo_feedback,
                               String nivel_bem_estar, String tendencias_humor) {
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    // Getters e Setters
    public int getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
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

    // Método para verificar se possui pesquisa vinculada
    public boolean hasPesquisa() {
        return id_pesquisa > 0;
    }

    // Método para validar dados básicos
    public boolean isValid() {
        return id_pesquisa > 0 &&
                resumo_feedback != null && !resumo_feedback.trim().isEmpty() &&
                nivel_bem_estar != null && !nivel_bem_estar.trim().isEmpty() &&
                tendencias_humor != null && !tendencias_humor.trim().isEmpty();
    }

    // Método para obter mensagens de validação
    public String getValidationMessage() {
        if (id_pesquisa <= 0) {
            return "ID da pesquisa é obrigatório e deve ser maior que zero";
        }
        if (resumo_feedback == null || resumo_feedback.trim().isEmpty()) {
            return "Resumo do feedback é obrigatório";
        }
        if (nivel_bem_estar == null || nivel_bem_estar.trim().isEmpty()) {
            return "Nível de bem-estar é obrigatório";
        }
        if (tendencias_humor == null || tendencias_humor.trim().isEmpty()) {
            return "Tendências de humor são obrigatórias";
        }
        return "VALID";
    }

    // Limpeza e normalização dos dados
    public void cleanData() {
        if (this.resumo_feedback != null) {
            this.resumo_feedback = this.resumo_feedback.trim();
        }
        if (this.nivel_bem_estar != null) {
            this.nivel_bem_estar = this.nivel_bem_estar.trim();
        }
        if (this.tendencias_humor != null) {
            this.tendencias_humor = this.tendencias_humor.trim();
        }
    }

    // Método para criar uma cópia do DTO
    public RelatorioRequestDto copy() {
        return new RelatorioRequestDto(
                this.id_pesquisa,
                this.resumo_feedback,
                this.nivel_bem_estar,
                this.tendencias_humor
        );
    }

    @Override
    public String toString() {
        return "RelatorioRequestDto{" +
                "id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelatorioRequestDto that = (RelatorioRequestDto) o;

        if (id_pesquisa != that.id_pesquisa) return false;
        if (resumo_feedback != null ? !resumo_feedback.equals(that.resumo_feedback) : that.resumo_feedback != null)
            return false;
        if (nivel_bem_estar != null ? !nivel_bem_estar.equals(that.nivel_bem_estar) : that.nivel_bem_estar != null)
            return false;
        return tendencias_humor != null ? tendencias_humor.equals(that.tendencias_humor) : that.tendencias_humor == null;
    }

    @Override
    public int hashCode() {
        int result = id_pesquisa;
        result = 31 * result + (resumo_feedback != null ? resumo_feedback.hashCode() : 0);
        result = 31 * result + (nivel_bem_estar != null ? nivel_bem_estar.hashCode() : 0);
        result = 31 * result + (tendencias_humor != null ? tendencias_humor.hashCode() : 0);
        return result;
    }
}
package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioRequestDto {

    @JsonProperty("id_pesquisa")
    private int id_pesquisa; // Agora obrigatório

    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    @JsonProperty("nivel_bem_estar")
    private String nivel_bem_estar;

    @JsonProperty("tendencias_humor")
    private String tendencias_humor;

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

    public boolean hasPesquisa() {
        return id_pesquisa > 0;
    }

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
}
package br.com.fiap.dto;

import jakarta.validation.constraints.NotBlank;

public class AtividadeRequestDto {

    @NotBlank(message = "A descrição da atividade é obrigatória.")
    private String descricao_atividade;

    @NotBlank(message = "O tipo da atividade é obrigatório.")
    private String tipo_atividade;

    @NotBlank(message = "A frequência recomendada é obrigatória.")
    private String frequencia_recomendada;


    private Integer id_relatorio;

    public AtividadeRequestDto() {}

    public AtividadeRequestDto(String descricao_atividade, String tipo_atividade,
                               String frequencia_recomendada, Integer id_relatorio) {
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
        this.id_relatorio = id_relatorio;
    }

    // Getters e Setters
    public String getDescricao_atividade() {
        return descricao_atividade;
    }

    public void setDescricao_atividade(String descricao_atividade) {
        this.descricao_atividade = descricao_atividade;
    }

    public String getTipo_atividade() {
        return tipo_atividade;
    }

    public void setTipo_atividade(String tipo_atividade) {
        this.tipo_atividade = tipo_atividade;
    }

    public String getFrequencia_recomendada() {
        return frequencia_recomendada;
    }

    public void setFrequencia_recomendada(String frequencia_recomendada) {
        this.frequencia_recomendada = frequencia_recomendada;
    }

    public Integer getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(Integer id_relatorio) {
        this.id_relatorio = id_relatorio;
    }
}
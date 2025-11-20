package br.com.fiap.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) para receber dados de requisição de Atividade.
 * Utilizado para validar e transportar dados de entrada para operações relacionadas a atividades.
 * Inclui anotações de validação para garantir a integridade dos dados recebidos.
 */
public class AtividadeRequestDto {

    @NotBlank(message = "A descrição da atividade é obrigatória.")
    private String descricao_atividade;

    @NotBlank(message = "O tipo da atividade é obrigatório.")
    private String tipo_atividade;

    @NotBlank(message = "A frequência recomendada é obrigatória.")
    private String frequencia_recomendada;

    private Integer id_relatorio;

    /**
     * Construtor padrão que inicializa um DTO vazio.
     * Necessário para frameworks de serialização/desserialização.
     */
    public AtividadeRequestDto() {}

    /**
     * Construtor completo com todos os campos do DTO.
     */
    public AtividadeRequestDto(String descricao_atividade, String tipo_atividade,
                               String frequencia_recomendada, Integer id_relatorio) {
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
        this.id_relatorio = id_relatorio;
    }


    /**
     *  Descrição detalhada da atividade
     */
    public String getDescricao_atividade() {
        return descricao_atividade;
    }

    /**
     *  descricao_atividade Descrição detalhada da atividade
     */
    public void setDescricao_atividade(String descricao_atividade) {
        this.descricao_atividade = descricao_atividade;
    }

    /**
     *  Tipo/categoria da atividade
     */
    public String getTipo_atividade() {
        return tipo_atividade;
    }

    /**
     *  tipo_atividade Tipo/categoria da atividade
     */
    public void setTipo_atividade(String tipo_atividade) {
        this.tipo_atividade = tipo_atividade;
    }

    /**
     * n Frequência recomendada para execução da atividade
     */
    public String getFrequencia_recomendada() {
        return frequencia_recomendada;
    }

    /**
     *  frequencia_recomendada Frequência recomendada para execução
     */
    public void setFrequencia_recomendada(String frequencia_recomendada) {
        this.frequencia_recomendada = frequencia_recomendada;
    }

    /**
     *  ID do relatório associado à atividade (pode ser null)
     */
    public Integer getId_relatorio() {
        return id_relatorio;
    }

    /**
     *  id_relatorio ID do relatório associado à atividade (pode ser null)
     */
    public void setId_relatorio(Integer id_relatorio) {
        this.id_relatorio = id_relatorio;
    }
}
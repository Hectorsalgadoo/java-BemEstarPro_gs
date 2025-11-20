package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO de resposta para informações de atividades registradas no sistema.
 * <p>
 * Este objeto é utilizado para retornar dados consolidados de uma atividade,
 * podendo incluir informações sobre o relatório associado e o nome do funcionário,
 * dependendo do contexto da consulta.
 * </p>
 *
 * <p>Campos opcionais são omitidos do JSON quando nulos.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtividadeResponseDto {

    /**
     * Identificador único da atividade.
     */
    @JsonProperty("id_atividade")
    private Integer idAtividade;

    /**
     * Descrição detalhada da atividade.
     */
    @JsonProperty("descricao_atividade")
    private String descricaoAtividade;

    /**
     * Tipo ou categoria da atividade (ex.: física, mental, laboral).
     */
    @JsonProperty("tipo_atividade")
    private String tipoAtividade;

    /**
     * Frequência recomendada para realização da atividade.
     */
    @JsonProperty("frequencia_recomendada")
    private String frequenciaRecomendada;

    /**
     * ID do relatório ao qual esta atividade está associada.
     */
    @JsonProperty("id_relatorio")
    private Integer idRelatorio;

    /**
     * Nome do funcionário relacionado a esta atividade, quando aplicável.
     */
    @JsonProperty("nome_funcionario")
    private String nomeFuncionario;

    /**
     * Construtor padrão.
     */
    public AtividadeResponseDto() {}

    /**
     * Construtor básico, utilizado quando há apenas informações da própria atividade.
     */
    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade,
                                String tipoAtividade, String frequenciaRecomendada) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
    }

    /**
     * Construtor intermediário, incluindo ID do relatório associado.
     */
    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade,
                                String tipoAtividade, String frequenciaRecomendada,
                                Integer idRelatorio) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
        this.idRelatorio = idRelatorio;
    }

    /**
     * Construtor completo, incluindo referência ao relatório e nome do funcionário.
     */
    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade,
                                String tipoAtividade, String frequenciaRecomendada,
                                Integer idRelatorio, String nomeFuncionario) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
        this.idRelatorio = idRelatorio;
        this.nomeFuncionario = nomeFuncionario;
    }


    /**
     *  ID da atividade
     */
    public Integer getIdAtividade() {
        return idAtividade;
    }

    /**
     *  idAtividade novo ID da atividade
     */
    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    /**@return descrição da atividade
     */
    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    /**
     *  descricaoAtividade nova descrição da atividade
     */
    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    /**
     *  tipo da atividade
     */
    public String getTipoAtividade() {
        return tipoAtividade;
    }

    /**
     *  tipoAtividade novo tipo da atividade
     */
    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    /**
     *  frequência recomendada
     */
    public String getFrequenciaRecomendada() {
        return frequenciaRecomendada;
    }

    /**
     *  frequenciaRecomendada nova frequência recomendada
     */
    public void setFrequenciaRecomendada(String frequenciaRecomendada) {
        this.frequenciaRecomendada = frequenciaRecomendada;
    }

    /**
     *  ID do relatório vinculado
     */
    public Integer getIdRelatorio() {
        return idRelatorio;
    }

    /**
     *  idRelatorio novo ID de relatório vinculado
     */
    public void setIdRelatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    /**
     *  nome do funcionário, caso presente
     */
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    /**
     *  nomeFuncionario novo nome do funcionário vinculado
     */
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
}

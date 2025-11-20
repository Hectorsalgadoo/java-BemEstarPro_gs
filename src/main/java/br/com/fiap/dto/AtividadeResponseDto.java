package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtividadeResponseDto {

    @JsonProperty("id_atividade")
    private Integer idAtividade;

    @JsonProperty("descricao_atividade")
    private String descricaoAtividade;

    @JsonProperty("tipo_atividade")
    private String tipoAtividade;

    @JsonProperty("frequencia_recomendada")
    private String frequenciaRecomendada;

    @JsonProperty("id_relatorio")
    private Integer idRelatorio;

    @JsonProperty("nome_funcionario")
    private String nomeFuncionario;

    public AtividadeResponseDto() {}

    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade,
                                String tipoAtividade, String frequenciaRecomendada) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
    }

    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade,
                                String tipoAtividade, String frequenciaRecomendada,
                                Integer idRelatorio) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
        this.idRelatorio = idRelatorio;
    }

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

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public String getFrequenciaRecomendada() {
        return frequenciaRecomendada;
    }

    public void setFrequenciaRecomendada(String frequenciaRecomendada) {
        this.frequenciaRecomendada = frequenciaRecomendada;
    }

    public Integer getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
}
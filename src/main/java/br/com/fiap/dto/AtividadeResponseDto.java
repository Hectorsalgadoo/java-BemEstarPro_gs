package br.com.fiap.dto;

public class AtividadeResponseDto {

    private Integer idAtividade;
    private String descricaoAtividade;
    private String tipoAtividade;
    private String frequenciaRecomendada;

    public AtividadeResponseDto() {}

    public AtividadeResponseDto(Integer idAtividade, String descricaoAtividade, String tipoAtividade, String frequenciaRecomendada) {
        this.idAtividade = idAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.tipoAtividade = tipoAtividade;
        this.frequenciaRecomendada = frequenciaRecomendada;
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
}

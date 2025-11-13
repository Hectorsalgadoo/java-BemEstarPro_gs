package br.com.fiap.models;

public class Atividade {

    private int id_atividade;
    private String descricao_atividade;
    private String tipo_atividade;
    private String frequencia_recomendada;

    public Atividade() {
    }

    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
    }

    public int getId_atividade() {
        return id_atividade;
    }

    public void setId_atividade(int id_atividade) {
        this.id_atividade = id_atividade;
    }

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

    @Override
    public String toString() {
        return "Atividade{" +
                "id_atividade=" + id_atividade +
                ", descricao_atividade='" + descricao_atividade + '\'' +
                ", tipo_atividade='" + tipo_atividade + '\'' +
                ", frequencia_recomendada='" + frequencia_recomendada + '\'' +
                '}';
    }
}

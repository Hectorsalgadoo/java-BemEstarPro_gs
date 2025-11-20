package br.com.fiap.models;

public class Atividade {

    private int id_atividade;
    private String descricao_atividade;
    private String tipo_atividade;
    private String frequencia_recomendada;
    private Integer id_relatorio;
    private String nome_funcionario;

    public Atividade() {
    }

    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
    }

    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada,
                     Integer id_relatorio) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
        this.id_relatorio = id_relatorio;
    }

    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada,
                     Integer id_relatorio, String nome_funcionario) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
        this.id_relatorio = id_relatorio;
        this.nome_funcionario = nome_funcionario;
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

    public Integer getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(Integer id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "id_atividade=" + id_atividade +
                ", descricao_atividade='" + descricao_atividade + '\'' +
                ", tipo_atividade='" + tipo_atividade + '\'' +
                ", frequencia_recomendada='" + frequencia_recomendada + '\'' +
                ", id_relatorio=" + id_relatorio +
                ", nome_funcionario='" + nome_funcionario + '\'' +
                '}';
    }
}
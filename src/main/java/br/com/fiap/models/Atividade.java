package br.com.fiap.models;

/**
 * Classe que representa uma Atividade no sistema.
 * Contém informações sobre atividades, suas descrições, tipos e frequências recomendadas.
 * Pode estar associada a relatórios e funcionários.
 */
public class Atividade {

    private int id_atividade;
    private String descricao_atividade;
    private String tipo_atividade;
    private String frequencia_recomendada;
    private Integer id_relatorio;
    private String nome_funcionario;

    /**
     * Construtor padrão que inicializa uma atividade vazia.
     * Útil para criar instâncias que serão preenchidas posteriormente.
     */
    public Atividade() {
    }

    /**
     * Construtor com informações básicas da atividade.
     */
    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
    }

    /**
     * Construtor com informações básicas e ID do relatório associado.
     */
    public Atividade(int id_atividade, String descricao_atividade,
                     String tipo_atividade, String frequencia_recomendada,
                     Integer id_relatorio) {
        this.id_atividade = id_atividade;
        this.descricao_atividade = descricao_atividade;
        this.tipo_atividade = tipo_atividade;
        this.frequencia_recomendada = frequencia_recomendada;
        this.id_relatorio = id_relatorio;
    }

    /**
     * Construtor completo com todas as informações da atividade.
     */
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

    /**
     * Retorna o ID único da atividade.
     */
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

    /**
     * Tipo/categoria da atividade
     */
    public String getTipo_atividade() {
        return tipo_atividade;
    }

    public void setTipo_atividade(String tipo_atividade) {
        this.tipo_atividade = tipo_atividade;
    }

    public String getFrequencia_recomendada() {
        return frequencia_recomendada;
    }

    /**
     * frequencia_recomendada Frequência recomendada para execução
     */
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

    /**
     * Retorna uma representação em string dos dados da atividade.
     */
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
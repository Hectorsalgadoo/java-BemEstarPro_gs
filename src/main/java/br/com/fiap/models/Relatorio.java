package br.com.fiap.models;

public class Relatorio {

    private int id_relatorio;
    private Funcionario funcionario;
    private int id_funcionario;
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;
    private int id_pesquisa; // Mantém como int
    private String resumo_feedback;
    private String nivel_bem_estar;
    private String tendencias_humor;

    public Relatorio() {
    }

    public int getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        if (funcionario != null) {
            this.id_funcionario = funcionario.getId();
        }
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public PesquisaRegimeTrabalho getPesquisaRegimeTrabalho() {
        return pesquisaRegimeTrabalho;
    }

    public void setPesquisaRegimeTrabalho(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
        this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
        if (pesquisaRegimeTrabalho != null) {
            this.id_pesquisa = pesquisaRegimeTrabalho.getId_pesquisa();
        }
    }

    public int getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public boolean hasPesquisa() {
        return id_pesquisa > 0;
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

    @Override
    public String toString() {
        return "Relatorio{" +
                "id_relatorio=" + id_relatorio +
                ", funcionario=" + funcionario +
                ", id_funcionario=" + id_funcionario +
                ", pesquisaRegimeTrabalho=" + pesquisaRegimeTrabalho +
                ", id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                '}';
    }
}
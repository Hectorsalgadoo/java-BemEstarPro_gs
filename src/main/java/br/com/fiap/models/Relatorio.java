package br.com.fiap.models;

public class Relatorio {

    private int id_relatorio;
    private Funcionario funcionario;
    private int id_funcionario;
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;
    private int id_pesquisa;
    private String resumo_feedback;
    private String nivel_bem_estar;
    private String tendencias_humor;

    // Construtores
    public Relatorio() {
    }

    public Relatorio(int id_funcionario, int id_pesquisa, String resumo_feedback,
                     String nivel_bem_estar, String tendencias_humor) {
        this.id_funcionario = id_funcionario;
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    public Relatorio(Funcionario funcionario, PesquisaRegimeTrabalho pesquisaRegimeTrabalho,
                     String resumo_feedback, String nivel_bem_estar, String tendencias_humor) {
        setFuncionario(funcionario);
        setPesquisaRegimeTrabalho(pesquisaRegimeTrabalho);
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    // Getters e Setters
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
        // Se já temos um funcionário com ID diferente, atualiza ou remove a referência
        if (this.funcionario != null && this.funcionario.getId() != id_funcionario) {
            this.funcionario = null; // Remove referência inconsistente
        }
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
        // Se já temos uma pesquisa com ID diferente, atualiza ou remove a referência
        if (this.pesquisaRegimeTrabalho != null && this.pesquisaRegimeTrabalho.getId_pesquisa() != id_pesquisa) {
            this.pesquisaRegimeTrabalho = null; // Remove referência inconsistente
        }
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

    // Métodos de validação e utilidade
    public boolean hasPesquisa() {
        return id_pesquisa > 0;
    }

    public boolean hasFuncionario() {
        return id_funcionario > 0;
    }

    public boolean isValid() {
        return hasFuncionario() &&
                resumo_feedback != null && !resumo_feedback.trim().isEmpty() &&
                nivel_bem_estar != null && !nivel_bem_estar.trim().isEmpty() &&
                tendencias_humor != null && !tendencias_humor.trim().isEmpty();
    }

    public String getValidationMessage() {
        if (!hasFuncionario()) {
            return "ID do funcionário é obrigatório";
        }
        if (resumo_feedback == null || resumo_feedback.trim().isEmpty()) {
            return "Resumo do feedback é obrigatório";
        }
        if (nivel_bem_estar == null || nivel_bem_estar.trim().isEmpty()) {
            return "Nível de bem-estar é obrigatório";
        }
        if (tendencias_humor == null || tendencias_humor.trim().isEmpty()) {
            return "Tendências de humor são obrigatórias";
        }
        return "VALID";
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

    // Método para criar uma cópia do relatório
    public Relatorio copy() {
        Relatorio copia = new Relatorio();
        copia.id_relatorio = this.id_relatorio;
        copia.id_funcionario = this.id_funcionario;
        copia.id_pesquisa = this.id_pesquisa;
        copia.resumo_feedback = this.resumo_feedback;
        copia.nivel_bem_estar = this.nivel_bem_estar;
        copia.tendencias_humor = this.tendencias_humor;
        // Nota: não copiamos as referências a objetos para evitar side effects
        return copia;
    }

    // Método para criar relatório a partir de DTO (simulação)
    public static Relatorio fromBasicData(int id_funcionario, int id_pesquisa,
                                          String resumo_feedback, String nivel_bem_estar,
                                          String tendencias_humor) {
        Relatorio relatorio = new Relatorio();
        relatorio.setId_funcionario(id_funcionario);
        relatorio.setId_pesquisa(id_pesquisa);
        relatorio.setResumo_feedback(resumo_feedback);
        relatorio.setNivel_bem_estar(nivel_bem_estar);
        relatorio.setTendencias_humor(tendencias_humor);
        return relatorio;
    }

    // Builder pattern
    public static class Builder {
        private int id_relatorio;
        private int id_funcionario;
        private int id_pesquisa;
        private String resumo_feedback;
        private String nivel_bem_estar;
        private String tendencias_humor;
        private Funcionario funcionario;
        private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;

        public Builder id_relatorio(int id_relatorio) {
            this.id_relatorio = id_relatorio;
            return this;
        }

        public Builder id_funcionario(int id_funcionario) {
            this.id_funcionario = id_funcionario;
            return this;
        }

        public Builder id_pesquisa(int id_pesquisa) {
            this.id_pesquisa = id_pesquisa;
            return this;
        }

        public Builder resumo_feedback(String resumo_feedback) {
            this.resumo_feedback = resumo_feedback;
            return this;
        }

        public Builder nivel_bem_estar(String nivel_bem_estar) {
            this.nivel_bem_estar = nivel_bem_estar;
            return this;
        }

        public Builder tendencias_humor(String tendencias_humor) {
            this.tendencias_humor = tendencias_humor;
            return this;
        }

        public Builder funcionario(Funcionario funcionario) {
            this.funcionario = funcionario;
            return this;
        }

        public Builder pesquisaRegimeTrabalho(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
            this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
            return this;
        }

        public Relatorio build() {
            Relatorio relatorio = new Relatorio();
            relatorio.setId_relatorio(this.id_relatorio);

            // Prioriza o objeto funcionário se disponível
            if (this.funcionario != null) {
                relatorio.setFuncionario(this.funcionario);
            } else {
                relatorio.setId_funcionario(this.id_funcionario);
            }

            // Prioriza o objeto pesquisa se disponível
            if (this.pesquisaRegimeTrabalho != null) {
                relatorio.setPesquisaRegimeTrabalho(this.pesquisaRegimeTrabalho);
            } else {
                relatorio.setId_pesquisa(this.id_pesquisa);
            }

            relatorio.setResumo_feedback(this.resumo_feedback);
            relatorio.setNivel_bem_estar(this.nivel_bem_estar);
            relatorio.setTendencias_humor(this.tendencias_humor);

            return relatorio;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "id_relatorio=" + id_relatorio +
                ", id_funcionario=" + id_funcionario +
                ", id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                ", funcionario=" + (funcionario != null ? funcionario.getNome() : "null") +
                ", pesquisaRegimeTrabalho=" + (pesquisaRegimeTrabalho != null ? pesquisaRegimeTrabalho.getId_pesquisa() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relatorio relatorio = (Relatorio) o;

        if (id_relatorio != relatorio.id_relatorio) return false;
        if (id_funcionario != relatorio.id_funcionario) return false;
        if (id_pesquisa != relatorio.id_pesquisa) return false;
        if (resumo_feedback != null ? !resumo_feedback.equals(relatorio.resumo_feedback) : relatorio.resumo_feedback != null)
            return false;
        if (nivel_bem_estar != null ? !nivel_bem_estar.equals(relatorio.nivel_bem_estar) : relatorio.nivel_bem_estar != null)
            return false;
        return tendencias_humor != null ? tendencias_humor.equals(relatorio.tendencias_humor) : relatorio.tendencias_humor == null;
    }

    @Override
    public int hashCode() {
        int result = id_relatorio;
        result = 31 * result + id_funcionario;
        result = 31 * result + id_pesquisa;
        result = 31 * result + (resumo_feedback != null ? resumo_feedback.hashCode() : 0);
        result = 31 * result + (nivel_bem_estar != null ? nivel_bem_estar.hashCode() : 0);
        result = 31 * result + (tendencias_humor != null ? tendencias_humor.hashCode() : 0);
        return result;
    }
}
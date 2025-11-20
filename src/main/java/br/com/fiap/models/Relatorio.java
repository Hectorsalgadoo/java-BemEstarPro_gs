package br.com.fiap.models;

/**
 * Classe que representa um Relatório no sistema.
 * Contém informações consolidadas sobre feedback, bem-estar e tendências de humor dos funcionários.
 * Pode estar associado a um funcionário e uma pesquisa de regime de trabalho.
 * Inclui métodos de validação, utilitários e suporte ao padrão Builder.
 */
public class Relatorio {

    private int id_relatorio;
    private Funcionario funcionario;
    private int id_funcionario;
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;
    private int id_pesquisa;
    private String resumo_feedback;
    private String nivel_bem_estar;
    private String tendencias_humor;

    /**
     * Construtor padrão que inicializa um relatório vazio.
     * Útil para criar instâncias que serão preenchidas posteriormente.
     */
    public Relatorio() {
    }

    /**
     * Construtor com dados básicos do relatório usando IDs.
     *
     *  id_funcionario ID do funcionário associado ao relatório
     *  id_pesquisa ID da pesquisa associada ao relatório
     * resumo_feedback Resumo consolidado do feedback
     * nivel_bem_estar Nível geral de bem-estar identificado
     *  tendencias_humor Tendências de humor observadas
     */
    public Relatorio(int id_funcionario, int id_pesquisa, String resumo_feedback,
                     String nivel_bem_estar, String tendencias_humor) {
        this.id_funcionario = id_funcionario;
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    /**
     * Construtor com objetos completos associados ao relatório.
     *
     *  funcionario Objeto Funcionario associado ao relatório
     *  pesquisaRegimeTrabalho Objeto PesquisaRegimeTrabalho associado
     *  resumo_feedback Resumo consolidado do feedback
     * nivel_bem_estar Nível geral de bem-estar identificado
     *  tendencias_humor Tendências de humor observadas
     */
    public Relatorio(Funcionario funcionario, PesquisaRegimeTrabalho pesquisaRegimeTrabalho,
                     String resumo_feedback, String nivel_bem_estar, String tendencias_humor) {
        setFuncionario(funcionario);
        setPesquisaRegimeTrabalho(pesquisaRegimeTrabalho);
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    /**
     *  ID único do relatório
     */
    public int getId_relatorio() {
        return id_relatorio;
    }

    /**
     * @param id_relatorio ID único do relatório
     */
    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    /**
     *  Objeto Funcionario associado ao relatório
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * Define o funcionário associado e atualiza automaticamente o ID do funcionário.
     *
     * @param funcionario Objeto Funcionario associado ao relatório
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        if (funcionario != null) {
            this.id_funcionario = funcionario.getId();
        }
    }

    /**
     * ID do funcionário associado ao relatório
     */
    public int getId_funcionario() {
        return id_funcionario;
    }

    /**
     * Define o ID do funcionário e remove referência inconsistente se houver.
     *
     * @param id_funcionario ID do funcionário associado ao relatório
     */
    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
        // Se já temos um funcionário com ID diferente, atualiza ou remove a referência
        if (this.funcionario != null && this.funcionario.getId() != id_funcionario) {
            this.funcionario = null; // Remove referência inconsistente
        }
    }

    /**
     *  Objeto PesquisaRegimeTrabalho associado ao relatório
     */
    public PesquisaRegimeTrabalho getPesquisaRegimeTrabalho() {
        return pesquisaRegimeTrabalho;
    }

    /**
     * Define a pesquisa associada e atualiza automaticamente o ID da pesquisa.

     */
    public void setPesquisaRegimeTrabalho(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
        this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
        if (pesquisaRegimeTrabalho != null) {
            this.id_pesquisa = pesquisaRegimeTrabalho.getId_pesquisa();
        }
    }

    /**
     *nID da pesquisa associada ao relatório
     */
    public int getId_pesquisa() {
        return id_pesquisa;
    }

    /**
     * Define o ID da pesquisa e remove referência inconsistente se houver.

     */
    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
        // Se já temos uma pesquisa com ID diferente, atualiza ou remove a referência
        if (this.pesquisaRegimeTrabalho != null && this.pesquisaRegimeTrabalho.getId_pesquisa() != id_pesquisa) {
            this.pesquisaRegimeTrabalho = null; // Remove referência inconsistente
        }
    }

    /**
     *  Resumo consolidado do feedback
     */
    public String getResumo_feedback() {
        return resumo_feedback;
    }

    /**
     * resumo_feedback Resumo consolidado do feedback
     */
    public void setResumo_feedback(String resumo_feedback) {
        this.resumo_feedback = resumo_feedback;
    }

    /**
     *  Nível geral de bem-estar identificado
     */
    public String getNivel_bem_estar() {
        return nivel_bem_estar;
    }

    /**
     *  nivel_bem_estar Nível geral de bem-estar identificado
     */
    public void setNivel_bem_estar(String nivel_bem_estar) {
        this.nivel_bem_estar = nivel_bem_estar;
    }

    /**
     *  Tendências de humor observadas
     */
    public String getTendencias_humor() {
        return tendencias_humor;
    }

    /**
     *  tendencias_humor Tendências de humor observadas
     */
    public void setTendencias_humor(String tendencias_humor) {
        this.tendencias_humor = tendencias_humor;
    }

    /**
     * Verifica se o relatório possui uma pesquisa associada.
     */
    public boolean hasPesquisa() {
        return id_pesquisa > 0;
    }

    /**
     * Verifica se o relatório possui um funcionário associado.
     */
    public boolean hasFuncionario() {
        return id_funcionario > 0;
    }

    /**
     * Valida se o relatório está completo e com dados válidos.
     */
    public boolean isValid() {
        return hasFuncionario() &&
                resumo_feedback != null && !resumo_feedback.trim().isEmpty() &&
                nivel_bem_estar != null && !nivel_bem_estar.trim().isEmpty() &&
                tendencias_humor != null && !tendencias_humor.trim().isEmpty();
    }

    /**
     * Retorna mensagem de validação indicando qual campo está inválido.
     */
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

    /**
     * Limpa e formata os dados de texto do relatório removendo espaços extras.
     */
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

    /**
     * Cria uma cópia superficial do relatório atual.
     * Nota: não copia as referências a objetos para evitar side effects.
     */
    public Relatorio copy() {
        Relatorio copia = new Relatorio();
        copia.id_relatorio = this.id_relatorio;
        copia.id_funcionario = this.id_funcionario;
        copia.id_pesquisa = this.id_pesquisa;
        copia.resumo_feedback = this.resumo_feedback;
        copia.nivel_bem_estar = this.nivel_bem_estar;
        copia.tendencias_humor = this.tendencias_humor;
        return copia;
    }

    /**
     * Método estático para criar relatório a partir de dados básicos.
     */
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

    /**
     * Classe Builder para construção fluente de objetos Relatorio.
     * Permite configurar o relatório passo a passo de forma mais legível.
     */
    public static class Builder {
        private int id_relatorio;
        private int id_funcionario;
        private int id_pesquisa;
        private String resumo_feedback;
        private String nivel_bem_estar;
        private String tendencias_humor;
        private Funcionario funcionario;
        private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;

        /**
         *  id_relatorio ID único do relatório
         *  Próprio builder para encadeamento
         */
        public Builder id_relatorio(int id_relatorio) {
            this.id_relatorio = id_relatorio;
            return this;
        }

        /**
         *  id_funcionario ID do funcionário associado
         *  Próprio builder para encadeamento
         */
        public Builder id_funcionario(int id_funcionario) {
            this.id_funcionario = id_funcionario;
            return this;
        }

        /**
         *  id_pesquisa ID da pesquisa associada
         *  Próprio builder para encadeamento
         */
        public Builder id_pesquisa(int id_pesquisa) {
            this.id_pesquisa = id_pesquisa;
            return this;
        }

        /**
         *  resumo_feedback Resumo consolidado do feedback
         *  Próprio builder para encadeamento
         */
        public Builder resumo_feedback(String resumo_feedback) {
            this.resumo_feedback = resumo_feedback;
            return this;
        }

        /**
         *  nivel_bem_estar Nível geral de bem-estar
         *  Próprio builder para encadeamento
         */
        public Builder nivel_bem_estar(String nivel_bem_estar) {
            this.nivel_bem_estar = nivel_bem_estar;
            return this;
        }

        /**
         *  tendencias_humor Tendências de humor observadas
         *  Próprio builder para encadeamento
         */
        public Builder tendencias_humor(String tendencias_humor) {
            this.tendencias_humor = tendencias_humor;
            return this;
        }

        /**
         *  funcionario Objeto Funcionario associado
         *  Próprio builder para encadeamento
         */
        public Builder funcionario(Funcionario funcionario) {
            this.funcionario = funcionario;
            return this;
        }

        /**
         *  pesquisaRegimeTrabalho Objeto PesquisaRegimeTrabalho associado
         *  Próprio builder para encadeamento
         */
        public Builder pesquisaRegimeTrabalho(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
            this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
            return this;
        }

        /**
         * Constrói o objeto Relatorio final com as configurações do builder.
         *
         *  Nova instância de Relatorio configurada
         */
        public Relatorio build() {
            Relatorio relatorio = new Relatorio();
            relatorio.setId_relatorio(this.id_relatorio);


            if (this.funcionario != null) {
                relatorio.setFuncionario(this.funcionario);
            } else {
                relatorio.setId_funcionario(this.id_funcionario);
            }

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

    /**
     * Cria um novo builder para construção fluente de Relatorio.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Retorna uma representação em string dos dados do relatório.
     */
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

    /**
     * Compara este relatório com outro objeto para verificar igualdade.
     */
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

    /**
     * Retorna o código hash do relatório para uso em coleções.
     */
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
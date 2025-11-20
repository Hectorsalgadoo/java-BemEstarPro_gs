package br.com.fiap.dto;

import br.com.fiap.models.Relatorio;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DTO responsável por representar a resposta de um Relatório consolidado,
 * contendo dados do relatório, informações do funcionário e dados da pesquisa,
 * quando disponível.
 *
 * <p>Este DTO é utilizado para transferir dados ao cliente de forma segura,
 * evitando exposição direta do modelo Relatorio.</p>
 */
@XmlRootElement
public class RelatorioResponseDto {

    /** Identificador único do relatório */
    @JsonProperty("id_relatorio")
    private int id_relatorio;

    /** Nome do funcionário relacionado ao relatório */
    @JsonProperty("nome_funcionario")
    private String nomeFuncionario;

    /** ID da pesquisa vinculada ao relatório (pode ser nulo) */
    @JsonProperty("id_pesquisa")
    private Integer id_pesquisa;

    /** Regime de trabalho informado na pesquisa */
    @JsonProperty("regime_trabalho")
    private String regimeTrabalho;

    /** Índice de satisfação informado na pesquisa */
    @JsonProperty("satisfacao")
    private Integer satisfacao;

    /** Comentário registrado na pesquisa */
    @JsonProperty("comentario_pesquisa")
    private String comentarioPesquisa;

    /** Resumo geral do feedback registrado no relatório */
    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    /** Nível de bem-estar geral reportado */
    @JsonProperty("nivel_bem_estar")
    private String nivel_bem_estar;

    /** Tendências de humor observadas */
    @JsonProperty("tendencias_humor")
    private String tendencias_humor;

    /**
     * Construtor padrão.
     */
    public RelatorioResponseDto() {
    }

    /**
     * Construtor completo para criação manual do DTO.
     */
    public RelatorioResponseDto(int id_relatorio, String nomeFuncionario,
                                Integer id_pesquisa, String regimeTrabalho, Integer satisfacao,
                                String comentarioPesquisa, String resumo_feedback,
                                String nivel_bem_estar, String tendencias_humor) {
        this.id_relatorio = id_relatorio;
        this.nomeFuncionario = nomeFuncionario;
        this.id_pesquisa = id_pesquisa;
        this.regimeTrabalho = regimeTrabalho;
        this.satisfacao = satisfacao;
        this.comentarioPesquisa = comentarioPesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    /**
     * Constrói um DTO com base no modelo {@link Relatorio}.
     *
     *  relatorio objeto de modelo Relatorio
     *  IllegalArgumentException se o relatório for nulo
     */
    public RelatorioResponseDto(Relatorio relatorio) {
        if (relatorio == null) {
            throw new IllegalArgumentException("Relatorio não pode ser nulo");
        }

        this.id_relatorio = relatorio.getId_relatorio();
        this.id_pesquisa = relatorio.hasPesquisa() ? relatorio.getId_pesquisa() : null;

        this.nomeFuncionario = extractNomeFuncionario(relatorio);
        this.regimeTrabalho = extractRegimeTrabalho(relatorio);
        this.satisfacao = extractSatisfacao(relatorio);
        this.comentarioPesquisa = extractComentario(relatorio);

        this.resumo_feedback = relatorio.getResumo_feedback();
        this.nivel_bem_estar = relatorio.getNivel_bem_estar();
        this.tendencias_humor = relatorio.getTendencias_humor();
    }


    /**
     * Obtém o nome do funcionário associado.
     * Retorna uma mensagem padrão caso não exista funcionário.
     */
    private String extractNomeFuncionario(Relatorio relatorio) {
        if (relatorio.getFuncionario() != null && relatorio.getFuncionario().getNome() != null) {
            return relatorio.getFuncionario().getNome();
        }
        return "Funcionário não informado";
    }

    /**
     * Obtém o regime de trabalho, caso haja uma pesquisa vinculada.
     */
    private String extractRegimeTrabalho(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            String regime = relatorio.getPesquisaRegimeTrabalho().getRegime_trabalho();
            return regime != null ? regime : "Não informado";
        }
        return null;
    }

    /**
     * Obtém o nível de satisfação registrado na pesquisa.
     */
    private Integer extractSatisfacao(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            return relatorio.getPesquisaRegimeTrabalho().getSatisfacao();
        }
        return null;
    }

    /**
     * Obtém o comentário registrado na pesquisa.
     */
    private String extractComentario(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            String comentario = relatorio.getPesquisaRegimeTrabalho().getComentario();
            return comentario != null ? comentario : "Sem comentários";
        }
        return null;
    }


    /**
     *  ID do relatório
     */
    public int getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    /**
     *  nome do funcionário associado
     */
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public Integer getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(Integer id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public String getRegimeTrabalho() {
        return regimeTrabalho;
    }

    public void setRegimeTrabalho(String regimeTrabalho) {
        this.regimeTrabalho = regimeTrabalho;
    }

    public Integer getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(Integer satisfacao) {
        this.satisfacao = satisfacao;
    }

    public String getComentarioPesquisa() {
        return comentarioPesquisa;
    }

    public void setComentarioPesquisa(String comentarioPesquisa) {
        this.comentarioPesquisa = comentarioPesquisa;
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


    /**
     * Verifica se há uma pesquisa associada ao relatório.
     */
    public boolean hasPesquisa() {
        return id_pesquisa != null && id_pesquisa > 0;
    }

    /**
     * Verifica se há dados válidos de funcionário.
     */
    public boolean hasFuncionario() {
        return nomeFuncionario != null && !nomeFuncionario.equals("Funcionário não informado");
    }

    /**
     * Retorna as primeiras 50 letras do resumo.
     */
    public String getResumoPreview() {
        if (resumo_feedback == null || resumo_feedback.length() <= 50) {
            return resumo_feedback;
        }
        return resumo_feedback.substring(0, 50) + "...";
    }

    /**
     * Converte automaticamente um objeto Relatorio para DTO.
     */
    public static RelatorioResponseDto convertToDto(Relatorio relatorio) {
        if (relatorio == null) return null;
        return new RelatorioResponseDto(relatorio);
    }

    /**
     * Builder para construção fluente de RelatorioResponseDto.
     */
    public static class Builder {
        private int id_relatorio;
        private String nomeFuncionario;
        private Integer id_pesquisa;
        private String regimeTrabalho;
        private Integer satisfacao;
        private String comentarioPesquisa;
        private String resumo_feedback;
        private String nivel_bem_estar;
        private String tendencias_humor;

        public Builder id_relatorio(int id_relatorio) {
            this.id_relatorio = id_relatorio;
            return this;
        }

        public Builder nomeFuncionario(String nomeFuncionario) {
            this.nomeFuncionario = nomeFuncionario;
            return this;
        }

        public Builder id_pesquisa(Integer id_pesquisa) {
            this.id_pesquisa = id_pesquisa;
            return this;
        }

        public Builder regimeTrabalho(String regimeTrabalho) {
            this.regimeTrabalho = regimeTrabalho;
            return this;
        }

        public Builder satisfacao(Integer satisfacao) {
            this.satisfacao = satisfacao;
            return this;
        }

        public Builder comentarioPesquisa(String comentarioPesquisa) {
            this.comentarioPesquisa = comentarioPesquisa;
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

        public RelatorioResponseDto build() {
            return new RelatorioResponseDto(
                    id_relatorio, nomeFuncionario, id_pesquisa, regimeTrabalho,
                    satisfacao, comentarioPesquisa, resumo_feedback,
                    nivel_bem_estar, tendencias_humor
            );
        }
    }

    /**
     * Cria um novo builder para este DTO.
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "RelatorioResponseDto{" +
                "id_relatorio=" + id_relatorio +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", id_pesquisa=" + id_pesquisa +
                ", regimeTrabalho='" + regimeTrabalho + '\'' +
                ", satisfacao=" + satisfacao +
                ", comentarioPesquisa='" + comentarioPesquisa + '\'' +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelatorioResponseDto that = (RelatorioResponseDto) o;

        if (id_relatorio != that.id_relatorio) return false;
        if (nomeFuncionario != null ? !nomeFuncionario.equals(that.nomeFuncionario) : that.nomeFuncionario != null)
            return false;
        if (id_pesquisa != null ? !id_pesquisa.equals(that.id_pesquisa) : that.id_pesquisa != null) return false;
        if (regimeTrabalho != null ? !regimeTrabalho.equals(that.regimeTrabalho) : that.regimeTrabalho != null)
            return false;
        if (satisfacao != null ? !satisfacao.equals(that.satisfacao) : that.satisfacao != null) return false;
        if (comentarioPesquisa != null ? !comentarioPesquisa.equals(that.comentarioPesquisa) : that.comentarioPesquisa != null)
            return false;
        if (resumo_feedback != null ? !resumo_feedback.equals(that.resumo_feedback) : that.resumo_feedback != null)
            return false;
        if (nivel_bem_estar != null ? !nivel_bem_estar.equals(that.nivel_bem_estar) : that.nivel_bem_estar != null)
            return false;
        return tendencias_humor != null ? tendencias_humor.equals(that.tendencias_humor) : that.tendencias_humor == null;
    }

    @Override
    public int hashCode() {
        int result = id_relatorio;
        result = 31 * result + (nomeFuncionario != null ? nomeFuncionario.hashCode() : 0);
        result = 31 * result + (id_pesquisa != null ? id_pesquisa.hashCode() : 0);
        result = 31 * result + (regimeTrabalho != null ? regimeTrabalho.hashCode() : 0);
        result = 31 * result + (satisfacao != null ? satisfacao.hashCode() : 0);
        result = 31 * result + (comentarioPesquisa != null ? comentarioPesquisa.hashCode() : 0);
        result = 31 * result + (resumo_feedback != null ? resumo_feedback.hashCode() : 0);
        result = 31 * result + (nivel_bem_estar != null ? nivel_bem_estar.hashCode() : 0);
        result = 31 * result + (tendencias_humor != null ? tendencias_humor.hashCode() : 0);
        return result;
    }
}

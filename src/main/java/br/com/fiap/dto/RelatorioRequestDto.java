package br.com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DTO utilizado para receber requisições de criação ou atualização
 * de relatórios de bem-estar dos funcionários.
 *
 * <p>Este objeto contém os dados enviados pelo cliente, incluindo:
 * resumo do feedback, nível de bem-estar, tendências de humor e a
 * referência à pesquisa relacionada.</p>
 */
@XmlRootElement
public class RelatorioRequestDto {

    /**
     * Identificador da pesquisa associada ao relatório.
     */
    @JsonProperty("id_pesquisa")
    private int id_pesquisa;

    /**
     * Resumo das principais observações e feedbacks coletados.
     */
    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    /**
     * Nível geral de bem-estar identificado no relatório.
     */
    @JsonProperty("nivel_bem_estar")
    private String nivel_bem_estar;

    /**
     * Descrição das tendências de humor observadas na análise.
     */
    @JsonProperty("tendencias_humor")
    private String tendencias_humor;

    /**
     * Construtor padrão utilizado por frameworks de serialização.
     */
    public RelatorioRequestDto() {}

    /**
     * Construtor completo utilizado para preencher todos os dados da requisição.
     */
    public RelatorioRequestDto(int id_pesquisa, String resumo_feedback,
                               String nivel_bem_estar, String tendencias_humor) {
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }


    /** ID da pesquisa vinculada ao relatório */
    public int getId_pesquisa() {
        return id_pesquisa;
    }

    /**
     * Define o ID da pesquisa.
     */
    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    /**  resumo do feedback */
    public String getResumo_feedback() {
        return resumo_feedback;
    }

    /**
     * Define o resumo do feedback da pesquisa.
     */
    public void setResumo_feedback(String resumo_feedback) {
        this.resumo_feedback = resumo_feedback;
    }

    /**  nível de bem-estar */
    public String getNivel_bem_estar() {
        return nivel_bem_estar;
    }

    /**
     * Define o nível de bem-estar.
     */
    public void setNivel_bem_estar(String nivel_bem_estar) {
        this.nivel_bem_estar = nivel_bem_estar;
    }

    /**  tendências de humor identificadas */
    public String getTendencias_humor() {
        return tendencias_humor;
    }

    /**
     * Define as tendências de humor observadas.
     *
     */
    public void setTendencias_humor(String tendencias_humor) {
        this.tendencias_humor = tendencias_humor;
    }


    /**
     * Indica se existe uma pesquisa vinculada a este relatório.
     */
    public boolean hasPesquisa() {
        return id_pesquisa > 0;
    }

    /**
     * Valida os dados do DTO verificando se todos os campos obrigatórios
     * foram preenchidos corretamente.
     */
    public boolean isValid() {
        return id_pesquisa > 0 &&
                resumo_feedback != null && !resumo_feedback.trim().isEmpty() &&
                nivel_bem_estar != null && !nivel_bem_estar.trim().isEmpty() &&
                tendencias_humor != null && !tendencias_humor.trim().isEmpty();
    }

    /**
     * Retorna uma mensagem explicando qual campo falhou na validação.
     */
    public String getValidationMessage() {
        if (id_pesquisa <= 0) {
            return "ID da pesquisa é obrigatório e deve ser maior que zero";
        }
        if (isEmpty(resumo_feedback)) {
            return "Resumo do feedback é obrigatório";
        }
        if (isEmpty(nivel_bem_estar)) {
            return "Nível de bem-estar é obrigatório";
        }
        if (isEmpty(tendencias_humor)) {
            return "Tendências de humor são obrigatórias";
        }
        return "VALID";
    }

    /** Função auxiliar para verificar campos vazios */
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Normaliza e limpa os dados, removendo espaços desnecessários.
     */
    public void cleanData() {
        if (resumo_feedback != null) resumo_feedback = resumo_feedback.trim();
        if (nivel_bem_estar != null) nivel_bem_estar = nivel_bem_estar.trim();
        if (tendencias_humor != null) tendencias_humor = tendencias_humor.trim();
    }

    /**
     * Cria uma cópia exata do DTO.
     */
    public RelatorioRequestDto copy() {
        return new RelatorioRequestDto(
                this.id_pesquisa,
                this.resumo_feedback,
                this.nivel_bem_estar,
                this.tendencias_humor
        );
    }

    @Override
    public String toString() {
        return "RelatorioRequestDto{" +
                "id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                ", nivel_bem_estar='" + nivel_bem_estar + '\'' +
                ", tendencias_humor='" + tendencias_humor + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelatorioRequestDto)) return false;

        RelatorioRequestDto that = (RelatorioRequestDto) o;

        if (id_pesquisa != that.id_pesquisa) return false;
        if (!safeEquals(resumo_feedback, that.resumo_feedback)) return false;
        if (!safeEquals(nivel_bem_estar, that.nivel_bem_estar)) return false;
        return safeEquals(tendencias_humor, that.tendencias_humor);
    }

    private boolean safeEquals(Object a, Object b) {
        return a != null ? a.equals(b) : b == null;
    }

    @Override
    public int hashCode() {
        int result = id_pesquisa;
        result = 31 * result + (resumo_feedback != null ? resumo_feedback.hashCode() : 0);
        result = 31 * result + (nivel_bem_estar != null ? nivel_bem_estar.hashCode() : 0);
        result = 31 * result + (tendencias_humor != null ? tendencias_humor.hashCode() : 0);
        return result;
    }
}

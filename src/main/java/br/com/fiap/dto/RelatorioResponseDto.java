package br.com.fiap.dto;

import br.com.fiap.models.Relatorio;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioResponseDto {

    @JsonProperty("id_relatorio")
    private int id_relatorio;

    @JsonProperty("id_funcionario")
    private int id_funcionario;

    @JsonProperty("nome_funcionario")
    private String nomeFuncionario;

    @JsonProperty("id_pesquisa")
    private Integer id_pesquisa;

    @JsonProperty("regime_trabalho")
    private String regimeTrabalho;

    @JsonProperty("satisfacao")
    private Integer satisfacao;

    @JsonProperty("comentario_pesquisa")
    private String comentarioPesquisa;

    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    @JsonProperty("nivel_bem_estar")
    private String nivel_bem_estar;

    @JsonProperty("tendencias_humor")
    private String tendencias_humor;

    public RelatorioResponseDto() {
    }

    public RelatorioResponseDto(int id_relatorio, int id_funcionario, String nomeFuncionario,
                                Integer id_pesquisa, String regimeTrabalho, Integer satisfacao,
                                String comentarioPesquisa, String resumo_feedback,
                                String nivel_bem_estar, String tendencias_humor) {
        this.id_relatorio = id_relatorio;
        this.id_funcionario = id_funcionario;
        this.nomeFuncionario = nomeFuncionario;
        this.id_pesquisa = id_pesquisa;
        this.regimeTrabalho = regimeTrabalho;
        this.satisfacao = satisfacao;
        this.comentarioPesquisa = comentarioPesquisa;
        this.resumo_feedback = resumo_feedback;
        this.nivel_bem_estar = nivel_bem_estar;
        this.tendencias_humor = tendencias_humor;
    }

    public RelatorioResponseDto(Relatorio relatorio) {
        this.id_relatorio = relatorio.getId_relatorio();
        this.id_funcionario = relatorio.getId_funcionario();

        // Converte 0 para null no JSON
        this.id_pesquisa = relatorio.hasPesquisa() ? relatorio.getId_pesquisa() : null;

        // Obtém os dados da pesquisa, se existir
        this.regimeTrabalho = extractRegimeTrabalho(relatorio);
        this.satisfacao = extractSatisfacao(relatorio);
        this.comentarioPesquisa = extractComentario(relatorio);

        this.nomeFuncionario = extractNomeFuncionario(relatorio);
        this.resumo_feedback = relatorio.getResumo_feedback();
        this.nivel_bem_estar = relatorio.getNivel_bem_estar();
        this.tendencias_humor = relatorio.getTendencias_humor();
    }

    private String extractNomeFuncionario(Relatorio relatorio) {
        if (relatorio.getFuncionario() != null) {
            try {
                return relatorio.getFuncionario().getNome();
            } catch (Exception e) {
                try {
                    return relatorio.getFuncionario().getNome();
                } catch (Exception e2) {
                    return null;
                }
            }
        }
        return null;
    }

    private String extractRegimeTrabalho(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            try {
                return relatorio.getPesquisaRegimeTrabalho().getRegime_trabalho();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private Integer extractSatisfacao(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            try {
                return relatorio.getPesquisaRegimeTrabalho().getSatisfacao();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private String extractComentario(Relatorio relatorio) {
        if (relatorio.hasPesquisa() && relatorio.getPesquisaRegimeTrabalho() != null) {
            try {
                return relatorio.getPesquisaRegimeTrabalho().getComentario();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    // Getters e Setters para todos os campos...
    public int getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

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

    @Override
    public String toString() {
        return "RelatorioResponseDto{" +
                "id_relatorio=" + id_relatorio +
                ", id_funcionario=" + id_funcionario +
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

    public static RelatorioResponseDto convertToDto(Relatorio relatorio) {
        if (relatorio == null) return null;
        return new RelatorioResponseDto(relatorio);
    }
}
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

    @JsonProperty("id_pesquisa")
    private int id_pesquisa;

    @JsonProperty("resumo_feedback")
    private String resumo_feedback;

    public RelatorioResponseDto() {
    }

    public RelatorioResponseDto(int id_relatorio, int id_funcionario, int id_pesquisa, String resumo_feedback) {
        this.id_relatorio = id_relatorio;
        this.id_funcionario = id_funcionario;
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
    }

    public RelatorioResponseDto(Relatorio relatorio) {
        this.id_relatorio = relatorio.getId_relatorio();
        this.id_funcionario = relatorio.getId_funcionario();
        this.id_pesquisa = relatorio.getId_pesquisa();
        this.resumo_feedback = relatorio.getResumo_feedback();
    }


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

    public int getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(int id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public String getResumo_feedback() {
        return resumo_feedback;
    }

    public void setResumo_feedback(String resumo_feedback) {
        this.resumo_feedback = resumo_feedback;
    }

    @Override
    public String toString() {
        return "RelatorioResponseDto{" +
                "id_relatorio=" + id_relatorio +
                ", id_funcionario=" + id_funcionario +
                ", id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                '}';
    }

    public static RelatorioResponseDto convertToDto(Relatorio relatorio) {
        if (relatorio == null) return null;

        return new RelatorioResponseDto(
                relatorio.getId_relatorio(),
                relatorio.getId_funcionario(),
                relatorio.getId_pesquisa(),
                relatorio.getResumo_feedback()
        );
    }
}

package br.com.fiap.dto;

import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import br.com.fiap.models.Relatorio;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioResponseDto {

    @JsonProperty("id_relatorio")
    private int id_relatorio;
    private Funcionario funcionario;
    private int id_funcionario;
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;
    private int id_pesquisa;
    private String resumo_feedback;

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

    public RelatorioResponseDto() {
    }

    public RelatorioResponseDto(int id_relatorio, Funcionario funcionario, int id_funcionario, PesquisaRegimeTrabalho pesquisaRegimeTrabalho, int id_pesquisa, String resumo_feedback) {
        this.id_relatorio = id_relatorio;
        this.funcionario = funcionario;
        this.id_funcionario = id_funcionario;
        this.pesquisaRegimeTrabalho = pesquisaRegimeTrabalho;
        this.id_pesquisa = id_pesquisa;
        this.resumo_feedback = resumo_feedback;
    }

    public RelatorioResponseDto(Relatorio relatorio) {
        this.id_relatorio = relatorio.getId_relatorio();
        this.funcionario = relatorio.getFuncionario();
        this.id_funcionario = relatorio.getId_funcionario();
        this.pesquisaRegimeTrabalho = relatorio.getPesquisaRegimeTrabalho();
        this.id_pesquisa = relatorio.getId_pesquisa();
        this.resumo_feedback = relatorio.getResumo_feedback();
    }


    @Override
    public String toString() {
        return "RelatorioResponseDto{" +
                "id_relatorio=" + id_relatorio +
                ", funcionario=" + funcionario +
                ", id_funcionario=" + id_funcionario +
                ", pesquisaRegimeTrabalho=" + pesquisaRegimeTrabalho +
                ", id_pesquisa=" + id_pesquisa +
                ", resumo_feedback='" + resumo_feedback + '\'' +
                '}';
    }

    public static RelatorioResponseDto convertToDto(Relatorio relatorio) {
        if (relatorio == null) {
            return null;
        }

        RelatorioResponseDto dto = new RelatorioResponseDto();
        dto.setId_relatorio(relatorio.getId_relatorio());
        dto.setId_funcionario(relatorio.getId_funcionario());
        dto.setId_pesquisa(relatorio.getId_pesquisa());
        dto.setResumo_feedback(relatorio.getResumo_feedback());
        dto.setFuncionario(relatorio.getFuncionario());
        dto.setPesquisaRegimeTrabalho(relatorio.getPesquisaRegimeTrabalho());

        return dto;
    }
}

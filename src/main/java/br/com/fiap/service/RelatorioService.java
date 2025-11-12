package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.RelatorioDao;
import br.com.fiap.dto.PesquisaRegimeTrabalhoRequestDto;
import br.com.fiap.dto.PesquisaRegimeTrabalhoResponseDto;
import br.com.fiap.dto.RelatorioRequestDto;
import br.com.fiap.dto.RelatorioResponseDto;
import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import br.com.fiap.models.Relatorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RelatorioService {

    @Inject
    private RelatorioDao relatorioDao;

    @Inject
    private FuncionarioDao funcionarioDao;

    @Inject
    private PesquisaRegimeTrabalho pesquisaRegimeTrabalho;

    public List<RelatorioResponseDto> listar() {
        List<Relatorio> relatorio = relatorioDao.listRelatorio();
        return relatorio.stream()
                .map(RelatorioResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    public RelatorioResponseDto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatorio deve ser positivo");
        }

        Relatorio relatorio = relatorioDao.buscarPorIdRelatorio(id);
        if (relatorio == null) {
            throw new NotFoundException("Relatorio com  ID " + id + " não encontrada");
        }
        return RelatorioResponseDto.convertToDto(relatorio);
    }

    public Relatorio cadastrar(RelatorioRequestDto relatorioDto) throws SQLException {

        if (relatorioDto.getId_funcionario() <= 0) {
            throw new IllegalArgumentException("ID do funcionario é obrigatório e deve ser positivo");
        }

        Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(relatorioDto.getId_funcionario());
        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "Funcionario com ID " + relatorioDto.getId_funcionario() + " não encontrado"
            );
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setId_funcionario(relatorioDto.getId_funcionario());
        relatorio.setFuncionario(funcionario);
        relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
        relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());
        relatorio.setPesquisaRegimeTrabalho(relatorioDto.getPesquisaRegimeTrabalho());

        try {
            relatorioDao.cadastrarRelatorio(relatorio);
            return relatorio;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar o relatório: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar o relatório", e);
        }
    }

    public void atualizar(RelatorioRequestDto relatorioDto, int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo para atualização");
        }

        Relatorio relatorioExistente = relatorioDao.buscarPorIdRelatorio(id);
        if (relatorioExistente == null) {
            throw new IllegalArgumentException("Relatório com ID " + id + " não encontrado");
        }


        if (relatorioDto.getId_funcionario() <= 0) {
            throw new IllegalArgumentException("ID do funcionário é obrigatório e deve ser positivo");
        }

        Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(relatorioDto.getId_funcionario());
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário com ID " + relatorioDto.getId_funcionario() + " não encontrado");
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setId_relatorio(id);
        relatorio.setId_funcionario(relatorioDto.getId_funcionario());
        relatorio.setFuncionario(funcionario);
        relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
        relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());
        relatorio.setPesquisaRegimeTrabalho(relatorioDto.getPesquisaRegimeTrabalho());

        relatorioDao.atualizar(relatorio);
        System.out.println(" Relatório atualizado com sucesso!");
    }

    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatorio deve ser positivo para exclusão");
        }
        buscarPorId(id);
        try {
            relatorioDao.excluirRelatorio(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir a pesquisa", e);
        }
    }


}

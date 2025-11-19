package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.RelatorioDao;
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

    public List<RelatorioResponseDto> listar() {
        List<Relatorio> relatorios = relatorioDao.listRelatorio();
        return relatorios.stream()
                .map(RelatorioResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    public RelatorioResponseDto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }

        Relatorio relatorio = relatorioDao.buscarPorIdRelatorio(id);
        if (relatorio == null) {
            throw new NotFoundException("Relatório com ID " + id + " não encontrado");
        }

        return RelatorioResponseDto.convertToDto(relatorio);
    }

    public Relatorio cadastrar(RelatorioRequestDto relatorioDto) throws SQLException {
        relatorioDto.cleanData();

        if (relatorioDto.getId_pesquisa() <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo");
        }

        if (relatorioDto.getResumo_feedback() == null || relatorioDto.getResumo_feedback().trim().isEmpty()) {
            throw new IllegalArgumentException("Resumo do feedback é obrigatório");
        }

        if (relatorioDto.getNivel_bem_estar() == null || relatorioDto.getNivel_bem_estar().trim().isEmpty()) {
            throw new IllegalArgumentException("Nível de bem-estar é obrigatório");
        }

        if (relatorioDto.getTendencias_humor() == null || relatorioDto.getTendencias_humor().trim().isEmpty()) {
            throw new IllegalArgumentException("Tendências de humor são obrigatórias");
        }

        Funcionario funcionario = funcionarioDao.buscarFuncionarioPorPesquisa(relatorioDto.getId_pesquisa());
        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "Pesquisa com ID " + relatorioDto.getId_pesquisa() + " não encontrada ou sem funcionário vinculado"
            );
        }

        Relatorio relatorio = new Relatorio();

        relatorio.setId_funcionario(funcionario.getId());

        relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());

        relatorio.setFuncionario(funcionario);
        relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
        relatorio.setNivel_bem_estar(relatorioDto.getNivel_bem_estar());
        relatorio.setTendencias_humor(relatorioDto.getTendencias_humor());

        try {
            relatorioDao.cadastrarRelatorio(relatorio);
            return relatorio;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar o relatório: " + e.getMessage(), e);
        }
    }

    public void atualizar(RelatorioRequestDto relatorioDto, int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }

        // Verifica se o relatório existe
        Relatorio relatorioExistente = relatorioDao.buscarPorIdRelatorio(id);
        if (relatorioExistente == null) {
            throw new NotFoundException("Relatório com ID " + id + " não encontrado");
        }

        relatorioDto.cleanData();

        // Validações básicas
        if (relatorioDto.getId_pesquisa() <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo");
        }

        if (relatorioDto.getResumo_feedback() == null || relatorioDto.getResumo_feedback().trim().isEmpty()) {
            throw new IllegalArgumentException("Resumo do feedback é obrigatório");
        }

        if (relatorioDto.getNivel_bem_estar() == null || relatorioDto.getNivel_bem_estar().trim().isEmpty()) {
            throw new IllegalArgumentException("Nível de bem-estar é obrigatório");
        }

        if (relatorioDto.getTendencias_humor() == null || relatorioDto.getTendencias_humor().trim().isEmpty()) {
            throw new IllegalArgumentException("Tendências de humor são obrigatórias");
        }

        // Busca a pesquisa para obter o funcionário automaticamente
        Funcionario funcionario = funcionarioDao.buscarFuncionarioPorPesquisa(relatorioDto.getId_pesquisa());
        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "Pesquisa com ID " + relatorioDto.getId_pesquisa() + " não encontrada ou sem funcionário vinculado"
            );
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setId_relatorio(id);

        // Seta o id_funcionario automaticamente da pesquisa
        relatorio.setId_funcionario(funcionario.getId());

        // Seta id_pesquisa
        relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());

        relatorio.setFuncionario(funcionario);
        relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
        relatorio.setNivel_bem_estar(relatorioDto.getNivel_bem_estar());
        relatorio.setTendencias_humor(relatorioDto.getTendencias_humor());

        relatorioDao.atualizar(relatorio);
    }

    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }
        buscarPorId(id);
        relatorioDao.excluirRelatorio(id);
    }
}
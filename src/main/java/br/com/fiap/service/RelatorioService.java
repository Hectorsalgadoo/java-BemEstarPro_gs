package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.RelatorioDao;
import br.com.fiap.dto.RelatorioRequestDto;
import br.com.fiap.dto.RelatorioResponseDto;
import br.com.fiap.models.Funcionario;
import br.com.fiap.models.Relatorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio relacionadas a relatórios.
 * Responsável por orquestrar as operações entre controllers e DAOs,
 * aplicando regras de negócio, validações e convertendo entre DTOs e entidades.
 * Inclui validações de dados, busca de funcionários por pesquisa e tratamento de integridade referencial.
 *
 */
@ApplicationScoped
public class RelatorioService {

    @Inject
    private RelatorioDao relatorioDao;

    @Inject
    private FuncionarioDao funcionarioDao;

    /**
     * Lista todos os relatórios cadastrados no sistema.
     * Converte as entidades Relatorio para DTOs de resposta.
     */
    public List<RelatorioResponseDto> listar() {
        try {
            List<Relatorio> relatorios = relatorioDao.listRelatorio();
            return relatorios.stream()
                    .map(RelatorioResponseDto::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar relatórios: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um relatório específico pelo seu ID.
     */
    public RelatorioResponseDto buscarPorId(int id) {
        validarIdRelatorio(id);

        try {
            Relatorio relatorio = relatorioDao.buscarPorIdRelatorio(id);
            if (relatorio == null) {
                throw new NotFoundException("Relatório com ID " + id + " não encontrado");
            }
            return RelatorioResponseDto.convertToDto(relatorio);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar relatório ID " + id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Cadastra um novo relatório no sistema.
     * Realiza validações dos dados, busca o funcionário associado à pesquisa e persiste no banco.
     */
    public RelatorioResponseDto cadastrar(RelatorioRequestDto relatorioDto) throws SQLException {
        validarDto(relatorioDto);
        relatorioDto.cleanData();

        try {
            Funcionario funcionario = buscarFuncionarioPorPesquisa(relatorioDto.getId_pesquisa());
            Relatorio relatorio = criarRelatorio(relatorioDto, funcionario);

            relatorioDao.cadastrarRelatorio(relatorio);
            return RelatorioResponseDto.convertToDto(relatorio);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar relatório: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um relatório existente.
     * Realiza validações dos dados, busca o funcionário associado e atualiza no banco.
     */
    public void atualizar(RelatorioRequestDto relatorioDto, int id) throws SQLException {
        validarIdRelatorio(id);
        validarDto(relatorioDto);
        relatorioDto.cleanData();

        try {
            Relatorio relatorioExistente = relatorioDao.buscarPorIdRelatorio(id);
            if (relatorioExistente == null) {
                throw new NotFoundException("Relatório com ID " + id + " não encontrado");
            }

            Funcionario funcionario = buscarFuncionarioPorPesquisa(relatorioDto.getId_pesquisa());
            Relatorio relatorio = criarRelatorio(relatorioDto, funcionario);
            relatorio.setId_relatorio(id);

            relatorioDao.atualizar(relatorio);

        } catch (NotFoundException | IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar relatório ID " + id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Exclui um relatório do sistema pelo seu ID.
     * Verifica se o relatório existe antes de realizar a exclusão.
     */
    public void excluir(int id) {
        validarIdRelatorio(id);

        try {
            Relatorio relatorio = relatorioDao.buscarPorIdRelatorio(id);
            if (relatorio == null) {
                throw new NotFoundException("Relatório com ID " + id + " não encontrado");
            }

            relatorioDao.excluirRelatorio(id);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir relatório ID " + id + ": " + e.getMessage(), e);
        }
    }


    /**
     * Valida se o ID do relatório é válido (positivo).
     */
    private void validarIdRelatorio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }
    }

    /**
     * Valida os dados do DTO de requisição do relatório.
     * Verifica se os campos obrigatórios estão preenchidos corretamente.
     */
    private void validarDto(RelatorioRequestDto relatorioDto) {
        if (relatorioDto == null) {
            throw new IllegalArgumentException("DTO do relatório não pode ser nulo");
        }

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
    }

    /**
     * Busca o funcionário associado a uma pesquisa específica.
     */
    private Funcionario buscarFuncionarioPorPesquisa(int idPesquisa) {
        Funcionario funcionario = funcionarioDao.buscarFuncionarioPorPesquisa(idPesquisa);
        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "Pesquisa com ID " + idPesquisa + " não encontrada ou sem funcionário vinculado"
            );
        }
        return funcionario;
    }

    /**
     * Cria uma entidade Relatorio a partir do DTO de requisição e do funcionário associado.
     */
    private Relatorio criarRelatorio(RelatorioRequestDto relatorioDto, Funcionario funcionario) {
        Relatorio relatorio = new Relatorio();

        relatorio.setId_funcionario(funcionario.getId());
        relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());
        relatorio.setFuncionario(funcionario);
        relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
        relatorio.setNivel_bem_estar(relatorioDto.getNivel_bem_estar());
        relatorio.setTendencias_humor(relatorioDto.getTendencias_humor());

        return relatorio;
    }
}
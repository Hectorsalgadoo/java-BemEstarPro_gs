package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.PesquisaRegimeTrabalhoDao;
import br.com.fiap.dto.PesquisaRegimeTrabalhoRequestDto;
import br.com.fiap.dto.PesquisaRegimeTrabalhoResponseDto;
import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio relacionadas a pesquisas de regime de trabalho.
 * Responsável por orquestrar as operações entre controllers e DAOs,
 * aplicando regras de negócio, validações e convertendo entre DTOs e entidades.
 * Inclui validações de funcionário associado e tratamento de integridade referencial.
 *
 */
@ApplicationScoped
public class PesquisaRegimeTrabalhoService {

    @Inject
    private PesquisaRegimeTrabalhoDao pesquisaRegimeTrabalhoDao;

    @Inject
    private FuncionarioDao funcionarioDao;

    /**
     * Lista todas as pesquisas de regime de trabalho cadastradas no sistema.
     * Converte as entidades PesquisaRegimeTrabalho para DTOs de resposta.
     *
     */
    public List<PesquisaRegimeTrabalhoResponseDto> listar() {
        List<PesquisaRegimeTrabalho> pesquisaRegime = pesquisaRegimeTrabalhoDao.listarPesquisa();
        return pesquisaRegime.stream()
                .map(PesquisaRegimeTrabalhoResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma pesquisa de regime de trabalho específica pelo seu ID.
     */
    public PesquisaRegimeTrabalhoResponseDto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo");
        }

        PesquisaRegimeTrabalho pesquisaRegimeTrabalho = pesquisaRegimeTrabalhoDao.buscarPorIdPesquisa(id);
        if (pesquisaRegimeTrabalho == null) {
            throw new NotFoundException("Pesquisa com  ID " + id + " não encontrada");
        }
        return PesquisaRegimeTrabalhoResponseDto.convertToDto(pesquisaRegimeTrabalho);
    }

    /**
     * Cadastra uma nova pesquisa de regime de trabalho no sistema.
     * Realiza validações do funcionário associado e persiste no banco.
     */
    public PesquisaRegimeTrabalho cadastrar(PesquisaRegimeTrabalhoRequestDto pesquisaDto) throws SQLException {
        PesquisaRegimeTrabalho pesquisa = new PesquisaRegimeTrabalho();
        pesquisa.setSatisfacao(pesquisaDto.getSatisfacao());
        pesquisa.setRegime_trabalho(pesquisaDto.getRegime_trabalho());
        pesquisa.setComentario(pesquisaDto.getComentario());

        if (pesquisaDto.getIdfuncionario() == null || pesquisaDto.getIdfuncionario() <= 0) {
            throw new IllegalArgumentException("ID do funcionario é obrigatório");
        }
        Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(pesquisaDto.getIdfuncionario());
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + pesquisaDto.getIdfuncionario() + " não encontrado");
        }
        pesquisa.setIdfuncionario(pesquisaDto.getIdfuncionario());

        pesquisa.setFuncionario(funcionario);

        try {
            pesquisaRegimeTrabalhoDao.cadastrarPesquisa(pesquisa);
            return pesquisa;
        } catch (Exception e) {
            System.out.println("Erro detalhado ao cadastrar a pesquisa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar a pesquisa: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de uma pesquisa de regime de trabalho existente.
     * Realiza validações do ID, funcionário associado e atualiza no banco.
     */
    public void atualizar(PesquisaRegimeTrabalhoRequestDto pesquisaDto, int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo para atualização");
        }

        buscarPorId(id);

        PesquisaRegimeTrabalho pesquisaRegimeTrabalho = new PesquisaRegimeTrabalho();
        pesquisaRegimeTrabalho.setId_pesquisa(id);
        pesquisaRegimeTrabalho.setSatisfacao(pesquisaDto.getSatisfacao());
        pesquisaRegimeTrabalho.setRegime_trabalho(pesquisaDto.getRegime_trabalho());
        pesquisaRegimeTrabalho.setComentario(pesquisaDto.getComentario());

        if (pesquisaDto.getIdfuncionario() == null || pesquisaDto.getIdfuncionario() <= 0) {
            throw new IllegalArgumentException("ID do funcionario é obrigatório");
        }
        Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(pesquisaDto.getIdfuncionario());
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + pesquisaDto.getIdfuncionario() + " não encontrado");
        }
        pesquisaRegimeTrabalho.setIdfuncionario(pesquisaDto.getIdfuncionario());
        pesquisaRegimeTrabalho.setFuncionario(funcionario);

        try {
            pesquisaRegimeTrabalhoDao.atualizar(pesquisaRegimeTrabalho);
        } catch (Exception e) {
            System.out.println("Erro detalhado ao atualizar a pesquisa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar a pesquisa: " + e.getMessage(), e);
        }
    }

    /**
     * Exclui uma pesquisa de regime de trabalho do sistema pelo seu ID.
     * Verifica se a pesquisa existe antes de realizar a exclusão.
     */
    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo para exclusão");
        }
        buscarPorId(id);

        try {
            pesquisaRegimeTrabalhoDao.excluirPesquisa(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir a pesquisa", e);
        }
    }
}
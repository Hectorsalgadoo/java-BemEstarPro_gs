package br.com.fiap.service;

import br.com.fiap.dao.AtividadeDao;
import br.com.fiap.dto.AtividadeRequestDto;
import br.com.fiap.dto.AtividadeResponseDto;
import br.com.fiap.models.Atividade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio relacionadas a atividades.
 * Responsável por orquestrar as operações entre controllers e DAOs,
 * aplicando regras de negócio e convertendo entre DTOs e entidades.
 *
 */
@ApplicationScoped
public class AtividadeService {

    @Inject
    AtividadeDao atividadeDao;

    /**
     * Cria uma nova atividade no sistema.
     * Converte o DTO de requisição para entidade, persiste no banco e retorna o DTO de resposta.
     *
     *  dto DTO contendo os dados da atividade a ser criada
     *  DTO de resposta com os dados da atividade criada, incluindo ID gerado
     *  RuntimeException Se ocorrer erro durante a persistência
     */
    public AtividadeResponseDto criar(AtividadeRequestDto dto) {
        Atividade atividade = new Atividade();
        atividade.setDescricao_atividade(dto.getDescricao_atividade());
        atividade.setTipo_atividade(dto.getTipo_atividade());
        atividade.setFrequencia_recomendada(dto.getFrequencia_recomendada());
        atividade.setId_relatorio(dto.getId_relatorio());

        atividadeDao.inserir(atividade);

        return convertToDto(atividade);
    }

    /**
     * Lista todas as atividades cadastradas no sistema.
     * Inclui o nome do funcionário associado através do relatório.
     *
     *  Lista de DTOs de resposta com todas as atividades
     *  RuntimeException Se ocorrer erro durante a consulta
     */
    public List<AtividadeResponseDto> listar() {
        return atividadeDao.listarTodos()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma atividade específica pelo seu ID.
     *
     *  id ID da atividade a ser buscada
     *  DTO de resposta com os dados da atividade encontrada
     *  RuntimeException Se a atividade não for encontrada
     */
    public AtividadeResponseDto buscarPorId(Integer id) {
        Atividade atividade = atividadeDao.buscarPorId(id);

        if (atividade == null) {
            throw new RuntimeException("Atividade não encontrada");
        }

        return convertToDto(atividade);
    }

    /**
     * Atualiza os dados de uma atividade existente.
     * Busca a atividade, atualiza seus dados e retorna a versão atualizada.
     */
    public AtividadeResponseDto atualizar(Integer id, AtividadeRequestDto dto) {
        Atividade atividade = atividadeDao.buscarPorId(id);

        if (atividade == null) {
            throw new RuntimeException("Atividade não encontrada para atualização");
        }

        atividade.setDescricao_atividade(dto.getDescricao_atividade());
        atividade.setTipo_atividade(dto.getTipo_atividade());
        atividade.setFrequencia_recomendada(dto.getFrequencia_recomendada());
        atividade.setId_relatorio(dto.getId_relatorio());

        atividadeDao.atualizar(atividade);

        Atividade atividadeAtualizada = atividadeDao.buscarPorId(id);
        return convertToDto(atividadeAtualizada);
    }

    /**
     * Exclui uma atividade do sistema pelo seu ID.
     */
    public boolean deletar(Integer id) {
        try {
            return atividadeDao.deletar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar atividade: " + e.getMessage(), e);
        }
    }

    /**
     * Converte uma entidade Atividade para um DTO de resposta.
     * Método auxiliar que realiza o mapeamento entre a entidade de domínio e o DTO de apresentação.
     */
    private AtividadeResponseDto convertToDto(Atividade atividade) {
        return new AtividadeResponseDto(
                atividade.getId_atividade(),
                atividade.getDescricao_atividade(),
                atividade.getTipo_atividade(),
                atividade.getFrequencia_recomendada(),
                atividade.getId_relatorio(),
                atividade.getNome_funcionario()
        );
    }
}
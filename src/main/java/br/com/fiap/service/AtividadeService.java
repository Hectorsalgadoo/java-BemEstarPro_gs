package br.com.fiap.service;

import br.com.fiap.dao.AtividadeDao;
import br.com.fiap.dto.AtividadeRequestDto;
import br.com.fiap.dto.AtividadeResponseDto;
import br.com.fiap.models.Atividade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AtividadeService {

    @Inject
    AtividadeDao atividadeDao;

    public AtividadeResponseDto criar(AtividadeRequestDto dto) {
        Atividade atividade = new Atividade();
        atividade.setDescricao_atividade(dto.getDescricao_atividade());
        atividade.setTipo_atividade(dto.getTipo_atividade());
        atividade.setFrequencia_recomendada(dto.getFrequencia_recomendada());
        atividade.setId_relatorio(dto.getId_relatorio());

        atividadeDao.inserir(atividade);

        return convertToDto(atividade);
    }

    public List<AtividadeResponseDto> listar() {
        return atividadeDao.listarTodos()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AtividadeResponseDto buscarPorId(Integer id) {
        Atividade atividade = atividadeDao.buscarPorId(id);

        if (atividade == null) {
            throw new RuntimeException("Atividade não encontrada");
        }

        return convertToDto(atividade);
    }

    // MÉTODO REMOVIDO: buscarPorRelatorio

    public AtividadeResponseDto atualizar(Integer id, AtividadeRequestDto dto) {
        Atividade atividade = atividadeDao.buscarPorId(id);

        if (atividade == null) {
            throw new RuntimeException("Atividade não encontrada para atualização");
        }

        atividade.setDescricao_atividade(dto.getDescricao_atividade());
        atividade.setTipo_atividade(dto.getTipo_atividade());
        atividade.setFrequencia_recomendada(dto.getFrequencia_recomendada());

        atividadeDao.atualizar(atividade);

        return convertToDto(atividade);
    }

    public boolean deletar(Integer id) {
        try {
            return atividadeDao.deletar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar atividade: " + e.getMessage(), e);
        }
    }

    // Método para converter Atividade para AtividadeResponseDto
    private AtividadeResponseDto convertToDto(Atividade atividade) {
        return new AtividadeResponseDto(
                atividade.getId_atividade(),
                atividade.getDescricao_atividade(),
                atividade.getTipo_atividade(),
                atividade.getFrequencia_recomendada(),
                atividade.getId_relatorio() // Pode ser null
        );
    }
}
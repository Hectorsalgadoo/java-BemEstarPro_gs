
package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dto.FuncionarioRequestDto;
import br.com.fiap.dto.FuncionarioResponseDto;
import br.com.fiap.models.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FuncionarioService {

    @Inject
    private FuncionarioDao funcionarioDao;

    /**
     * Lista todos os funcionarios
     */
    public List<FuncionarioResponseDto> listar() {
        try {
            List<Funcionario> funcionariosList = funcionarioDao.listarFuncionarios();
            List<FuncionarioResponseDto> response = funcionariosList.stream()
                    .map(FuncionarioResponseDto::convertToDto)
                    .collect(Collectors.toList());

            System.out.println("Listados " + response.size() + " funcionarios");
            return response;

        } catch (Exception e) {
            System.err.println("Erro ao listar os Funcionarios: " + e.getMessage());
            throw new RuntimeException("Erro ao listar os Funcionarios", e);
        }
    }

    /**
     * Busca funcionario por ID
     */
    public FuncionarioResponseDto buscarPorIdFuncionario(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do funcionario deve ser positivo");
        }

        try {
            Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(id);
            if (funcionario == null || funcionario.getId() == null) {
                throw new NotFoundException("Funcionario com ID " + id + " não encontrado");
            }

            FuncionarioResponseDto response = FuncionarioResponseDto.convertToDto(funcionario);
            System.out.println("Funcionario encontrado: ID=" + id);
            return response;

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro ao buscar o funcionario ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Erro ao buscar o funcionario", e);
        }
    }

    /**
     * Busca funcionario por CPF
     */
    public FuncionarioResponseDto buscarPorCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter exatamente 11 dígitos");
        }

        try {
            Funcionario funcionario = funcionarioDao.buscarPorCpf(cpf);
            if (funcionario == null || funcionario.getId() == null) {
                return null;
            }

            FuncionarioResponseDto response = FuncionarioResponseDto.convertToDto(funcionario);
            System.out.println("Funcionario encontrado por CPF: " + cpf);
            return response;

        } catch (Exception e) {
            System.err.println("Erro ao buscar o funcionario CPF " + cpf + ": " + e.getMessage());
            throw new RuntimeException("Erro ao buscar o funcionario por CPF", e);
        }
    }

    /**
     * Cadastra novo funcionario - VERSÃO CORRIGIDA
     */
    public FuncionarioResponseDto cadastrar(FuncionarioRequestDto funcionarioDto) {
        if (funcionarioDto == null) {
            throw new IllegalArgumentException("Dados do funcionario não podem ser nulos");
        }

        // Debug: verifique o que está chegando
        System.out.println("=== DTO RECEBIDO NO SERVICE ===");
        System.out.println("Nome: " + funcionarioDto.getNome());
        System.out.println("CPF: " + funcionarioDto.getCpf());
        System.out.println("Cargo: " + funcionarioDto.getCargo());
        System.out.println("===============================");

        funcionarioDto.cleanData();

        if (!funcionarioDto.isValid()) {
            throw new IllegalArgumentException("Dados do Funcionario inválidos ou incompletos");
        }

        try {
            Funcionario funcionarioExistente = funcionarioDao.buscarPorCpf(funcionarioDto.getCpf());
            if (funcionarioExistente != null) {
                throw new IllegalArgumentException("CPF já cadastrado: " + funcionarioDto.getCpf());
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setNome(funcionarioDto.getNome());
            funcionario.setCpf(funcionarioDto.getCpf());
            funcionario.setCargo(funcionarioDto.getCargo()); // LINHA CORRIGIDA - estava faltando

            System.out.println("Tentando cadastrar o funcionario: " +
                    funcionario.getNome() + ", CPF: " + funcionario.getCpf() +
                    ", Cargo: " + funcionario.getCargo());

            funcionarioDao.cadastrarFun(funcionario);

            if (funcionario.getId() == null) {
                throw new RuntimeException("Falha ao obter ID gerado para o funcionario");
            }

            FuncionarioResponseDto response = FuncionarioResponseDto.convertToDto(funcionario);
            System.out.println("Funcionario cadastrado com sucesso: ID=" + response.getId_funcionario());
            return response;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro detalhado ao cadastrar o funcionario: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar o funcionario: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza funcionario existente - VERSÃO CORRIGIDA
     */
    public void atualizar(FuncionarioRequestDto funcionarioDto) {
        if (funcionarioDto == null) {
            throw new IllegalArgumentException("Dados do funcionario não podem ser nulos");
        }
        if (funcionarioDto.getId() == null || funcionarioDto.getId() <= 0) {
            throw new IllegalArgumentException("ID do funcionario deve ser positivo para atualização");
        }

        funcionarioDto.cleanData();
        if (!funcionarioDto.isValid()) {
            throw new IllegalArgumentException("Dados do funcionario inválidos ou incompletos");
        }

        try {
            Funcionario funcionarioExistente = funcionarioDao.buscarPorIdFuncionario(funcionarioDto.getId());
            if (funcionarioExistente == null || funcionarioExistente.getId() == null) {
                throw new NotFoundException("Funcionario com ID " + funcionarioDto.getId() + " não encontrado para atualização");
            }

            if (!funcionarioExistente.getCpf().equals(funcionarioDto.getCpf())) {
                Funcionario funcionarioComCpf = funcionarioDao.buscarPorCpf(funcionarioDto.getCpf());
                if (funcionarioComCpf != null && !funcionarioComCpf.getId().equals(funcionarioDto.getId())) {
                    throw new IllegalArgumentException("CPF já cadastrado para outro funcionario: " + funcionarioDto.getCpf());
                }
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setId(funcionarioDto.getId());
            funcionario.setNome(funcionarioDto.getNome());
            funcionario.setCpf(funcionarioDto.getCpf());
            funcionario.setCargo(funcionarioDto.getCargo());
            funcionarioDao.atualizaFuncionario(funcionario);
            System.out.println("Funcionario atualizado com sucesso: ID=" + funcionarioDto.getId());

        } catch (NotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o funcionario ID " + funcionarioDto.getId() + ": " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o funcionario", e);
        }
    }

    /**
     * Exclui funcionario por ID
     */
    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do funcionario deve ser positivo para exclusão");
        }

        try {
            Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(id);
            if (funcionario == null || funcionario.getId() == null) {
                throw new NotFoundException("Funcionario com ID " + id + " não encontrado para exclusão");
            }

            funcionarioDao.excluirFuncionario(id);
            System.out.println("Funcionario excluído com sucesso: ID=" + id);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro ao excluir o Funcionario ID " + id + ": " + e.getMessage());

            if (e.getMessage().contains("constraint") || e.getMessage().contains("foreign key")) {
                throw new RuntimeException("Não é possível excluir o Funcionario: existem registros relacionados", e);
            }

            throw new RuntimeException("Erro ao excluir funcionario", e);
        }
    }
}
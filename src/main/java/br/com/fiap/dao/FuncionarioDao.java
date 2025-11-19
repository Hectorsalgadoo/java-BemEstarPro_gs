package br.com.fiap.dao;

import br.com.fiap.models.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FuncionarioDao {

    @Inject
    DataSource dataSource;

    /**
     *
     * Cadastra um cuncionario novo
     * @param funcionario
     */
    public void cadastrarFun(Funcionario funcionario){
        // Validações iniciais
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do funcionário é obrigatório");
        }
        if (funcionario.getCpf() == null || !funcionario.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido: deve ter exatamente 11 dígitos numéricos");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo do funcionário é obrigatório");
        }

        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet generatedKeys = null;

        try {
            conexao = dataSource.getConnection();

            String[] generatedColumns = {"id_funcionario"};
            comandoSQL = conexao.prepareStatement(
                    "INSERT INTO FUNCIONARIO(nome_funcionario, cpf_funcionario, cargo_funcionario) " +
                            "VALUES (?, ?, ?)",
                    generatedColumns);

            System.out.println("Cadastrando Funcionario: Nome=" + funcionario.getNome() +
                    ", CPF=" + funcionario.getCpf() + ", Cargo=" + funcionario.getCargo());

            comandoSQL.setString(1, funcionario.getNome());
            comandoSQL.setString(2, funcionario.getCpf());
            comandoSQL.setString(3, funcionario.getCargo());

            int affectedRows = comandoSQL.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao cadastrar o funcionario, nenhuma linha afetada.");
            }

            generatedKeys = comandoSQL.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                try {
                    int generatedId = generatedKeys.getInt(1);
                    funcionario.setId(generatedId);
                    System.out.println("Funcionario cadastrado com sucesso. ID gerado: " + generatedId);
                } catch (SQLException e) {
                    try {
                        String generatedIdStr = generatedKeys.getString(1);
                        if (generatedIdStr != null) {
                            int generatedId = Integer.parseInt(generatedIdStr);
                            funcionario.setId(generatedId);
                            System.out.println("Funcionario cadastrado com sucesso. ID gerado (string): " + generatedId);
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("Falha ao converter ID gerado: " + generatedKeys.getString(1));
                        throw new SQLException("Falha ao obter ID gerado para o funcionario");
                    }
                }
            } else {
                throw new SQLException("Falha ao obter ID gerado para o funcionario - nenhuma chave retornada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro SQL ao cadastrar funcionario: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar o funcionario: " + funcionario.getNome(), e);
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (comandoSQL != null) comandoSQL.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    /**
     * Lista todos os Funcionario
     */
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM FUNCIONARIO ORDER BY nome_funcionario");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id_funcionario"));
                funcionario.setNome(rs.getString("nome_funcionario"));
                funcionario.setCpf(rs.getString("cpf_funcionario"));
                funcionario.setCargo(rs.getString("cargo_funcionario"));

                funcionarios.add(funcionario);
            }

            System.out.println("Listados " + funcionarios.size() + " funcionario");

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionarios: " + e.getMessage());
            throw new RuntimeException("Erro ao listar funcionarios", e);
        }
        return funcionarios;
    }

    /**
     * Busca um funcionario por um id
     * @param id
     * @return
     */
    public Funcionario buscarPorIdFuncionario(int id) {
        Funcionario funcionario = null;

        try (Connection conexao =dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM FUNCIONARIO WHERE id_funcionario = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("id_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setCpf(rs.getString("cpf_funcionario"));
                    funcionario.setCargo(rs.getString("cargo_funcionario"));

                    System.out.println("Funcionario encontrado: ID=" + id + ", Nome=" + funcionario.getNome());
                } else {
                    System.out.println("Funcionario não encontrado com ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Funcionario por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar Funcionario por ID: " + id, e);
        }
        return funcionario;
    }

    public Funcionario buscarFuncionarioPorPesquisa(int id_pesquisa) {
        String sql = """
                 SELECT f.id_funcionario, f.nome_funcionario, f.cpf_funcionario, f.cargo_funcionario
                 FROM FUNCIONARIO f
                 JOIN PESQUISA_REGIME_TRABALHO p ON f.id_funcionario = p.id_funcionario
                 WHERE p.id_pesquisa = ?
                 """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id_pesquisa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("id_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setCpf(String.valueOf(rs.getLong("cpf_funcionario")));
                    funcionario.setCargo(rs.getString("cargo_funcionario"));
                    return funcionario;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por pesquisa: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Atualiza um funcionario existente
     */
    public void atualizaFuncionario(Funcionario funcionario) {
        if (funcionario.getId() <= 0) {
            throw new IllegalArgumentException("ID do funcionario deve ser positivo");
        }
        if (!funcionario.isCpfValido()) {
            throw new IllegalArgumentException("CPF inválido: deve ter exatamente 11 dígitos");
        }

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(
                     "UPDATE FUNCIONARIO SET nome_funcionario = ?, cpf_funcionario = ?, cargo_funcionario = ?" +
                             " WHERE id_funcionario = ?")) {


            String cpf = funcionario.getCpf();
            if (cpf == null || !cpf.matches("\\d{11}")) {
                throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos: " + cpf);
            }

            ps.setString(1, funcionario.getNome());
            ps.setString(2, cpf);
            ps.setString(3, funcionario.getCargo());
            ps.setInt(4, funcionario.getId());


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Funcionario atualizado com sucesso. ID: " + funcionario.getId());
            } else {
                throw new RuntimeException("Nenhum funcionario atualizado (ID não encontrado): " + funcionario.getId());
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o funcionario: " + funcionario.getId(), e);
        }
    }

    /**
     * Exclui um funcionario por ID
     */
    public void excluirFuncionario(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do funcionario deve ser positivo");
        }

        try (Connection conexao =dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM funcionario WHERE id_funcionario = ?")) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("funcionario excluído com sucesso. ID: " + id);
            } else {
                throw new RuntimeException("Nenhum funcionario excluído (ID não encontrado): " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionario: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir funcionario: " + id, e);
        }
    }

    /**
     * Busca funcionario por CPF
     */
    public Funcionario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter apenas números: " + cpf);
        }

        Funcionario funcionario = null;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM FUNCIONARIO WHERE cpf_funcionario = ?")) {


            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("id_funcionario"));
                    funcionario.setNome(rs.getString("nome_funcionario"));
                    funcionario.setCpf(rs.getString("cpf_funcionario"));
                    funcionario.setCargo(rs.getString("cargo_funcionario"));

                    System.out.println("Funcionario encontrado por CPF: " + cpf);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar o funcionario por CPF: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar o funcionario por CPF: " + cpf, e);
        }
        return funcionario;
    }



}


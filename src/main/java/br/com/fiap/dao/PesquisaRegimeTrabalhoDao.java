package br.com.fiap.dao;

import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesquisaRegimeTrabalhoDao {

    @Inject
    private FuncionarioDao funcionarioDao;

    /**
     * Cadastra uma pesquisa de satisfacao
     */
    @Inject
    private DataSource dataSource;
    public void cadastrarPesquisa(PesquisaRegimeTrabalho pesquisa) {
        String sql = "INSERT INTO PESQUISA_REGIME_TRABALHO (satisfacao,regime_trabalho,comentario,id_funcionario) VALUES (?, ?, ?, ?)";


        try (Connection conexao = dataSource.getConnection();
             PreparedStatement comandoSQL = conexao.prepareStatement(sql, new String[]{"ID_pesquisa"})) {


            comandoSQL.setInt(1,pesquisa.getSatisfacao());
            comandoSQL.setString(2,pesquisa.getRegime_trabalho());
            comandoSQL.setString(3,pesquisa.getComentario());


            if (pesquisa.getIdfuncionario() > 0) {
                comandoSQL.setInt(4, pesquisa.getIdfuncionario());
            } else {
                throw new IllegalArgumentException("ID do funcionario é obrigatório");
            }

            int rowsAffected = comandoSQL.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = comandoSQL.getGeneratedKeys()) {
                    if (generatedKeys != null && generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        pesquisa.setId_pesquisa(generatedId);
                        System.out.println("Pesquisa inserida com sucesso. ID: " + generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar consulta online: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar consulta online", e);
        }
    }

    /**
     * Lista todas as pesquisas de satisfacao
     */
    public List<PesquisaRegimeTrabalho> listarPesquisa() {
        List<PesquisaRegimeTrabalho> pesquisas = new ArrayList<>();
        String sql = "SELECT " +
                "co.ID_pesquisa, co.satisfacao, co.regime_trabalho, co.comentario, " +
                "p.id_funcionario, p.nome_funcionario, p.cpf_funcionario, p.cargo_funcionario " +
                "FROM PESQUISA_REGIME_TRABALHO co " +
                "LEFT JOIN FUNCIONARIO p ON co.id_funcionario = p.id_funcionario " +
                "ORDER BY co.DATA_CONSULTA DESC";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PesquisaRegimeTrabalho pesquisa = criarPesquisaFromResultSet(rs);
                pesquisas.add(pesquisa);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar as pesquisas: " + e.getMessage());
            throw new RuntimeException("Erro ao listar as pesquisas", e);
        }
        return pesquisas;
    }

    /**
     * Cria objeto Pesquisa a partir do ResultSet
     */
    private PesquisaRegimeTrabalho criarPesquisaFromResultSet(ResultSet rs) throws SQLException {
        PesquisaRegimeTrabalho pesquisa = new PesquisaRegimeTrabalho();


        pesquisa.setId_pesquisa(rs.getInt("ID_PESUISA   "));

        pesquisa.setSatisfacao(rs.getInt("SATISFACAO"));
        pesquisa.setComentario(rs.getString("COMENTARIO"));


        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getInt("ID_Funcionario"));
        funcionario.setNome(rs.getString("NOME_funcionario"));
        funcionario.setCpf(rs.getString("CPF_funcionario"));
        pesquisa.setFuncionario(funcionario);

        return pesquisa;
    }

    /**
     * Busca consulta online por ID
     */
    public PesquisaRegimeTrabalho buscarPorIdPesquisa(int id) {
        String sql = "SELECT " +
                "co.ID_pesquisa, co.satisfacao, co.regime_trabalho, co.comentario, " +
                "p.id_funcionario, p.nome_funcionario, p.cpf_funcionario, p.cargo_funcionario " +
                "FROM PESQUISA_REGIME_TRABALHO co " +
                "LEFT JOIN FUNCIONARIO p ON co.id_funcionario = p.id_funcionario " +
                "ORDER BY co.DATA_CONSULTA DESC";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return criarPesquisaFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar a pesquisa por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a pesquisa", e);
        }
        return null;
    }

    /**
     * Atualiza uma pesquisa de satisfacao
     */
    public void atualizar(PesquisaRegimeTrabalho pesquisaRegimeTrabalho) {
        if (pesquisaRegimeTrabalho.getId_pesquisa() <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo");
        }

        String sql = "UPDATE PESQUISA_REGIME_TRABALHO SET satisfacao = ?, regime_trabalho = ?, comentario = ?, id_funcionario = ? WHERE id_pesquisa = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, pesquisaRegimeTrabalho.getSatisfacao());
            ps.setString(2, pesquisaRegimeTrabalho.getRegime_trabalho());
            ps.setString(3,pesquisaRegimeTrabalho.getComentario());


            if (pesquisaRegimeTrabalho.getIdfuncionario() > 0) {
                ps.setInt(4, pesquisaRegimeTrabalho.getIdfuncionario());
            } else {
                throw new IllegalArgumentException("ID do funcionario é obrigatório");
            }

            //ps.setInt(7, consultaOnline.getIdConsulta());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pesquisa não encontrada para atualização");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a pesquisa: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a pesquisa", e);
        }
    }

    /**
     * Exclui uma pesquisa  por ID
     */
    public void excluirPesquisa(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da pesquisa deve ser positivo");
        }

        String sql = "DELETE FROM PESQUISA_REGIME_TRABALHO WHERE id_pesquisa = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Pesquisa não encontrada para exclusão");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir a pesquisa: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a pesquisa", e);
        }
    }



}

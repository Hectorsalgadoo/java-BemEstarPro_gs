package br.com.fiap.dao;

import br.com.fiap.models.Relatorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RelatorioDao {

    @Inject
    DataSource dataSource;

    public Relatorio cadastrarRelatorio(Relatorio relatorio) {
        String sql = """
                INSERT INTO RELATORIO 
                (id_funcionario, resumo_feedback, nivel_bem_estar, tendencias_humor)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id_relatorio"})) {

            stmt.setInt(1, relatorio.getId_funcionario());
            stmt.setString(2, relatorio.getResumo_feedback());
            stmt.setString(3, relatorio.getNivel_bem_estar());
            stmt.setString(4, relatorio.getTendencias_humor());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    relatorio.setId_relatorio(rs.getInt(1));
                }
            }

            System.out.println("Relatório inserido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar relatório: " + e.getMessage(), e);
        }

        return relatorio;
    }

    public List<Relatorio> listRelatorio() {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = """
                SELECT id_relatorio, id_funcionario, resumo_feedback, 
                       nivel_bem_estar, tendencias_humor
                FROM RELATORIO
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                relatorios.add(relatorioFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relatórios: " + e.getMessage(), e);
        }

        return relatorios;
    }

    public Relatorio buscarPorIdRelatorio(int id) {
        String sql = """
                SELECT id_relatorio, id_funcionario, resumo_feedback, 
                       nivel_bem_estar, tendencias_humor
                FROM RELATORIO 
                WHERE id_relatorio = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return relatorioFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relatório: " + e.getMessage(), e);
        }

        return null;
    }

    public void atualizar(Relatorio relatorio) {
        if (relatorio.getId_relatorio() <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }

        String sql = """
                UPDATE RELATORIO
                SET id_funcionario = ?, 
                    resumo_feedback = ?, 
                    nivel_bem_estar = ?, 
                    tendencias_humor = ?
                WHERE id_relatorio = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, relatorio.getId_funcionario());
            ps.setString(2, relatorio.getResumo_feedback());
            ps.setString(3, relatorio.getNivel_bem_estar());
            ps.setString(4, relatorio.getTendencias_humor());
            ps.setInt(5, relatorio.getId_relatorio());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Relatório não encontrado para atualização");
            }

            System.out.println("Relatório atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar relatório: " + e.getMessage(), e);
        }
    }

    public void excluirRelatorio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        String sql = "DELETE FROM RELATORIO WHERE id_relatorio = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Relatório não encontrado para exclusão");
            }

            System.out.println("Relatório excluído com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir relatório: " + e.getMessage(), e);
        }
    }

    private Relatorio relatorioFromResultSet(ResultSet rs) throws SQLException {
        Relatorio r = new Relatorio();

        r.setId_relatorio(rs.getInt("id_relatorio"));
        r.setId_funcionario(rs.getInt("id_funcionario"));
        r.setResumo_feedback(rs.getString("resumo_feedback"));
        r.setNivel_bem_estar(rs.getString("nivel_bem_estar"));
        r.setTendencias_humor(rs.getString("tendencias_humor"));

        return r;
    }
}

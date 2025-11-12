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
        String sql = "INSERT INTO RELATORIO (id_funcionario, resumo_feedback) VALUES (?, ?)";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id_relatorio"})) {

            stmt.setInt(1, relatorio.getId_funcionario());
            stmt.setString(2, relatorio.getResumo_feedback());
            stmt.executeUpdate();

            // Recuperar o ID gerado automaticamente
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
        String sql = "SELECT id_relatorio, id_funcionario, resumo_feedback FROM RELATORIO";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Relatorio relatorio = relatorioFromResultSet(rs);
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar os relatórios: " + e.getMessage());
            throw new RuntimeException("Erro ao listar os relatórios", e);
        }

        return relatorios;
    }

    public Relatorio buscarPorIdRelatorio(int id) {
        String sql = "SELECT id_relatorio, id_funcionario, resumo_feedback FROM RELATORIO WHERE id_relatorio = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return relatorioFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar o relatório: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar o relatório", e);
        }

        return null;
    }

    public void atualizar(Relatorio relatorio) {
        if (relatorio.getId_relatorio() <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }

        String sql = "UPDATE RELATORIO SET id_funcionario = ?, resumo_feedback = ? WHERE id_relatorio = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, relatorio.getId_funcionario());
            ps.setString(2, relatorio.getResumo_feedback());
            ps.setInt(3, relatorio.getId_relatorio());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Relatório não encontrado para atualização");
            }

            System.out.println("Relatório atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o relatório: " + e.getMessage(), e);
        }
    }

    public void excluirRelatorio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do relatório deve ser positivo");
        }

        String sql = "DELETE FROM RELATORIO WHERE id_relatorio = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Relatório não encontrado para exclusão");
            }

            System.out.println("Relatório excluído com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o relatório: " + e.getMessage(), e);
        }
    }

    private Relatorio relatorioFromResultSet(ResultSet rs) throws SQLException {
        Relatorio relatorio = new Relatorio();
        relatorio.setId_relatorio(rs.getInt("id_relatorio"));
        relatorio.setId_funcionario(rs.getInt("id_funcionario"));
        relatorio.setResumo_feedback(rs.getString("resumo_feedback"));
        return relatorio;
    }
}

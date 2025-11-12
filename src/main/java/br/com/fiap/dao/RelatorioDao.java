package br.com.fiap.dao;

import br.com.fiap.models.Relatorio;
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
public class RelatorioDao {

    @Inject
    DataSource dataSource;

    public Relatorio cadastrarRelatorio(Relatorio relatorio) {
        String sql = "INSERT INTO RELATORIO (id_funcionario, id_pesquisa, resumo_feedback) VALUES (?, ?, ?)";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, relatorio.getId_funcionario());
            stmt.setInt(2, relatorio.getId_pesquisa());
            stmt.setString(3, relatorio.getResumo_feedback());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Relatório inserido com sucesso!");
            } else {
                System.out.println("Nenhuma linha foi inserida.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar relatório: " + e.getMessage(), e);
        }
        return relatorio;
    }

    public List<Relatorio> listRelatorio() {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = "SELECT * FROM RELATORIO";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Relatorio relatorio1 = relatorioFromResultSet(rs);
                relatorios.add(relatorio1);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar os relatórios: " + e.getMessage());
            throw new RuntimeException("Erro ao listar os relatórios", e);
        }

        return relatorios;
    }

    private Relatorio relatorioFromResultSet(ResultSet rs) throws SQLException {
        Relatorio relatorio = new Relatorio();
        relatorio.setId_relatorio(rs.getInt("id_relatorio"));
        relatorio.setId_funcionario(rs.getInt("id_funcionario"));
        relatorio.setId_pesquisa(rs.getInt("id_pesquisa"));
        relatorio.setResumo_feedback(rs.getString("resumo_feedback"));
        return relatorio;
    }

    public Relatorio buscarPorIdRelatorio(int id) {
        String sql = "SELECT * FROM RELATORIO WHERE id_relatorio = ?";

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

        String sql = "UPDATE RELATORIO SET id_funcionario = ?, id_pesquisa = ?, resumo_feedback = ? WHERE id_relatorio = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            if (relatorio.getId_funcionario() <= 0) {
                throw new IllegalArgumentException("ID do funcionário é obrigatório");
            }

            ps.setInt(1, relatorio.getId_funcionario());
            ps.setInt(2, relatorio.getId_pesquisa());
            ps.setString(3, relatorio.getResumo_feedback());
            ps.setInt(4, relatorio.getId_relatorio());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Relatório não encontrado para atualização");
            } else {
                System.out.println("Relatório atualizado com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o relatório: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o relatório", e);
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
            } else {
                System.out.println("Relatório excluído com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir o relatório: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o relatório", e);
        }
    }


}

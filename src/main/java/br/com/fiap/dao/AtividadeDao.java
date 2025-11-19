package br.com.fiap.dao;

import br.com.fiap.models.Atividade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AtividadeDao {

    @Inject
    DataSource dataSource;

    public Atividade inserir(Atividade atividade) {
        String sql = """
            INSERT INTO ATIVIDADE (descricao_atividade, tipo_atividade, frequencia_recomendada, id_relatorio)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id_atividade"})) {

            stmt.setString(1, atividade.getDescricao_atividade());
            stmt.setString(2, atividade.getTipo_atividade());
            stmt.setString(3, atividade.getFrequencia_recomendada());
            if (atividade.getId_relatorio() != null) {
                stmt.setInt(4, atividade.getId_relatorio());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    atividade.setId_atividade(rs.getInt(1));
                }
            }

            System.out.println("Atividade cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir atividade: " + e.getMessage(), e);
        }

        return atividade;
    }

    public List<Atividade> listarTodos() {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM ATIVIDADE ORDER BY id_atividade";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                atividades.add(atividadeFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar atividades: " + e.getMessage(), e);
        }

        return atividades;
    }

    public Atividade buscarPorId(Integer id) {
        String sql = "SELECT * FROM ATIVIDADE WHERE id_atividade = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return atividadeFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar atividade: " + e.getMessage(), e);
        }

        return null;
    }

    private Atividade atividadeFromResultSet(ResultSet rs) throws SQLException {
        Atividade atividade = new Atividade();

        atividade.setId_atividade(rs.getInt("id_atividade"));
        atividade.setDescricao_atividade(rs.getString("descricao_atividade"));
        atividade.setTipo_atividade(rs.getString("tipo_atividade"));
        atividade.setFrequencia_recomendada(rs.getString("frequencia_recomendada"));

        if (rs.getObject("id_relatorio") != null) {
            atividade.setId_relatorio(rs.getInt("id_relatorio"));
        }

        return atividade;
    }

    public void atualizar(Atividade atividade) {
        String sql = """
            UPDATE ATIVIDADE
            SET descricao_atividade = ?, tipo_atividade = ?, frequencia_recomendada = ?, id_relatorio = ?
            WHERE id_atividade = ?
            """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, atividade.getDescricao_atividade());
            stmt.setString(2, atividade.getTipo_atividade());
            stmt.setString(3, atividade.getFrequencia_recomendada());

            // id_relatorio pode ser null
            if (atividade.getId_relatorio() != null) {
                stmt.setInt(4, atividade.getId_relatorio());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, atividade.getId_atividade());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Atividade não encontrada para atualização");
            }

            System.out.println("Atividade atualizada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar atividade: " + e.getMessage(), e);
        }
    }

    public boolean deletar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da atividade deve ser positivo");
        }

        String sql = "DELETE FROM ATIVIDADE WHERE id_atividade = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Atividade não encontrada para exclusão");
            }

            System.out.println("Atividade excluída com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir atividade: " + e.getMessage(), e);
        }
        return true;
    }
}
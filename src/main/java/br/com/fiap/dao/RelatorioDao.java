package br.com.fiap.dao;

import br.com.fiap.models.Relatorio;
import br.com.fiap.models.Funcionario;
import br.com.fiap.models.PesquisaRegimeTrabalho;
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
                (id_funcionario, id_pesquisa, resumo_feedback, nivel_bem_estar, tendencias_humor)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id_relatorio"})) {

            stmt.setInt(1, relatorio.getId_funcionario());

            if (relatorio.hasPesquisa()) {
                stmt.setInt(2, relatorio.getId_pesquisa());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setString(3, relatorio.getResumo_feedback());
            stmt.setString(4, relatorio.getNivel_bem_estar());
            stmt.setString(5, relatorio.getTendencias_humor());

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
                 SELECT r.id_relatorio, r.id_funcionario, r.id_pesquisa, 
                        r.resumo_feedback, r.nivel_bem_estar, r.tendencias_humor,
                        f.nome_funcionario,
                        p.regime_trabalho, p.satisfacao, p.comentario
                 FROM RELATORIO r
                 JOIN FUNCIONARIO f ON r.id_funcionario = f.id_funcionario
                 LEFT JOIN PESQUISA_REGIME_TRABALHO p ON r.id_pesquisa = p.id_pesquisa
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
                 SELECT r.id_relatorio, r.id_funcionario, r.id_pesquisa,
                        r.resumo_feedback, r.nivel_bem_estar, r.tendencias_humor,
                        f.nome_funcionario,
                        p.regime_trabalho, p.satisfacao, p.comentario
                 FROM RELATORIO r
                 JOIN FUNCIONARIO f ON r.id_funcionario = f.id_funcionario
                 LEFT JOIN PESQUISA_REGIME_TRABALHO p ON r.id_pesquisa = p.id_pesquisa
                 WHERE r.id_relatorio = ?
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
                    id_pesquisa = ?,
                    resumo_feedback = ?, 
                    nivel_bem_estar = ?, 
                    tendencias_humor = ?
                WHERE id_relatorio = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, relatorio.getId_funcionario());

            if (relatorio.hasPesquisa()) {
                ps.setInt(2, relatorio.getId_pesquisa());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, relatorio.getResumo_feedback());
            ps.setString(4, relatorio.getNivel_bem_estar());
            ps.setString(5, relatorio.getTendencias_humor());
            ps.setInt(6, relatorio.getId_relatorio());

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
        r.setId_pesquisa(rs.getInt("id_pesquisa"));
        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getInt("id_funcionario"));
        funcionario.setNome(rs.getString("nome_funcionario"));
        r.setFuncionario(funcionario);

        if (r.hasPesquisa() && rs.getObject("regime_trabalho") != null) {
            PesquisaRegimeTrabalho pesquisa = new PesquisaRegimeTrabalho();
            pesquisa.setId_pesquisa(r.getId_pesquisa());
            pesquisa.setRegime_trabalho(rs.getString("regime_trabalho"));

            if (rs.getObject("satisfacao") != null) {
                pesquisa.setSatisfacao(rs.getInt("satisfacao"));
            } else {
                pesquisa.setSatisfacao(0);
            }

            if (rs.getObject("comentario") != null) {
                pesquisa.setComentario(rs.getString("comentario"));
            } else {
                pesquisa.setComentario(null);
            }

            r.setPesquisaRegimeTrabalho(pesquisa);
        }

        return r;
    }

    private boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
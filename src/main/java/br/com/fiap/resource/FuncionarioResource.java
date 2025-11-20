package br.com.fiap.resource;

import br.com.fiap.dto.FuncionarioRequestDto;
import br.com.fiap.dto.FuncionarioResponseDto;
import br.com.fiap.service.FuncionarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Recurso REST responsável por gerenciar operações relacionadas a Funcionários.
 *
 * <p>Oferece endpoints para listagem, busca, cadastro, atualização e exclusão de funcionários.</p>
 *
 * <p>Endpoints disponíveis:</p>
 * <ul>
 *     <li>GET /funcionarios — lista todos os funcionários</li>
 *     <li>GET /funcionarios/{id} — busca funcionário pelo ID</li>
 *     <li>GET /funcionarios/cpf/{cpf} — busca funcionário pelo CPF</li>
 *     <li>POST /funcionarios — cadastra novo funcionário</li>
 *     <li>PUT /funcionarios/{id} — atualiza funcionário existente</li>
 *     <li>DELETE /funcionarios/{id} — exclui funcionário pelo ID</li>
 * </ul>
 *
 * <p>Executa validações, tratamento de erros e retorno de respostas HTTP adequadas.</p>
 */
@Path("/funcionarios")
public class FuncionarioResource {

    @Inject
    FuncionarioService funcionarioService;

    /**
     * Lista todos os funcionários cadastrados.
     *
     *  resposta HTTP 200 contendo uma lista de {@link FuncionarioResponseDto},
     * ou erro 500 em caso de falha interna.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<FuncionarioResponseDto> funcionario = funcionarioService.listar();
            return Response.ok(funcionario).build();
        } catch (Exception e) {
            System.err.println("Erro ao listar os funcionarios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao listar os funcionarios")
                    .build();
        }
    }

    /**
     * Busca um funcionário pelo ID.
     *
     *  id identificador do funcionário
     *  resposta HTTP 200 com o funcionário encontrado,
     *         erro 404 se não existir,
     *         erro 400 se o ID for inválido.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID do funcionario deve ser positivo")
                        .build();
            }

            FuncionarioResponseDto funcionario = funcionarioService.buscarPorIdFuncionario(id);
            return Response.ok(funcionario).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionario não encontrado com ID: " + id)
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao buscar o funcionario ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar o funcionario")
                    .build();
        }
    }

    /**
     * Busca um funcionário pelo CPF.
     *
     *  cpf CPF do funcionário (11 dígitos)
     *  resposta HTTP 200 com o funcionário encontrado,
     *         erro 400 se o CPF for inválido,
     *         erro 404 se não existir.
     */
    @GET
    @Path("/cpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            if (cpf == null || cpf.length() != 11) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("CPF deve ter exatamente 11 dígitos")
                        .build();
            }

            FuncionarioResponseDto funcionario = funcionarioService.buscarPorCpf(cpf);

            if (funcionario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Funcionario não encontrado com CPF: " + cpf)
                        .build();
            }

            return Response.ok(funcionario).build();

        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionario CPF " + cpf + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar o funcionario por CPF")
                    .build();
        }
    }

    /**
     * Cadastra um novo funcionário.
     *
     *  funcionarioDto dados enviados pelo cliente
     *  uriInfo contexto da requisição para construção da URL do recurso criado
     *  resposta HTTP 201 com o funcionário criado e Location,
     *         ou erros 400/409/500 conforme validação e regras de negócio.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(FuncionarioRequestDto funcionarioDto, @Context UriInfo uriInfo) {
        try {
            if (funcionarioDto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do funcionario não podem ser nulos")
                        .build();
            }

            funcionarioDto.cleanData();

            if (!funcionarioDto.isValid()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do funcionario inválidos ou incompletos")
                        .build();
            }

            FuncionarioResponseDto funcionarioCadastrado = funcionarioService.cadastrar(funcionarioDto);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(funcionarioCadastrado.getId_funcionario()));
            URI location = builder.build();

            return Response.created(location)
                    .entity(funcionarioCadastrado)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("constraint")
                    || e.getMessage().contains("duplicate")
                    || e.getMessage().contains("unique")
                    || e.getMessage().contains("CPF")) {

                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: CPF já cadastrado")
                        .build();
            }

            System.err.println("Erro ao cadastrar funcionario: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar funcionario")
                    .build();
        }
    }

    /**
     * Atualiza um funcionário existente.
     *
     *  funcionarioDto dados enviados para atualização
     *  id identificador do funcionário
     *  resposta HTTP 200 se atualizado com sucesso,
     *         ou erros 400/404/409/500 conforme validação e regras de negócio.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(FuncionarioRequestDto funcionarioDto, @PathParam("id") int id) {
        try {

            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID do funcionario deve ser positivo")
                        .build();
            }

            if (funcionarioDto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do funcionario não podem ser nulos")
                        .build();
            }

            funcionarioDto.cleanData();

            if (!funcionarioDto.isValid()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do funcionario inválidos ou incompletos")
                        .build();
            }

            funcionarioDto.setId(id);
            funcionarioService.atualizar(funcionarioDto);

            return Response.ok("Funcionario atualizado com sucesso").build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionario não encontrado com ID: " + id)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {

            if (e.getMessage().contains("constraint")
                    || e.getMessage().contains("duplicate")
                    || e.getMessage().contains("unique")
                    || e.getMessage().contains("CPF")) {

                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: CPF já cadastrado")
                        .build();
            }

            System.err.println("Erro ao atualizar funcionario ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar funcionario")
                    .build();
        }
    }

    /**
     * Exclui um funcionário pelo ID.
     *
     *  id identificador do funcionário
     *  resposta HTTP 204 se excluído com sucesso,
     *         erro 404 se o funcionário não existir,
     *         erro 409 se houver vínculos,
     *         erro 400/500 em outros casos.
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID do funcionario deve ser positivo")
                        .build();
            }

            funcionarioService.excluir(id);
            return Response.noContent().build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Funcionario não encontrado com ID: " + id)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {

            if (e.getMessage().contains("constraint")
                    || e.getMessage().contains("foreign key")) {

                return Response.status(Response.Status.CONFLICT)
                        .entity("Não é possível excluir funcionario: existem registros relacionados")
                        .build();
            }

            System.err.println("Erro ao excluir funcionario ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir funcionario")
                    .build();
        }
    }
}

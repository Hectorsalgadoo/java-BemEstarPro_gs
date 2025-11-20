package br.com.fiap.resource;

import br.com.fiap.dto.AtividadeRequestDto;
import br.com.fiap.dto.AtividadeResponseDto;
import br.com.fiap.service.AtividadeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

/**
 * Recurso REST responsável por gerenciar operações relacionadas a Atividades.
 *
 * <p>Fornece endpoints para listar, buscar, cadastrar, atualizar e excluir atividades.
 * Utiliza o padrão DTO para entrada e saída de dados.</p>
 *
 * <p>Endpoints disponíveis:</p>
 * <ul>
 *     <li>GET /atividade — lista todas as atividades</li>
 *     <li>GET /atividade/{id} — busca uma atividade por ID</li>
 *     <li>POST /atividade — cria uma nova atividade</li>
 *     <li>PUT /atividade/{id} — atualiza uma atividade existente</li>
 *     <li>DELETE /atividade/{id} — exclui uma atividade por ID</li>
 * </ul>
 *
 * <p>Gerencia respostas HTTP apropriadas e mensagens de erro com tratamento
 * para exceções comuns, como NotFoundException e IllegalArgumentException.</p>
 */
@Path("/atividade")
public class AtividadeResource {

    @Inject
    AtividadeService atividadeService;

    /**
     * Lista todas as atividades cadastradas.
     *
     *  lista de {@link AtividadeResponseDto} com status HTTP 200,
     * ou status 500 em caso de erro interno.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<AtividadeResponseDto> lista = atividadeService.listar();
            return Response.ok(lista).build();
        } catch (Exception e) {
            System.err.println("Erro ao listar atividades: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao listar atividades")
                    .build();
        }
    }

    /**
     * Busca uma atividade pelo ID.
     *
     *  id identificador da atividade
     *  atividade encontrada (HTTP 200), erro 404 se não existir,
     * ou erro 400 se o ID for inválido.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID da atividade deve ser positivo")
                        .build();
            }

            AtividadeResponseDto atividade = atividadeService.buscarPorId(id);
            return Response.ok(atividade).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atividade não encontrada com ID: " + id)
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao buscar atividade " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar atividade")
                    .build();
        }
    }

    /**
     * Cadastra uma nova atividade.
     *
     *  request dados enviados pelo cliente para criação
     *  uriInfo informações da URI para construção do Location do recurso criado
     *  atividade criada com status 201, ou erros 400/500
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(AtividadeRequestDto request, @Context UriInfo uriInfo) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados da atividade não podem ser nulos")
                        .build();
            }

            AtividadeResponseDto atividadeCriada = atividadeService.criar(request);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(atividadeCriada.getIdAtividade()));
            URI location = builder.build();

            return Response.created(location)
                    .entity(atividadeCriada)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar atividade: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar atividade")
                    .build();
        }
    }

    /**
     * Atualiza uma atividade existente.
     *
     *  id identificador da atividade
     *  request dados atualizados
     *  atividade atualizada com status 200, ou erros 400/404/500
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, AtividadeRequestDto request) {
        try {
            atividadeService.atualizar(id, request);

            AtividadeResponseDto atualizada = atividadeService.buscarPorId(id);
            return Response.ok(atualizada).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atividade não encontrada com ID: " + id + " para atualização")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao atualizar atividade: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar atividade")
                    .build();
        }
    }

    /**
     * Exclui uma atividade pelo ID.
     *
     *  id identificador da atividade
     *  status 204 se excluído, 404 se não existir, ou 400/500 para erros
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            boolean sucesso = atividadeService.deletar(id);

            if (!sucesso) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Atividade com ID " + id + " não encontrada para exclusão")
                        .build();
            }

            return Response.noContent().build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao excluir atividade: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir atividade")
                    .build();
        }
    }
}

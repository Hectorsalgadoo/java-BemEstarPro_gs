package br.com.fiap.resource;


import br.com.fiap.dto.PesquisaRegimeTrabalhoRequestDto;
import br.com.fiap.dto.PesquisaRegimeTrabalhoResponseDto;
import br.com.fiap.models.PesquisaRegimeTrabalho;
import br.com.fiap.service.PesquisaRegimeTrabalhoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

/**
 * Recurso REST para pesquisa
 */
@Path("/pesquisa")
public class PesquisaRegimeTrabalhoResource {

    @Inject
    private PesquisaRegimeTrabalhoService pesquisaRegimeTrabalhoService;

    /**
     * Lista todas as consultas online
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<PesquisaRegimeTrabalhoResponseDto> pesquisa = pesquisaRegimeTrabalhoService.listar();
            return Response.ok(pesquisa).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao listar a pesquisa")
                    .build();
        }
    }

    /**
     * Busca consulta online por ID
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            PesquisaRegimeTrabalhoResponseDto consulta = pesquisaRegimeTrabalhoService.buscarPorId(id);
            return Response.ok(consulta).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pesquisa não encontrada com ID: " + id)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar a pesquisa")
                    .build();
        }
    }

    /**
     * Cadastra nova consulta online
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(PesquisaRegimeTrabalhoRequestDto pesquisadto, @Context UriInfo uriInfo) {
        try {
            //consultaDto.validateForCreate();

            PesquisaRegimeTrabalho pesquisaRegimeTrabalhocadastrar = pesquisaRegimeTrabalhoService.cadastrar(pesquisadto);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(pesquisaRegimeTrabalhocadastrar.getId_pesquisa()));
            URI location = builder.build();

            return Response.created(location).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && (e.getMessage().contains("constraint") || e.getMessage().contains("duplicate"))) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: " + e.getMessage())
                        .build();
            }
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar a pesquisa")
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro de banco de dados ao cadastrar a pesquisa")
                    .build();
        }
    }

    /**
     * Atualiza consulta online existente
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(PesquisaRegimeTrabalhoRequestDto pesquisadto, @PathParam("id") int id) {
        try {
            pesquisaRegimeTrabalhoService.atualizar(pesquisadto, id);

            PesquisaRegimeTrabalhoResponseDto atualizar = pesquisaRegimeTrabalhoService.buscarPorId(id);
            return Response.ok(atualizar).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pesquisa não encontrada com ID: " + id + " para atualização")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar a pesquisa")
                    .build();
        }
    }

    /**
     * Exclui consulta online por ID
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            pesquisaRegimeTrabalhoService.excluir(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pesquisa não encontrada com ID: " + id + " para exclusão")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir a pesquisa")
                    .build();
        }
    }



}

package br.com.fiap.resource;

import br.com.fiap.dto.RelatorioRequestDto;
import br.com.fiap.dto.RelatorioResponseDto;
import br.com.fiap.service.RelatorioService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

/**
 * Recurso REST para operações relacionadas a relatórios.
 * Expõe endpoints para listar, buscar, cadastrar, atualizar e excluir relatórios.
 */
@Path("/relatorio")
public class RelatorioResource {

    @Inject
    RelatorioService relatorioService;

    /**
     * Lista todos os relatórios cadastrados no sistema.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<RelatorioResponseDto> relatorios = relatorioService.listar();
            return Response.ok(relatorios).build();
        } catch (Exception e) {
            System.err.println("Erro ao listar relatórios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao listar relatórios")
                    .build();
        }
    }

    /**
     * Busca um relatório específico pelo seu ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            RelatorioResponseDto relatorio = relatorioService.buscarPorId(id);
            return Response.ok(relatorio).build();
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao buscar relatório ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar relatório")
                    .build();
        }
    }

    /**
     * Cadastra um novo relatório no sistema.
     * Retorna o status 201 (Created) com a localização do novo recurso e os dados cadastrados.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(RelatorioRequestDto relatorioDto, @Context UriInfo uriInfo) {
        try {
            if (relatorioDto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do relatório não podem ser nulos")
                        .build();
            }


            RelatorioResponseDto relatorioCadastrado = relatorioService.cadastrar(relatorioDto);

            URI location = uriInfo.getAbsolutePathBuilder()
                    .path(Integer.toString(relatorioCadastrado.getId_relatorio()))
                    .build();

            return Response.created(location)
                    .entity(relatorioCadastrado)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar relatório: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar relatório: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Atualiza os dados de um relatório existente.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(RelatorioRequestDto relatorioDto, @PathParam("id") int id) {
        try {
            relatorioService.atualizar(relatorioDto, id);
            RelatorioResponseDto atualizado = relatorioService.buscarPorId(id);
            return Response.ok(atualizado).build();

        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar relatório: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Exclui um relatório do sistema pelo seu ID.
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            relatorioService.excluir(id);
            return Response.noContent().build();
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir relatório: " + e.getMessage())
                    .build();
        }
    }
}
package br.com.fiap.resource;

import br.com.fiap.dto.AtividadeRequestDto;
import br.com.fiap.dto.AtividadeResponseDto;
import br.com.fiap.service.AtividadeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/atividade")
public class AtividadeResource {

    @Inject
    AtividadeService atividadeService;

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

package br.com.fiap.resource;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.RelatorioDao;
import br.com.fiap.dto.RelatorioRequestDto;
import br.com.fiap.dto.RelatorioResponseDto;
import br.com.fiap.models.Funcionario;
import br.com.fiap.models.Relatorio;
import br.com.fiap.service.RelatorioService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/relatorio")
public class RelatorioResource {

    @Inject
    FuncionarioDao funcionarioDao;

    @Inject
    RelatorioService relatorioService;

    @Inject
    RelatorioDao relatorioDao;

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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            RelatorioResponseDto relatorio = relatorioService.buscarPorId(id);
            return Response.ok(relatorio).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatório não encontrado com ID: " + id)
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao buscar relatório ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar relatório")
                    .build();
        }
    }

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

            relatorioDto.cleanData();

            Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(relatorioDto.getId_funcionario());
            if (funcionario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Funcionário com ID " + relatorioDto.getId_funcionario() + " não encontrado")
                        .build();
            }

            Relatorio relatorio = new Relatorio();
            relatorio.setId_funcionario(relatorioDto.getId_funcionario());
            relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
            relatorio.setNivel_bem_estar(relatorioDto.getNivel_bem_estar());
            relatorio.setTendencias_humor(relatorioDto.getTendencias_humor());
            relatorio.setFuncionario(funcionario);

            Relatorio relatorioCadastrado = relatorioDao.cadastrarRelatorio(relatorio);

            URI location = uriInfo.getAbsolutePathBuilder()
                    .path(Integer.toString(relatorioCadastrado.getId_relatorio()))
                    .build();

            return Response.created(location)
                    .entity(RelatorioResponseDto.convertToDto(relatorioCadastrado))
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar relatório: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar relatório")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(RelatorioRequestDto relatorioDto, @PathParam("id") int id) {
        try {
            relatorioService.atualizar(relatorioDto, id);
            RelatorioResponseDto atualizado = relatorioService.buscarPorId(id);
            return Response.ok(atualizado).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatório não encontrado com ID: " + id)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar relatório")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            relatorioService.excluir(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatório não encontrado com ID: " + id)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir relatório")
                    .build();
        }
    }
}

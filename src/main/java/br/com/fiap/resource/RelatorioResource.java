package br.com.fiap.resource;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.RelatorioDao;
import br.com.fiap.dto.*;
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
    private FuncionarioDao funcionarioDao;

    @Inject
    private RelatorioService relatorioService;

    @Inject
    private RelatorioDao relatorioDao;

    @GET
    public Response listar() {
        try {
            List<RelatorioResponseDto> relatorio = relatorioService.listar();
            return Response.ok(relatorio).build();
        } catch (Exception e) {
            System.err.println("Erro ao listar os relatorios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao listar os relatorios")
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
                        .entity("ID do relatorio deve ser positivo")
                        .build();
            }

            RelatorioResponseDto relatorio = relatorioService.buscarPorId(id);
            return Response.ok(relatorio).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatorio não encontrado com ID: " + id)
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao buscar o relatorio ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao buscar o relatorio")
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
            if (!relatorioDto.isValid()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados do relatório inválidos ou incompletos")
                        .build();
            }

            Funcionario funcionario = funcionarioDao.buscarPorIdFuncionario(relatorioDto.getId_funcionario());
            if (funcionario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Funcionário com ID " + relatorioDto.getId_funcionario() + " não encontrado")
                        .build();
            }

            Relatorio relatorio = new Relatorio();
            relatorio.setId_funcionario(relatorioDto.getId_funcionario());
            relatorio.setResumo_feedback(relatorioDto.getResumo_feedback());
            relatorio.setId_pesquisa(relatorioDto.getId_pesquisa());
            relatorio.setFuncionario(funcionario);
            relatorio.setPesquisaRegimeTrabalho(relatorioDto.getPesquisaRegimeTrabalho());


            Relatorio relatorioCadastrado = relatorioDao.cadastrarRelatorio(relatorio);


            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(relatorioCadastrado.getId_relatorio()));
            URI location = builder.build();

            return Response.created(location)
                    .entity(relatorioCadastrado)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("constraint") || e.getMessage().contains("duplicate") ||
                    e.getMessage().contains("unique") || e.getMessage().contains("CPF")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: CPF já cadastrado")
                        .build();
            }
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
    public Response atualizar(RelatorioRequestDto relatoriodto, @PathParam("id") int id) {
        try {
            relatorioService.atualizar(relatoriodto, id);

            RelatorioResponseDto atualizar = relatorioService.buscarPorId(id);
            return Response.ok(atualizar).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatorio não encontrada com ID: " + id + " para atualização")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar o relatorio")
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
                    .entity("Relatorio não encontrada com ID: " + id + " para exclusão")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID inválido: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao excluir o relatorio")
                    .build();
        }
    }







}

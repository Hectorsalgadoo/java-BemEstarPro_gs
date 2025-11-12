package br.com.fiap.resource;

import br.com.fiap.dto.FuncionarioRequestDto;
import br.com.fiap.dto.FuncionarioResponseDto;
import br.com.fiap.service.FuncionarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import jakarta.ws.rs.Path;

/**
 * Recurso REST para pacientes (sem relação com ConsultaOnline)
 */
@Path("/funcionarios")
public class FuncionarioResource {

    @Inject
    private FuncionarioService funcionarioService;

    /**
     * Lista todos os pacientes
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
     * Busca paciente por ID
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
     * Busca paciente por CPF
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
     * Cadastra novo paciente
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
            if (e.getMessage().contains("constraint") || e.getMessage().contains("duplicate") ||
                    e.getMessage().contains("unique") || e.getMessage().contains("CPF")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: CPF já cadastrado")
                        .build();
            }
            System.err.println("Erro ao cadastrar paciente: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao cadastrar paciente")
                    .build();
        }
    }

    /**
     * Atualiza paciente existente
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(FuncionarioRequestDto funcionarioDto, @PathParam("id") int id) {
        try {
            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID do paciente deve ser positivo")
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
            if (e.getMessage().contains("constraint") || e.getMessage().contains("duplicate") ||
                    e.getMessage().contains("unique") || e.getMessage().contains("CPF")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Conflito de dados: CPF já cadastrado")
                        .build();
            }
            System.err.println("Erro ao atualizar paciente ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao atualizar paciente")
                    .build();
        }
    }

    /**
     * Exclui paciente por ID
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
            if (e.getMessage().contains("constraint") || e.getMessage().contains("foreign key")) {
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

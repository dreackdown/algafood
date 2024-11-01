package dev.hugofaria.algafood.api.openapi.controller;

import dev.hugofaria.algafood.api.exceptionhandler.Problem;
import dev.hugofaria.algafood.api.model.CidadeModel;
import dev.hugofaria.algafood.api.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId,

            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);
}
package com.api.pagamento.service;

import com.api.pagamento.builder.TransacaoDTOBuilder;
import com.api.pagamento.domain.dto.TransacaoDTO;
import com.api.pagamento.domain.dto.util.Mapper;
import com.api.pagamento.domain.enumeration.StatusEnum;
import com.api.pagamento.domain.exception.InsercaoNaoPermitidaException;
import com.api.pagamento.domain.exception.TransacaoInexistenteException;
import com.api.pagamento.domain.model.Transacao;
import com.api.pagamento.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


/*

    Testes de Unidade ou teste unitário é a fase de testes onde cada unidade do sistema é testada individualmente.
    O objetivo é isolar cada parte do sistema para garantir que elas estejam funcionando conforme especificado.


 */

/*
    Junit

        Esse framework facilita a criação e manutenção do código para a automação de testes com apresentação dos
        resultados.
        Com ele, pode ser verificado se cada método de uma classe funciona da forma esperada, exibindo possíveis
        erros ou falhas podendo ser utilizado tanto para a execução de baterias de testes como para extensão.

        Com JUnit, o programador tem a possibilidade de usar esta ferramenta para criar um modelo padrão de testes,
        muitas vezes de forma automatizada.

        O teste de unidade testa o menor dos componentes de um sistema de maneira isolada. Cada uma dessas unidades
        define um conjunto de estímulos (chamada de métodos), e de dados de entrada e saída associados a cada estímulo.
        As entradas são parâmetros e as saídas são o valor de retorno, exceções ou o estado do objeto. Tipicamente
        um teste unitário executa um método individualmente e compara uma saída conhecida após o processamento da mesma.

*/

/* Mockito

    O Mockito é um framework de testes unitários e o seu principal objetivo é instanciar classes e controlar o
    comportamento dos métodos. Isso é chamado de mock, na tradução livre quer dizer zombar, e talvez seja mesmo o termo
    que melhor o define.
    Pois ao mockar a dependencia de uma classe, eu faço com que a classe que estou testando pense estar invocando o metodo
    realmente, mas de fato não está.

*/


/* hamcrest

    O Hamcrest é um framework que possibilita a criação de regras de verificação (matchers) de forma declarativa.
    Como dito no próprio site do Hamcrest, Matchers that can be combined to create flexible expressions of intent.

    Portanto a ideia é que com os matchers Hamcrest as asserções utilizadas expressem melhor a sua intenção,
    ficando mais legíveis e mais expressivas.

    Um matcher Hamcrest é um objeto que
        reporta se um dado objeto satisfaz um determinado critério;
        pode descrever este critério; e
        é capaz de descrever porque um objeto não satisfaz um determinado critério.


*/

//MockitoExtension.class = Extensão necessária para as anotações do mockito serem utilizadas
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    // Mock: cria uma instancia de uma classe, porém Mockada (simulada). Se você chamar um metodo ele não irá chamar
    // o metodo real, a não ser que você queira.
    @Mock
    private TransacaoRepository transacaoRepository;

    // InjectMocks: Cria uma intancia e injeta as dependências necessárias que estão anotadas com @Mock.
    @InjectMocks
    private TransacaoServiceImp transacaoService;

    // @Test = A anotação de teste informa ao JUnit que o método void público ao qual está anexado pode ser executado
    // como um caso de teste . Para executar o método, JUnit primeiro constrói uma nova instância da classe e,
    // em seguida,  invoca o método anotado

    //When: Após um mock ser criado, você pode direcionar um retorno para um metodo dado um parametro de entrada.

    // Quando a transação é informada, ela deve ser criada
    @Test
    void whenTransactionInformedThenItShouldBeCreated() throws InsercaoNaoPermitidaException {

        // Dado

            //Gera um TransacaoDTO
            TransacaoDTO transacaoDTO = TransacaoDTOBuilder.builder().build().toTransacaoDTO();

            //Tranforma o TransacaoDTO em um Transacao
            Transacao transacao = (Transacao) Mapper.convert(transacaoDTO, Transacao.class);

        //Quando

            //transacaoService.save( -> transacaoRepository.save(expectedTransacao) -> expectedTransacao
            when(transacaoRepository
                    .save(transacao))
                    .thenReturn(transacao);

        // Então

            //Cria um TransacaoDTO
            TransacaoDTO createdTransacaoDTO  = transacaoService.pagar(transacao);

            //Verifica se o atributo id do createdBeerDTO é igual ao atributo id do expectedDTO
            assertThat(createdTransacaoDTO.getId(), is(equalTo(transacaoDTO.getId())));
            assertThat(createdTransacaoDTO.getCartao(), is(equalTo(transacaoDTO.getCartao())));
            assertThat(createdTransacaoDTO.getDescricao().getId(), is(equalTo(transacaoDTO.getDescricao().getId())));
            assertThat(createdTransacaoDTO.getDescricao().getValor(), is(equalTo(transacaoDTO.getDescricao().getValor())));
            assertThat(createdTransacaoDTO.getDescricao().getDataHora(), is(equalTo(transacaoDTO.getDescricao().getDataHora())));
            assertThat(createdTransacaoDTO.getDescricao().getEstabelecimento(), is(equalTo(transacaoDTO.getDescricao().getEstabelecimento())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getId(), is(equalTo(transacaoDTO.getFormaPagamento().getId())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getTipo(), is(equalTo(transacaoDTO.getFormaPagamento().getTipo())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getParcelas(), is(equalTo(transacaoDTO.getFormaPagamento().getParcelas())));

    }

    // Quando o nsu, codigo_pagamento ou o status é informado, uma exceção deve ser lançada
    @Test
    void whenNsuCodPagStatusInformedThenAnExceptionShouldBeThrown() {

        // Dado

            //Gera um TransacaoDTO
            TransacaoDTO transacaoDTO = TransacaoDTOBuilder.builder().build().toTransacaoDTO();

            //Tranforma o TransacaoDTO em um Transacao
            Transacao transacao = (Transacao) Mapper.convert(transacaoDTO, Transacao.class);

            transacao.setId(null);
            transacao.getDescricao().setId(null);
            transacao.getFormaPagamento().setId(null);

        //Quando

            transacao.getDescricao().setNsu("1234567890");
            transacao.getDescricao().setCodigoAutorizacao("147258369");
            transacao.getDescricao().setStatus(StatusEnum.AUTORIZADO);

        // Então

            //Verifica se transacaoService.pagar(transacao) lançou a exceção InsercaoNaoPermitidaException.class
            assertThrows(InsercaoNaoPermitidaException.class, () -> transacaoService.pagar(transacao));

    }

    // Quando uma transacao é informada pelo id e não é encontrada, uma exceção deve ser retornada
    @Test
    void whenTransactionIsInformedByIdAndNotFoundThenAnExceptionIsReturned()  {
        // Dado

        Long id = 1L;

        //Quando

        //transacaoRepository.procurarPeloId(expectedTransacao) -> ConstraintViolationException

        // Então

        //Verifica se transacaoService.procurarPeloId(id) lançou a exceção TransacaoInexistenteException.class
        assertThrows(TransacaoInexistenteException.class, () -> transacaoService.procurarPeloId(id));

    }

    // Quando um pagamento não é informado com todos os campos obrigatórios, uma exceção deve ser retornada
    @Test
    void whenPaymentWithoutAllFieldsIsInformedThenAnExceptionIsReturned()  {
        // Dado

            //Gera um TransacaoDTO
            TransacaoDTO transacaoDTO = TransacaoDTOBuilder.builder().build().toTransacaoDTO();

            //Tranforma o TransacaoDTO em um Transacao
            Transacao transacao = (Transacao) Mapper.convert(transacaoDTO, Transacao.class);

            transacao.setId(null);
            transacao.getDescricao().setId(null);
            transacao.getFormaPagamento().setId(null);


        //Quando

            //transacaoRepository.save(transacao) -> ConstraintViolationException
            when(transacaoRepository.save(transacao))
                   .thenThrow(ConstraintViolationException.class);

        // Então

             //Verifica se transacaoService.pagar(transacao) lançou a exceção ConstraintViolationException.class
            assertThrows(ConstraintViolationException.class, () -> transacaoService.pagar(transacao));

    }

}

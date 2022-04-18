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
            TransacaoDTO expectedTransacaoDTO = TransacaoDTOBuilder.builder().build().toTransacaoDTO();

            //Tranforma o TransacaoDTO em um Transacao
            Transacao expectedTransacao = (Transacao) Mapper.convert(expectedTransacaoDTO, Transacao.class);

        //Quando

            //transacaoService.pagar(expectedTransacao) -> transacaoRepository.save(expectedTransacao) -> expectedTransacao
            //when(transacaoRepository
            //        .save(expectedTransacao))
            //        .thenReturn(expectedTransacao);

        // Então

            //Cria um TransacaoDTO
            TransacaoDTO createdTransacaoDTO  = transacaoService.pagar(expectedTransacao);

            //Verifica se o atributo id do createdBeerDTO é igual ao atributo id do expectedDTO
            assertThat(createdTransacaoDTO.getId(), is(equalTo(expectedTransacaoDTO.getId())));
            assertThat(createdTransacaoDTO.getCartao(), is(equalTo(expectedTransacaoDTO.getCartao())));
            assertThat(createdTransacaoDTO.getDescricao().getId(), is(equalTo(expectedTransacaoDTO.getDescricao().getId())));
            assertThat(createdTransacaoDTO.getDescricao().getValor(), is(equalTo(expectedTransacaoDTO.getDescricao().getValor())));
            assertThat(createdTransacaoDTO.getDescricao().getDataHora(), is(equalTo(expectedTransacaoDTO.getDescricao().getDataHora())));
            assertThat(createdTransacaoDTO.getDescricao().getEstabelecimento(), is(equalTo(expectedTransacaoDTO.getDescricao().getEstabelecimento())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getId(), is(equalTo(expectedTransacaoDTO.getFormaPagamento().getId())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getTipo(), is(equalTo(expectedTransacaoDTO.getFormaPagamento().getTipo())));
            assertThat(createdTransacaoDTO.getFormaPagamento().getParcelas(), is(equalTo(expectedTransacaoDTO.getFormaPagamento().getParcelas())));

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


        //transacaoRepository.save(transacao) -> InsercaoNaoPermitidaException
        when(transacaoRepository
                .save(transacao))
                .thenThrow(InsercaoNaoPermitidaException.class);

        // Então

            //Verifica se transacaoService.pagar(expectedTransacao) lançou a exceção InsercaoNaoPermitidaException.class
            assertThrows(InsercaoNaoPermitidaException.class, () -> transacaoService.pagar(transacao));

    }

    // Quando uma transacao é informada pelo id e não é encontrada, uma exceção deve ser retornada
    @Test
    void whenTransactionIsInfomedByIdAndNotFoundThenAnExceptionIsReturned() throws Exception {
        // Dado

        //Gera um TransacaoDTO
        TransacaoDTO transacaoDTO = TransacaoDTOBuilder.builder().build().toTransacaoDTO();

        //Tranforma o TransacaoDTO em um Transacao
        Transacao transacao = (Transacao) Mapper.convert(transacaoDTO, Transacao.class);

        transacao.setId(null);
        transacao.getDescricao().setId(null);
        transacao.getFormaPagamento().setId(null);

        //Quando

        //transacaoRepository.save(expectedTransacao) -> ConstraintViolationException
        when(transacaoRepository
                .save(transacao))
                .thenThrow(ConstraintViolationException.class);

        // Então

        //Verifica se transacaoService.pagar(expectedTransacao) lançou a exceção InsercaoNaoPermitidaException.class
        assertThrows(ConstraintViolationException .class, () -> transacaoService.pagar(transacao));

    }


    /*
    //Quando um nome de cerveja válido é fornecido, então retorna uma cerveja -> Exceção de cerveja não encontrada
    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {

        // Dado

            //Gera um BeerDTO
            BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        // Quando

            //expectedFoundBeer.getName() for encontrado ou não -> Optional.of(expectedFoundBeer)
            when(beerRepository.findByName(expectedFoundBeer.getName()))
                    .thenReturn(Optional.of(expectedFoundBeer));

        // Então

            //foundBeerDTO recebe a cerveja encontrada
            BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

            //Verifica se o objeto foundBeerDTO é igual ao objeto expecterFoundBeerDTO
            assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }

    //Quando um nome de cerveja não registrado é fornecido, então lança uma exceção
    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {

        // Dado

            //Gera um BeerDTO
            BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        // Quando

            //expectedFoundBeerDTO.getName() for encontrado ou não -> Optional.empty()
            when(beerRepository.findByName(expectedFoundBeerDTO.getName()))
                    .thenReturn(Optional.empty());

        // Então

            //Verifica se beerService.findByName(expectedFoundBeerDTO.getName()) lançou a exceção BeerNotFoundException.class
            assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));

    }


    //Quando uma lista de cervejas for chamada, então retorne uma lista de cervejas
    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        // Dado

            //Gera um BeerDTO
            BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        //when

            //beerService.listAll() retornar uma lista ou não -> Collections.singletonList(beerDTO)
            when(beerRepository.findAll())
                    .thenReturn(Collections.singletonList(expectedFoundBeer));

        //then

            //foundBeerDTO recebe a lista de cerveja
            List<BeerDTO> foundListBeersDTO = beerService.listAll();

            //Verifica se foundListBeersDTO não está vazio
            assertThat(foundListBeersDTO, is(not(empty())));

            //Verifica se o primeiro elemento da lista presente no objeto foundListBeersDTO é
            //igual ao elemento presente no objeto expectedFoundBeerDTO
            assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundBeerDTO)));
    }

    // Quando a lista de cerveja é chamada, então retorna uma lista vazia de cervejas
    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {

        //Quando

            //beerService.findAll() retornar uma lista ou não -> Collections.singletonList(beerDTO)
            when(beerRepository.findAll())
                    .thenReturn(Collections.EMPTY_LIST);

        //Então

            //foundBeerDTO recebe a lista de cerveja
            List<BeerDTO> foundListBeersDTO = beerService.listAll();

            //Verifica se foundListBeersDTO está vazio
            assertThat(foundListBeersDTO, is(empty()));
    }

    //Quando a exclusão é chamada com ID válido, então uma cerveja deve ser excluída
    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException{

        // Dado

            //Gera um BeerDTO
            BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedDeletedBeer = beerMapper.toModel(expectedDeletedBeerDTO);

        // Quando

            //beerService.findById() retornar uma lista ou não -> Optional.of(expectedDeletedBeer)
            when(beerRepository.findById(expectedDeletedBeerDTO.getId()))
                    .thenReturn(Optional.of(expectedDeletedBeer));

        //Não faça nada

            //Quando beerRepository.deleteById(expectedDeletedBeerDTO.getId());
            doNothing()
                    .when(beerRepository)
                    .deleteById(expectedDeletedBeerDTO.getId());
            //Obs: doNothing() é utilizado porque deleteByID não retorna nada

        // Então

            //Um beerDTO é excluído pelo seu ID
            beerService.deleteById(expectedDeletedBeerDTO.getId());

            //Verify: Verifica se um metódo de uma classe foi testado (no when) e verifica
            //a quantidade de vezes que ele foi testado (no when).

            //Verifica se o metódo findById da classe beerRepository foi chamado uma vez
            verify(beerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());

            //Verifica se o metódo deleteById da classe beerRepository foi chamado uma vez
            verify(beerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
    }


    //Quando o incremento é chamado, então aumente o BeerStock
    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {

        //Dado

            //Gera um BeerDTO
            BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //Quando

            //expectedBeerDTO.getId() for encontrado ou não -> Optional.of(expectedBeer)
            when(beerRepository.findById(expectedBeerDTO.getId()))
                    .thenReturn(Optional.of(expectedBeer));

            //expectedBeer for salvo ou não -> expectedBeer
            when(beerRepository.save(expectedBeer))
                    .thenReturn(expectedBeer);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

        // Então

            //Gera um BeerDTO
            BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

            //Verifica se expectedQuantityAfterIncrement é igual a não incrementedBeerDTO.getQuantity() io
            assertThat(expectedQuantityAfterIncrement, equalTo(incrementedBeerDTO.getQuantity()));

            //Verifica se expectedQuantityAfterIncrement é menor que expectedBeerDTO.getMax()
            assertThat(expectedQuantityAfterIncrement, lessThan(expectedBeerDTO.getMax()));

    }

    //Quando o incremento for maior que o máximo, então lance uma  exceção
    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {

        //Dado

            //Gera um BeerDTO
            BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //Quando

            //expectedBeerDTO.getId() for encontrado ou não -> Optional.of(expectedBeer
            when(beerRepository.findById(expectedBeerDTO.getId()))
                    .thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 80;
        //Então

            //Verifica se beerService.increment(expectedBeerDTO.getId(), quantityToIncrement) lançou a exceção
            //BeerStockExceededException.class
            assertThrows(BeerStockExceededException.class,
                    () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    }

    //Quando o incremento após a soma for maior do que o máximo, então lance a exceção
    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {

        //Dado

            //Gera um BeerDTO
            BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

            //Tranforma o BeertDTO em um BeerModel
            Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //Quando

            //expectedBeerDTO.getId() for encontrado ou não -> Optional.of(expectedBeer
            when(beerRepository.findById(expectedBeerDTO.getId()))
                    .thenReturn(Optional.of(expectedBeer));

            int quantityToIncrement = 45;

            //Verifica se beerService.increment(expectedBeerDTO.getId(), quantityToIncrement) lançou a exceção
            //BeerStockExceededException.class
            assertThrows(BeerStockExceededException.class,
                    () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));

    }

    //Quando o incremento é chamado com ID inválido, então lança exceção
    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {

        //Dado

            int quantityToIncrement = 10;

        //Quando

            //INVALID_BEER_ID for encontrado ou não -> Optional.of(expectedBeer)
            when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        //Então

            //Verifica se beerService.increment(expectedBeerDTO.getId(), quantityToIncrement) lançou a exceção
            //BeerStockExceededException.class
            assertThrows(BeerNotFoundException.class,
                    () -> beerService.increment(INVALID_BEER_ID, quantityToIncrement));
    }
*/
}

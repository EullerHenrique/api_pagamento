package com.api.pagamento.controller;

import com.api.pagamento.domain.dto.ResponseErrorDTO;
import com.api.pagamento.domain.dto.TransacaoDTO;
import com.api.pagamento.domain.exception.InsercaoNaoPermitidaException;
import com.api.pagamento.domain.exception.TransacaoInexistenteException;
import com.api.pagamento.domain.model.Transacao;
import com.api.pagamento.service.TransacaoService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
//@RestController: @Controller + @ResponseBody

//@Controller

//A anotação @Controller é uma anotação usada no framework Spring MVC (o componente do Spring Framework
//usado para implementar o aplicativo da Web). A anotação @Controller indica que uma classe particular serve como
//controlador. A anotação @Controller atua como um estereótipo para a classe anotada, indicando sua função.
//O despachante verifica essas classes anotadas em busca de métodos mapeados e detecta as anotações @RequestMapping.

//@ResponseBody em cada metódo
//A anotação @ResponseBody informa a um controlador que o objeto retornado é serializado automaticamente
//em JSON e passado de volta para o objeto HttpResponse .

@RestController

//@RequestMapping

//A anotação @RequestMapping mapeia uma classe, ou seja, associa uma URI a uma classe. Ao acessar a URL,
//os metódos mapeados da classe podem ser acessados.

@RequestMapping("/transacao/v1")

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    //ResponseEntity vs ResponseStatus: https://www.youtube.com/watch?v=D1TiEm956WE

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransacaoDTO> procurarPeloId(@PathVariable Long id) throws TransacaoInexistenteException {

       return ResponseEntity.ok().body(transacaoService.procurarPeloId(id));

    }

    @GetMapping()
    public ResponseEntity<List<TransacaoDTO>> procurarTodos() throws TransacaoInexistenteException {

        return ResponseEntity.ok().body(transacaoService.procurarTodos());

    }

    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoDTO> pagar(@RequestBody @Valid Transacao transacao) throws InsercaoNaoPermitidaException {

        return ResponseEntity.ok().body(transacaoService.pagar(transacao));

    }


    @GetMapping("/estorno/{id}")
    public ResponseEntity<TransacaoDTO> estornar(@PathVariable Long id) throws TransacaoInexistenteException {

        return ResponseEntity.ok().body(transacaoService.estornar(id));

    }

    @ExceptionHandler(InsercaoNaoPermitidaException.class)
    public ResponseEntity<ResponseErrorDTO> InsercaoNaoPermitidaException(InsercaoNaoPermitidaException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseError());

    }

    @ExceptionHandler(TransacaoInexistenteException.class)
    public ResponseEntity<ResponseErrorDTO> TranscaoInexistenteException(TransacaoInexistenteException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getResponseError());

    }


}

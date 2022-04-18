package com.api.pagamento.builder;
import com.api.pagamento.domain.dto.TransacaoDTO;
import com.api.pagamento.domain.dto.util.Mapper;
import com.api.pagamento.domain.model.Descricao;
import com.api.pagamento.domain.model.FormaPagamento;
import lombok.Builder;

@Builder
public class TransacaoDTOBuilder {

    @Builder.Default()
    private Long id = 1L;

    @Builder.Default()
    private String cartao = "4444********1234";

    @Builder.Default()
    private Descricao descricao = (Descricao) Mapper.convert(DescricaoDTOBuilder.toDescricaoDTO(), Descricao.class);


    @Builder.Default()
    private FormaPagamento formaPagamento = (FormaPagamento) Mapper.convert(FormaPagamentoDTOBuilder.toFormaPagamento(), FormaPagamento.class);

    public TransacaoDTO toTransacaoDTO() {
        return new TransacaoDTO(id, cartao, descricao, formaPagamento);
    }


}

package dev.hugofaria.algafood.domain.listener;

import dev.hugofaria.algafood.domain.event.PedidoCanceladoEvent;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.service.EnvioEmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
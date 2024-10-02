package dev.hugofaria.algafood.domain.listener;

import dev.hugofaria.algafood.domain.event.PedidoConfirmadoEvent;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.service.EnvioEmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class NotificacaoClientePedidoConfirmadoListener {

    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
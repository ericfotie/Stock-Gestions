package dev.commandeservice.Consummer;

import dev.commandeservice.Service.CommandService;
import dev.commandeservice.dto.CommandSubmittedEvent;
import dev.commandeservice.dto.ComdRequestDto;
import dev.commandeservice.Model.CommandStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmdConsummer {

    private final CommandService commandService;

    public CmdConsummer(CommandService commandService) {
        this.commandService = commandService;
    }

    @KafkaListener(topics = "${topic.command-submitted}", groupId = "command-service-group")
    public void listen(CommandSubmittedEvent event) {
        log.info("Nouvel événement reçu pour userId {} produit {}", event.userId(), event.product());

        ComdRequestDto dto = new ComdRequestDto(
                event.userId(),
                event.product(),
                event.quantity(),
                CommandStatus.PENDING
        );

        commandService.save(dto);

        log.info("Commande enregistrée pour userId {}", event.userId());
    }
}
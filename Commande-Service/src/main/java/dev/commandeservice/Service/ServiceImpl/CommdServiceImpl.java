package dev.commandeservice.Service.ServiceImpl;

import dev.commandeservice.Model.CmdModel;
import dev.commandeservice.Model.CommandStatus;
import dev.commandeservice.Producer.ProductProducer;
import dev.commandeservice.Repository.CmdRepository;
import dev.commandeservice.Service.CommandService;
import dev.commandeservice.dto.ComdRequestDto;
import dev.commandeservice.dto.ComdResponseDto;
import dev.commandeservice.dto.ProductCheckEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CommdServiceImpl implements CommandService {

    private final CmdRepository cmdRepository;
    private final ProductProducer productProducer;

    public CommdServiceImpl(CmdRepository cmdRepository, ProductProducer productProducer) {
        this.cmdRepository = cmdRepository;
        this.productProducer = productProducer;
    }

    @Override
    public ComdResponseDto save(ComdRequestDto dto) {
        CmdModel command = new CmdModel();
        command.setUserId(dto.userId());
        command.setProduct(dto.product());
        command.setQuantity(dto.quantity());
        command.setStatus(CommandStatus.PENDING);


        CmdModel saved = cmdRepository.save(command);
        log.info("Commande sauvegardée avec ID {}", saved.getId());


        ProductCheckEvent event = new ProductCheckEvent(
                saved.getId(),
                saved.getProduct(),
                saved.getQuantity()
        );
        productProducer.sendProductCheck(event);
        log.info("Événement ProductCheck envoyé pour commande ID {}", saved.getId());

        return mapToDto(saved);
    }


    @Override
    public ComdResponseDto update(Long id, ComdRequestDto dto) {
        CmdModel command = cmdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));


        command.setProduct(dto.product());
        command.setQuantity(dto.quantity());


        CmdModel updated = cmdRepository.save(command);
        log.info("Commande mise à jour ID {}", updated.getId());

        return mapToDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!cmdRepository.existsById(id)) {
            throw new RuntimeException("Commande non trouvée pour suppression");
        }
        cmdRepository.deleteById(id);
        log.info("Commande supprimée ID {}", id);
    }


    @Override
    public ComdResponseDto getById(Long id) {
        CmdModel command = cmdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        return mapToDto(command);
    }


    @Override
    public List<ComdResponseDto> getAll() {
        return cmdRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private ComdResponseDto mapToDto(CmdModel command) {
        return new ComdResponseDto(
                command.getId(),
                command.getUserId(),
                command.getProduct(),
                command.getQuantity(),
                command.getStatus()
        );
    }
}
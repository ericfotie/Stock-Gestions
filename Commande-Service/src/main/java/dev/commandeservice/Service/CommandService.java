package dev.commandeservice.Service;

import dev.commandeservice.dto.ComdRequestDto;
import dev.commandeservice.dto.ComdResponseDto;
import dev.commandeservice.dto.CommandSubmittedEvent;

import java.util.List;

public interface CommandService {

    ComdResponseDto save(ComdRequestDto dto);

    ComdResponseDto update(Long id, ComdRequestDto dto);

    void delete(Long id);

    ComdResponseDto getById(Long id);

    List<ComdResponseDto> getAll();
}

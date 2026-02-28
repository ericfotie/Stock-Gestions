package dev.commandeservice.Controller;

import dev.commandeservice.Service.ServiceImpl.CommdServiceImpl;
import dev.commandeservice.dto.ComdRequestDto;
import dev.commandeservice.dto.ComdResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cmd")
public class CmdController {
    private final CommdServiceImpl commdService;

    public CmdController(CommdServiceImpl commdService) {
        this.commdService = commdService;
    }
    @PostMapping
    public ResponseEntity<ComdResponseDto> create(@RequestBody ComdRequestDto dto) {
        ComdResponseDto response = commdService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ComdResponseDto> update(@PathVariable Long id,
                                                  @RequestBody ComdRequestDto dto) {
        ComdResponseDto response = commdService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commdService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComdResponseDto> getById(@PathVariable Long id) {
        ComdResponseDto response = commdService.getById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ComdResponseDto>> getAll() {
        List<ComdResponseDto> commands = commdService.getAll();
        return ResponseEntity.ok(commands);
    }
}

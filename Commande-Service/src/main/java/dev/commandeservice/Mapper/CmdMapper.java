package dev.commandeservice.Mapper;

import dev.commandeservice.Model.CmdModel;
import dev.commandeservice.Model.CommandStatus;
import dev.commandeservice.dto.ComdRequestDto;
import dev.commandeservice.dto.ComdResponseDto;

public class CmdMapper {


    public static CmdModel toEntity(ComdRequestDto dto) {
        CmdModel cmd = new CmdModel();
        cmd.setUserId(dto.userId());
        cmd.setProduct(dto.product());
        cmd.setQuantity(dto.quantity());
        cmd.setStatus(CommandStatus.PENDING);
        return cmd;
    }


    public static ComdResponseDto toDto(CmdModel cmd) {
        return new ComdResponseDto(
                cmd.getId(),
                cmd.getUserId(),
                cmd.getProduct(),
                cmd.getQuantity(),
                cmd.getStatus()
        );
    }


    public static void updateEntity(CmdModel cmd, ComdRequestDto dto) {
        cmd.setProduct(dto.product());
        cmd.setQuantity(dto.quantity());


        if (dto.status() != null) {
            cmd.setStatus(dto.status());
        }

}}
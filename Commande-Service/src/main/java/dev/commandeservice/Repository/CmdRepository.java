package dev.commandeservice.Repository;

import dev.commandeservice.Model.CmdModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmdRepository extends JpaRepository<CmdModel , Long> {
}

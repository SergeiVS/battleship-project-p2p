package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;

import java.util.Optional;

public record AttackResponseDto(
        AttackStatus attackStatus,
         CellValue cellValue
) {
    public Optional<CellValue> getCellValue(){
        return Optional.ofNullable(cellValue);
    }
}

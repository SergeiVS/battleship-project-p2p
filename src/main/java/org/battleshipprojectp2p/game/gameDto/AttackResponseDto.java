package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;

import java.util.Optional;

public record AttackResponseDto(
        AttackStatus attackStatus,
        CellValue cellValue
) {
    public AttackResponseDto {
//        if (attackStatus == AttackStatus.HIT && cellValue != CellValue.X) {
//            throw new IllegalStateException("By Hit CellValue should be X");
//        }
//        if (attackStatus == AttackStatus.MISS && cellValue != CellValue.E) {
//            throw new IllegalStateException("By Miss CellValue should be E");
//        }
//        if (attackStatus == AttackStatus.SINK && (cellValue == CellValue.X || cellValue == CellValue.E)) {
//            throw new IllegalStateException("By Sink CellValue should not be X or E");
//        }
    }
}

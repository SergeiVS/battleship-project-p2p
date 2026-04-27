package org.battleshipprojectp2p.service.dto;

public record HostSetupDto(
        String name,
        int cols,
        int rows,
        boolean isHost
) {
    public HostSetupDto(String name, int cols, int rows) {
        this(name, cols, rows, true);
    }
}

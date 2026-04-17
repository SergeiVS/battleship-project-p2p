package org.battleshipprojectp2p.service.dto;

public record GuestSetupDto(
        String name,
        String ip,
        int port,
        boolean isHost
) {
    public GuestSetupDto(String name, String ip, int port) {
        this(name, ip, port, false);
    }
}

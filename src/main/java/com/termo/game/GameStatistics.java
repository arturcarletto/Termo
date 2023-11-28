package com.termo.game;

import java.time.LocalDateTime;

public record GameStatistics(int size, int tries, boolean hasWon, LocalDateTime dateTime) {

}

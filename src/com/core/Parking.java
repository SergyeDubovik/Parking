package com.parking.src.com.core;

import java.io.IOException;
import java.math.BigDecimal;

public interface Parking {
    boolean enter(String carNumber);
    BigDecimal exit(String carNumber);
    void saveData() throws IOException;
    void loadData() throws IOException;
}

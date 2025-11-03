package com.parking.src.com.core;

import java.io.IOException;

public interface ParkingData {
    void saveData() throws IOException;
    void loadData() throws IOException;
}

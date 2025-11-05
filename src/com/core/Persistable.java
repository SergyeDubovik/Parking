package com.parking.src.com.core;

import java.io.IOException;

public interface Persistable {
    void saveData() throws IOException;
    void loadData() throws IOException;
}

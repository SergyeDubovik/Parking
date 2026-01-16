package com.parking.src.com.core;

import java.io.IOException;
import java.sql.SQLException;

public interface Persistable {
    void saveData() throws IOException, SQLException;
    void loadData() throws IOException;
}

package com.yulintu.thematic.data;

import java.util.UUID;

public interface Provider {

    UUID getId();

    String getType();

    String getConnectionString();
}

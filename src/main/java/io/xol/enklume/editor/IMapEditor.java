package io.xol.enklume.editor;

import java.io.File;
import java.io.IOException;

public interface IMapEditor {
    void load(File folder) throws IOException;

    void save(File folder);

    void set(int x, int y, int block);


}

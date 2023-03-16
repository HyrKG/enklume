package io.xol.enklume.editor;

import java.io.File;
import java.io.IOException;

public interface IMapEditor {
    void load(File folder, int id) throws IOException;

    void save(File folder, int id);

    void set(int x, int y, int block, int meta);
}

package io.xol.enklume.editor;

import com.google.common.collect.HashBasedTable;
import io.xol.enklume.MinecraftChunk;
import io.xol.enklume.MinecraftWorld;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Editor for minecraft world saves
 * <br>
 * This editor will cache all changes and overwrite the original file when saving
 */
public class MinecraftWorldEditor implements IMapEditor {

    protected MinecraftWorld world = null;
    protected int id;
    protected HashBasedTable<Integer, Integer, MinecraftChunk> cacheChangedChunk = HashBasedTable.create();

    public void load(MinecraftWorld world) {
        this.world = world;
    }

    public void load(File folder, int id) throws IOException {
        this.world = new MinecraftWorld(folder);
        this.id = id;
    }

    /**
     * Save your saves with your changes into a new folder
     *
     * @param folder that you want to save
     */
    public void save(File folder, int id) {
        this.id = id;
    }

    public void set(int x, int y, int block, int meta) {

    }
}

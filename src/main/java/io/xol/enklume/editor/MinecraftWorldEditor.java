package io.xol.enklume.editor;

import io.xol.enklume.MinecraftWorld;

import java.io.File;
import java.io.IOException;

/**
 * Editor for minecraft world saves
 */
public class MinecraftWorldEditor implements IMapEditor {

    protected MinecraftWorld world = null;

    public void load(MinecraftWorld world) {
        this.world = world;
    }

    public void load(File folder) throws IOException {
        this.world = new MinecraftWorld(folder);
    }

    /**
     * Save your saves with your changes into a new folder
     *
     * @param folder that you want to save
     */
    public void save(File folder) {

    }

    public void set(int x, int y, int block) {

    }
}

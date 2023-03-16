package io.xol.enklume;

import io.xol.enklume.nbt.NBTCompound;
import io.xol.enklume.nbt.NBTag;

public class MinecraftBlock {
    public final int id;
    public final int meta;

    public MinecraftBlock(int id, int meta) {
        this.id = id;
        this.meta = meta;
    }
}

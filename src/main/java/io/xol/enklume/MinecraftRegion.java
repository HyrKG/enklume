package io.xol.enklume;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MinecraftRegion {

    //chunk offset location in file
    int[] locations = new int[1024];
    //chunk size
    int[] sizes = new int[1024];

    RandomAccessFile file;
    private final MinecraftChunk[][] chunks = new MinecraftChunk[32][32];

    public MinecraftRegion(File regionFile) throws IOException, DataFormatException {
        file = new RandomAccessFile(regionFile, "r");

        //First read the 1024 chunks offsets
        for (int i = 0; i < 1024; i++) {
            locations[i] += file.read() << 16;
            locations[i] += file.read() << 8;
            locations[i] += file.read();

            sizes[i] += file.read();
        }

        //Discard the timestamp bytes, we don't care.
        byte[] osef = new byte[4];
        for (int i = 0; i < 1024; i++) {
            file.read(osef);
        }

        //Load chunks, there are 32^2 chunks in a region.
        for (int x = 0; x < 32; x++) {
            for (int z = 0; z < 32; z++) {
                chunks[x][z] = getChunkInternal(x, z);
            }
        }
    }

    /**
     * Offset chunk xz to offset location
     */
    int offset(int x, int z) {
        return ((x & 31) + (z & 31) * 32);
    }

    public MinecraftChunk getChunk(int x, int z) {
        return chunks[x][z];
    }

    private MinecraftChunk getChunkInternal(int x, int z) throws DataFormatException, IOException {
        int l = offset(x, z);
        if (sizes[l] > 0) {
            //Chunk non-void, load it
            file.seek(locations[l] * 4096L);

            //Read 4-bytes of data length
            int compressedLength = 0;
            compressedLength += file.read() << 24;
            compressedLength += file.read() << 16;
            compressedLength += file.read() << 8;
            compressedLength += file.read();

            //Read compression mode
            int compression = file.read();
            if (compression != 2) {
                throw new DataFormatException("\"Fatal error : compression scheme not Zlib. (\" + compression + \") at \" + is.getFilePointer() + \" l = \" + l + \" s= \" + sizes[l]");
            } else {
                byte[] compressedData = new byte[compressedLength];
                file.read(compressedData);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                //Unzip the ordeal
                Inflater inflater = new Inflater();
                inflater.setInput(compressedData);

                byte[] buffer = new byte[4096];
                while (!inflater.finished()) {
                    int c = inflater.inflate(buffer);
                    baos.write(buffer, 0, c);
                }
                baos.close();

                return new MinecraftChunk(x, z, baos.toByteArray());
            }
        }
        return new MinecraftChunk(x, z);
    }

    public void save(File file) throws FileNotFoundException {
        RandomAccessFile outputStream = new RandomAccessFile(file, "w");


    }

    public void close() throws IOException {
        file.close();
    }
}

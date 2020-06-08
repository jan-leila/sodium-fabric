package me.jellysquid.mods.sodium.client.render.chunk.compile;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import me.jellysquid.mods.sodium.client.gl.attribute.GlVertexFormat;
import me.jellysquid.mods.sodium.client.gl.buffer.VertexData;
import me.jellysquid.mods.sodium.client.gl.util.BufferSlice;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkMeshBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkMeshData;
import me.jellysquid.mods.sodium.client.render.layer.BlockRenderPass;
import me.jellysquid.mods.sodium.client.render.layer.BlockRenderPassManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.GlAllocationUtils;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.BlockPos;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * A collection of temporary buffers for each worker thread which will be used to build chunk meshes for given render
 * passes. This makes a best-effort attempt to pick a suitable size for each scratch buffer, but will never try to
 * shrink a buffer.
 */
public class ChunkBuildBuffers {
    private final ChunkMeshBuilder[] builders = new ChunkMeshBuilder[BlockRenderPass.count()];
    private final GlVertexFormat<?> format;

    private final BlockRenderPassManager renderPassManager;

    public ChunkBuildBuffers(GlVertexFormat<?> format, BlockRenderPassManager renderPassManager) {
        this.format = format;
        this.renderPassManager = renderPassManager;

        for (RenderLayer layer : RenderLayer.getBlockLayers()) {
            this.builders[renderPassManager.getRenderPassId(layer)] = new ChunkMeshBuilder(format, layer.getExpectedBufferSize());
        }
    }

    /**
     * Return the {@link ChunkMeshBuilder} for the given {@link RenderLayer} as mapped by the
     * {@link BlockRenderPassManager} for this render context.
     */
    public ChunkMeshBuilder get(RenderLayer layer) {
        return this.builders[this.renderPassManager.getRenderPassId(layer)];
    }

    /**
     * Creates immutable baked chunk meshes from all non-empty scratch buffers and resets the state of all mesh
     * builders. This is used after all blocks have been rendered to pass the finished meshes over to the graphics card.
     */
    public ChunkMeshData createMeshes(Vector3d camera, BlockPos pos) {
        Map<BlockRenderPass, BufferSlice> layers = new Reference2ObjectArrayMap<>();
        int bufferLen = 0;

        for (int i = 0; i < this.builders.length; i++) {
            ChunkMeshBuilder builder = this.builders[i];

            if (builder == null || builder.isEmpty()) {
                continue;
            }

            BlockRenderPass pass = this.renderPassManager.getRenderPass(i);

            if (pass.isTranslucent()) {
                builder.sortQuads((float) camera.x - (float) pos.getX(),
                        (float) camera.y - (float) pos.getY(),
                        (float) camera.z - (float) pos.getZ());
            }

            int start = bufferLen;
            int size = builder.getSize();

            layers.put(pass, new BufferSlice(start, size));
            bufferLen += size;
        }

        if (bufferLen <= 0) {
            return ChunkMeshData.EMPTY;
        }

        ByteBuffer buffer = GlAllocationUtils.allocateByteBuffer(bufferLen);

        for (Map.Entry<BlockRenderPass, BufferSlice> layer : layers.entrySet()) {
            BufferSlice slice = layer.getValue();
            buffer.position(slice.start);

            this.builders[layer.getKey().ordinal()]
                    .copyInto(buffer);
        }

        buffer.flip();

        return new ChunkMeshData(new VertexData(buffer, this.format), layers);
    }
}

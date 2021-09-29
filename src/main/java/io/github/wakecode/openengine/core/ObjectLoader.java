package io.github.wakecode.openengine.core;

import io.github.wakecode.openengine.core.entity.Model;
import io.github.wakecode.openengine.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public Model loadModel(float[] vertices) {
        int id = createVAO();

        storeDataInAttribList(0, 3, vertices);
        unbind();

        return new Model(id, vertices.length / 3);
    }

    private int createVAO() {
        int id = GL30.glGenVertexArrays();

        vaos.add(id);

        GL30.glBindVertexArray(id);

        return id;
    }

    private void storeDataInAttribList(int attribNo, int vertexCount, float[] data) {
        int vbo = GL15.glGenBuffers();

        vbos.add(vbo);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = Utils.storeDataInFloatBuffer(data);

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    private void cleanup() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }

        for (int vbo: vbos) {
            GL30.glDeleteBuffers(vbo);
        }
    }
}

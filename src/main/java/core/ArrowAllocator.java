package core;

import com.google.protobuf.ByteString;
import core.exceptions.UnknownVectorTypeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.Float8Vector;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.ValueVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Field;

/** Every client or server needs a locally baked arrow storage to hold vectors. */
public class ArrowAllocator {
    /** Every client and server has its own {@link RootAllocator} */
    public volatile RootAllocator rootAllocator;

    /** Vectors are placed into this {@link BufferAllocator} */
    volatile BufferAllocator childAllocator;

    /** Internal {@link java.util.HashMap} to access vectors */
    private final ConcurrentHashMap<String, ValueVector> vectors = new ConcurrentHashMap<>();

    public ArrowAllocator() {
        rootAllocator = new RootAllocator(Runtime.getRuntime().maxMemory());
        childAllocator =
                rootAllocator.newChildAllocator("childAlloc", 0, Runtime.getRuntime().maxMemory());
    }

    /**
     * Generates appropriate Key for vector information which displaced into the redis db
     *
     * @param vector Vector for which the key is created
     * @return Matching key for redis store
     */
    private String buildKeyForKV(ValueVector vector) {
        return vector.getName() + vector.getClass().getName();
    }

    /**
     * @param name Vector for which the key is created
     * @param typeOfVector Type of the vector
     * @return Matching key for redis store
     */
    private String buildKeyForKV(String name, String typeOfVector) {
        return name + typeOfVector;
    }

    /** @return Returns the child allocator */
    public BufferAllocator getChildAllocator() {
        return childAllocator;
    }

    /**
     * Creates vector in local arrow storage and places it into the internal hashmap
     *
     * @param vectorName Name of the vector
     * @param type_of_vector Type of the vector
     * @return Newly created vector
     * @throws UnknownVectorTypeException When trying to create a vector whose type is not yet
     *     supported, this exception is thrown
     */
    @SuppressWarnings("unchecked")
    public <T> T createVector(String vectorName, Class<?> type_of_vector) throws Exception {
        if (type_of_vector == IntVector.class) {
            IntVector vector = new IntVector(vectorName, childAllocator);
            vector.allocateNew();
            vectors.put(buildKeyForKV(vector), vector);
            return (T) vector;
        } else if (type_of_vector == Float8Vector.class) {
            Float8Vector vector = new Float8Vector(vectorName, childAllocator);
            vector.allocateNew();
            vectors.put(buildKeyForKV(vector), vector);
            return (T) vector;
        } else if (type_of_vector == VarCharVector.class) {
            VarCharVector vector = new VarCharVector(vectorName, childAllocator);
            vector.allocateNew();
            vectors.put(buildKeyForKV(vector), vector);
            return (T) vector;
        } else {
            throw new UnknownVectorTypeException(
                    "Cannot create vector. Unknown type: " + type_of_vector.getName());
        }
    }

    /**
     * Gets vector from local hashmap
     *
     * @param vectorName Name of the vector
     * @param typeOfVector Type of the vector
     * @return Desired vector
     */
    public ValueVector getVector(String vectorName, String typeOfVector) {
        return vectors.get(buildKeyForKV(vectorName, typeOfVector));
    }

    /**
     * Gets vector from local hashmap
     *
     * @param vectorName Name of the vector
     * @param typeOfVector Type of the vector
     * @return Desired vector
     */
    public ValueVector getVector(String vectorName, Class<?> typeOfVector) {
        return vectors.get(buildKeyForKV(vectorName, typeOfVector.getName()));
    }

    /**
     * Puts vector into local hashmap
     *
     * @param vector Vector to place into hashmap
     */
    public void putVector(ValueVector vector) {
        vectors.put(buildKeyForKV(vector), vector);
    }

    /**
     * Remove vector from local hashmap
     *
     * @param vectorName Name of the vector
     * @param typeOfVector Type of the vector
     * @return True if vector was in local hashmap and was removed. False if vector cannot found in
     *     local hashmap
     */
    public boolean removeVector(String vectorName, String typeOfVector) {
        ValueVector v = getVector(vectorName, typeOfVector);
        if (v == null) {
            return false;
        }
        v.clear();
        vectors.remove(buildKeyForKV(vectorName, typeOfVector));
        return true;
    }

    /** @return Number of vectors in */
    public int numVectors() {
        return vectors.size();
    }

    /**
     * Returns {@link ByteString} representation of a vector
     *
     * @param name Name of the desired vector
     * @param type_of_vector Type of the desired vector
     * @return {@link ByteString} representation
     * @throws Exception If vector cannot be found
     */
    public ByteString getSerializedVector(String name, String type_of_vector) throws Exception {
        FieldVector vector = (FieldVector) getVector(name, type_of_vector);
        if (vector == null) {
            throw new NullPointerException("Vector " + name + " not found!");
        }
        List<Field> fields = Collections.singletonList(vector.getField());
        List<FieldVector> vectors = Collections.singletonList(vector);
        VectorSchemaRoot root = new VectorSchemaRoot(fields, vectors);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out));
        writer.start();
        writer.writeBatch();
        writer.close();
        return ByteString.copyFrom(out.toByteArray());
    }

    /**
     * Returns {@link FieldVector} representation of a supplied serialized vector
     *
     * @param data Data of vector as {@link ByteString}
     * @param name Name of the vector
     * @return {@link FieldVector} representation of a supplied serialized vector
     * @throws Exception If reading of schema fails
     */
    public FieldVector getDeserializedVector(ByteString data, String name) throws Exception {
        ArrowStreamReader reader =
                new ArrowStreamReader(new ByteArrayInputStream(data.toByteArray()), rootAllocator);
        VectorSchemaRoot readBatch = reader.getVectorSchemaRoot();
        reader.loadNextBatch();
        FieldVector vector = readBatch.getVector(name);
        assert vector != null;
        vectors.put(buildKeyForKV(vector), vector);
        return vector;
    }

    /**
     * Get data from vector as {@link ByteArrayInputStream}
     *
     * @param vector Desired vector to get stream from
     * @return Data from vector as {@link ByteArrayInputStream}
     */
    public ByteArrayInputStream getStream(FieldVector vector) {
        List<Field> fields = List.of(vector.getField());
        List<FieldVector> vectors = List.of(vector);
        VectorSchemaRoot root = new VectorSchemaRoot(fields, vectors);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out));
        try {
            writer.start();
            writer.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Get data from vector-stream as {@link ValueVector} (see {@link
     * ArrowAllocator#getStream(FieldVector)} for "other side")
     *
     * @param stream The vector as {@link ByteArrayOutputStream}
     * @param nameOfVector Name of the vector
     * @param typeOfVector Type of the vector
     * @return Deserialized vector as type {@link ValueVector}
     */
    @SuppressWarnings("unchecked")
    public ValueVector readFromStream(
            ByteArrayOutputStream stream, String nameOfVector, String typeOfVector) {
        ArrowStreamReader reader =
                new ArrowStreamReader(new ByteArrayInputStream(stream.toByteArray()), childAllocator);
        try {
            VectorSchemaRoot readBatch = reader.getVectorSchemaRoot();
            reader.loadNextBatch();
            ValueVector vector = readBatch.getVector(nameOfVector);
            try {
                Class<? extends ValueVector> type_of_vector =
                        (Class<? extends ValueVector>) Class.forName(typeOfVector);
                putVector(type_of_vector.cast(vector));
                return vector;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

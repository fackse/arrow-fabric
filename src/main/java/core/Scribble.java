package core;

import java.util.*;
import org.apache.arrow.vector.*;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

public class Scribble {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Scribble.class);

    public static void main(String[] args) throws Exception {
        System.out.println(Util.getConfig());

    /*


    RootAllocator allocator = new RootAllocator(Long.MAX_VALUE);
    VarCharVector varCharVector = new VarCharVector("varcharVar", allocator);
    varCharVector.allocateNew();
    for (int i = 0; i < 535130; i++) {
        varCharVector.setSafe(i, ("test" + i).getBytes(StandardCharsets.UTF_8));
    }
    varCharVector.setValueCount(535130);

    List<Field> fields = List.of(varCharVector.getField());
    List<FieldVector> vectors = List.of(varCharVector);
    VectorSchemaRoot root = new VectorSchemaRoot(fields, vectors);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out));
    writer.start();
    writer.writeBatch();
    System.out.println(Util.getSizeFromBytes(out.size()));

    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

    byte[] content;
    int written = 0;
    List<byte[]> list = new ArrayList<>();
    while ((content = in.readNBytes(4194304)).length != 0){
        System.out.println("Avail "+Util.getSizeFromBytes(in.available()));
        System.out.println("size of iter content "+Util.getSizeFromBytes(content.length));
        written+=content.length;
        list.add(content);
        System.out.println("---");
    }
    assert written == out.size();


    ByteArrayOutputStream incoming = new ByteArrayOutputStream();
    for(byte[] chunk: list){
        incoming.write(chunk);
    }
    assert incoming.size() == out.size();
    System.out.println("Read");
    try (ArrowStreamReader reader = new ArrowStreamReader(new ByteArrayInputStream(incoming.toByteArray()), allocator)) {
        while (reader.loadNextBatch()) {
            VectorSchemaRoot readBatch = reader.getVectorSchemaRoot();
            System.out.println(readBatch.getRowCount());
            for (FieldVector v:readBatch.getFieldVectors()){
                System.out.println(v.getName());
                assert v == varCharVector;
            }
        }

    }*/

    }
}

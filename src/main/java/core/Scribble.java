package core;

import client.FabricClient;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.types.Types;
import org.apache.arrow.vector.types.pojo.Field;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class Scribble {
    private static final Logger logger = Logger.getLogger(FabricClient.class.getName());


    public static void main(String[] args) throws Exception {
        RootAllocator allocator = new RootAllocator(Long.MAX_VALUE);
        BitVector bitVector = new BitVector("boolean", allocator);
        VarCharVector varCharVector = new VarCharVector("varchar", allocator);
        for (int i = 0; i < 10; i++) {
            bitVector.setSafe(i, i % 2 == 0 ? 0 : 1);
            varCharVector.setSafe(i, ("test" + i).getBytes(StandardCharsets.UTF_8));
        }
        bitVector.setValueCount(10);
        varCharVector.setValueCount(10);

        List<Field> fields = Arrays.asList(bitVector.getField(), varCharVector.getField());
        logger.info(String.valueOf(bitVector.getField().getMetadata()));
        List<FieldVector> vectors = Arrays.asList(bitVector, varCharVector);
        VectorSchemaRoot root = new VectorSchemaRoot(fields, vectors);
        String s = varCharVector.getMinorType().name();
        logger.info(String.valueOf(Types.MinorType.valueOf(s)));
    }
}

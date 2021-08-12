package core;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.ValueVector;

import java.util.HashMap;


public class ArrowAllocator {
    RootAllocator allocator;
    BufferAllocator child;
    HashMap<String, IntVector> vectors = new HashMap<>();

    public ArrowAllocator() {
        allocator = new RootAllocator(Long.MAX_VALUE);
        child = allocator.newChildAllocator("childAlloc", 0, Long.MAX_VALUE);
    }

    public IntVector newIntVector(String name) {
        IntVector intVector = new IntVector(name, child);
        intVector.allocateNew();
        vectors.put(name, intVector);
        return intVector;
    }

    public void newVector(String name) {

    }

    public RootAllocator getAllocator(){
        return allocator;
    }
}


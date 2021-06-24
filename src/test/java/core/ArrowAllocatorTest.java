package core;

import org.apache.arrow.vector.IntVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrowAllocatorTest {
    private ArrowAllocator alloc;
    private IntVector v1;

    @BeforeEach
    public void setUp() {
        alloc = new ArrowAllocator();
    }

    @Test
    @DisplayName("Create INT Vector")
    public void testCreateIntVector() {
        v1 = alloc.newIntVector("name");
        assertNotNull(v1);
    }

    @Test
    @DisplayName("Set INT Vector")
    public void testSetIntVector() {
        v1 = alloc.newIntVector("name");
        v1.set(0, 1);
        assertEquals(1, v1.get(0));
    }

}


import org.example.Entry;
import org.example.MyHashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class HashMapTest {

    //Basic Put and Get
    @Test
    public void testPutAndGet() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        assertEquals(Integer.valueOf(1), map.get("apple"));
        assertEquals(Integer.valueOf(2), map.get("banana"));
        assertNull(map.get("grape")); // Test non-existent key
    }

    // Put with Overwriting
    @Test
    public void testPutOverwrite() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        Integer prevValue = map.put("apple", 3); //Overwrite
        assertEquals(Integer.valueOf(1), prevValue); //Check previous value
        assertEquals(Integer.valueOf(3), map.get("apple")); //Check new value

    }


    //PutIfAbsent
    @Test
    public void testPutIfAbsent() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        assertNull(map.putIfAbsent("apple", 1));
        assertEquals(Integer.valueOf(1), map.get("apple"));
        Object prevValue = map.putIfAbsent("apple", 3); //Try to add existing key
        assertEquals(Integer.valueOf(1), prevValue); //check return value
        assertEquals(Integer.valueOf(1), map.get("apple")); //Check that value wasn't overwritten
    }

    //Remove
    @Test
    public void testRemove() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        assertEquals(Integer.valueOf(2), map.remove("banana"));
        assertNull(map.get("banana"));
        assertNull(map.remove("grape")); //Remove non-existent key
    }

    //Remove with Value Check
    @Test
    public void testRemoveWithValueCheck(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        assertNull(map.remove("banana"));
        assertNotNull(map.remove("apple")); //Correct key and value
    }

    //Clear
    @Test
    public void testClear() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.clear();
        assertTrue(map.isEmpty());
        assertNull(map.get("apple"));
    }

    //ContainsKey
    @Test
    public void testContainsKey(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        assertTrue(map.containsKey("apple"));
        assertFalse(map.containsKey("banana"));
    }

    //ContainsValue
    @Test
    public void testContainsValue(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        assertTrue(map.containsValue(1));
        assertTrue(map.containsValue(2));
        assertFalse(map.containsValue(3));
    }

    //Size and IsEmpty
    @Test
    public void testSizeAndIsEmpty(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        map.put("apple", 1);
        assertEquals(1, map.size());
        assertFalse(map.isEmpty());
    }

    //Entry Set
    @Test
    public void testEntrySet(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        assertEquals(2, entrySet.size());
        //Further assertions could check the content of the entrySet (but order is not guaranteed)
    }


    //Key Set
    @Test
    public void testKeySet(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        Set<String> keySet = map.keySet();
        assertEquals(2, keySet.size());
        assertTrue(keySet.contains("apple"));
        assertTrue(keySet.contains("banana"));

    }

    //Values
    @Test
    public void testValues(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        Collection<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));

    }

    //Iterator
    @Test
    public void testIterator(){
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        int count = 0;
        for(Map.Entry<String, Integer> entry : map){
            count++;
        }
        assertEquals(2, count);
    }


    //Collision handling (requires keys that hash to the same index)
    @Test
    public void testCollisions() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        // Choose keys that are likely to collide (e.g., based on hashCode implementation)
        map.put("cat", 1);
        map.put("hat", 2); // Assuming "cat" and "hat" collide

        assertEquals(2, map.size());
        assertEquals(Integer.valueOf(1), map.get("cat"));
        assertEquals(Integer.valueOf(2), map.get("hat"));
    }

    //Resizing test
    @Test
    public void testResizing(){
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        for(int i = 0; i< 100; i++){ // Add more elements than initial capacity to trigger resizing
            map.put(i, i);
        }
        assertEquals(100, map.size());
        for(int i = 0; i < 100; i++){
            assertEquals(Integer.valueOf(i), map.get(i));
        }
    }


}


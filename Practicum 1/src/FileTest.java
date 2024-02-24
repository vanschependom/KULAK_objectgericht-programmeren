import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A class containing tests for the class of files.
 *
 * @version 2.0
 * @author  Vincent Van Schependom
 * @author  Flor De Meulemeester
 * @author  Arne Claerhout
 */
public class FileTest {

    private File file1, file2, file3;

    /**
     * Set up a mutable test fixture.
     * @post The variable file1 represents a file with name "Name1", size 20 that is writeable.
     * @post The variable file2 represents a file with name "Name2", size 100 that isn't writeable.
     * @post The variable file3 represents a file with name "Name3", size 30 that is writeable.
     */
    @BeforeEach
    public void setUpMutableFixture(){
        file1 = new File("Name1",20,true);
        file2 = new File("Name2",100,false);
        file3 = new File("Name3",30,true);
    }

    @Test
    public void extendedConstructor_LegalCase(){
        File theFile = new File("Filename123",1234,true);
        Assertions.assertEquals("Filename123",theFile.getName());
        Assertions.assertEquals(1234,theFile.getSize());
        Assertions.assertTrue(theFile.isWriteable());
    }

    @Test
    public void extendedConstructor_IllegalCase(){
        // The name of a file is implemented regarding total programming, we test an illegal name.
        File theFile = new File("$ù$#hjf&&",10,true);
        Assertions.assertEquals(null,theFile.getName());
        Assertions.assertEquals(10,theFile.getSize());
        Assertions.assertEquals(true,theFile.isWriteable());
    }

    @Test
    public void smallConstructor_LegalCase(){
        File theFile = new File("Name123");
        Assertions.assertEquals("Name123",theFile.getName());
        Assertions.assertEquals(0,theFile.getSize());
        Assertions.assertEquals(true,theFile.isWriteable());
    }

    @Test
    public void smallConstructor_IllegalCase(){
        // The name of a file is implemented regarding total programming, we test an illegal name.
        File theFile = new File("$ù$# hjf&&");
        Assertions.assertEquals(null,theFile.getName());
    }

    @Test
    public void changeLegalName() {
        // Changing the name of a file, modification time is changed
        Date date = new Date();
        file1.changeName("New-Name");
        Assertions.assertEquals("New-Name",file1.getName());
        Assertions.assertNotEquals(null, file1.getModificationTime());

    }

    @Test
    public void changeIllegalName1(){
        // Changing the name of a file into an illegal name, we expect the name to not change (total programming).
        // The modification is expected to still be null.
        file1.changeName("$#hjfbal$$"); // special characters not allowed
        Assertions.assertEquals("Name1",file1.getName());
        Assertions.assertNull(file1.getModificationTime());
    }

    @Test
    public void changeIllegalName2(){
        // Changing the name of a file into an illegal name, we expect the name to not change (total programming).
        // The modification is expected to still be null.
        file1.changeName("hjf bal"); // space not allowed
        Assertions.assertEquals("Name1",file1.getName());
        Assertions.assertNull(file1.getModificationTime());
    }

    @Test
    public void changeNameWithoutPermission(){
        // Changing the name of a file while the writeability is false, we expect that nothing changes (total programming).
        // The modification is expected to still be null.
        file2.changeName("name");
        Assertions.assertEquals("Name2", file2.getName());
        Assertions.assertNull(file2.getModificationTime());
    }

    @Test
    public void changeNameWithSameName() {
        //We expect the name to not change and the modification time to be null because it has not been set (total programming).
        file3.changeName("Name3");
        Assertions.assertEquals("Name3", file3.getName());
        Assertions.assertNull(file3.getModificationTime());
    }

    @Test
    public void enlargeSize(){
        // We only test legal cases because the size was implemented via nominal programming.
        // The modification time should now be set and not be equal to null.
        file1.enlarge(23);
        Assertions.assertEquals(43,file1.getSize());
        Assertions.assertNotEquals(null, file1.getModificationTime());
    }

    @Test
    public void shortenSize(){
        // We only test legal cases because the size was implemented via nominal programming.
        // The modification time should now be set and not be equal to null.
        file1.shorten(15);
        Assertions.assertEquals(5,file1.getSize());
        Assertions.assertNotEquals(null, file1.getModificationTime());
    }

    @Test
    public void hasOverlappingUsagePeriod1() throws InterruptedException {
        // Both files are changed so they both have a use period and the periods should overlap.
        TimeUnit.MILLISECONDS.sleep(2); // Wait, otherwise the overlap period isn't significant enough.
        file1.enlarge(30);
        TimeUnit.MILLISECONDS.sleep(10); // Wait, otherwise the overlap period isn't significant enough.
        file3.enlarge(30);
        Assertions.assertTrue(file1.hasOverlappingUsagePeriod(file3));
    }

    @Test
    public void hasOverlappingUsagePeriod2(){
        // Only one file is changed, so we expect false because usage period of file3 is not defined (yet).
        file1.enlarge(30);
        Assertions.assertFalse(file1.hasOverlappingUsagePeriod(file3));
    }

    @Test
    public void hasOverlappingUsagePeriod3(){
        file1.shorten(10); // Usage period of file1 ends
        File newFile = new File("This-file-is-created-after-file1-was-modified",20,true); // Usage period of newFile starts
        newFile.enlarge(20);
        Assertions.assertFalse(file1.hasOverlappingUsagePeriod(newFile));
    }

}

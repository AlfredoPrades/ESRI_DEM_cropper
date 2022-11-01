package net.terrik.demCropper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class DemCropperTest {

	DemCropper cropper;
	File inputFile;
	File outFile;
	private ClassLoader classLoader;
	
//    @Rule
//    public TemporaryFolder folder= new TemporaryFolder();
	
    @Before
    public void setUp() throws IOException {
    	classLoader = getClass().getClassLoader();
    	inputFile = new File(classLoader.getResource("20por20.asc").getFile());
    	//String smallDEM = FileUtils.readFileToString(inputFile,Charset.defaultCharset());
//    	System.out.println(smallDEM.length());
      	outFile = File.createTempFile( "junitOutFile", ".obj");
      	outFile.deleteOnExit();
    	
    }

    @Test                                               
    public void testCropper1() throws IOException {
    	cropper = new DemCropper(5, 15, 5, 15, inputFile, outFile);
    	cropper.process();
    	String smallDEM = FileUtils.readFileToString(outFile,Charset.defaultCharset());
    	File goodSample1 = new File(classLoader.getResource("salidaOk1.asc").getFile());
    	String goodOutput = FileUtils.readFileToString(goodSample1,Charset.defaultCharset());
    	Assert.assertEquals(smallDEM, goodOutput);
    }


    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters1() {
    	cropper = new DemCropper(16, 15, 5, 15, inputFile, outFile);
    	cropper.process();
    }

    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters11() {
    	cropper = new DemCropper(15, 15, 5, 15, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters2() {
    	cropper = new DemCropper(-16, 15, 5, 15, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters3() {
    	cropper = new DemCropper(5, 15, 16, 15, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters33() {
    	cropper = new DemCropper(5, 15, 15, 15, inputFile, outFile);
    	cropper.process();
    }

    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters4() {
    	cropper = new DemCropper(5, 15, -5, 15, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters5() {
    	cropper = new DemCropper(5, 25, 5, 15, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters6() {
    	cropper = new DemCropper(5, 15, 5, 25, inputFile, outFile);
    	cropper.process();
    }
    
    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters7() {
    	cropper = new DemCropper(-1, -15, -5, -25, inputFile, outFile);
    	cropper.process();
    }

    @Test (expected = InvalidParameterException.class)                                    
    public void testWithBadParameters8() {
    	File notFound = new File (inputFile.getAbsoluteFile()+"random");
    	cropper = new DemCropper(5, 15, 5, 15, notFound, outFile);
    	cropper.process();
    
    	
    }
    
}
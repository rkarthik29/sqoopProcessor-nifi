package org.apache.nifi.processors;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.attributes.CoreAttributes;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;


public class ExecuteSqoopTest {

    private TestRunner testRunner;

    @Before
    public void init() {
        testRunner = TestRunners.newTestRunner(ExecuteSqoop.class);
    }

    @Test
    public void testImport() {
    	
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://se-hdp251.field.hortonworks.com:3306/employees");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"root");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"password");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "import");
    	testRunner.setProperty(ExecuteSqoop.TABLE, "employees");
    	testRunner.setProperty(ExecuteSqoop.HDFS_CONFIG_RESOURCES, "/Users/knarayanan/sehdp25/core-site.xml,/Users/knarayanan/sehdp25/hive-site.xml,"
    			+ "/Users/knarayanan/sehdp25/mapred/mapred-site.xml,/Users/knarayanan/sehdp25/yarn/yarn-site.xml");
    	testRunner.run();

        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }
    @Test
    public void testExport() {
    	
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://se-hdp251.field.hortonworks.com:3306/employees");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"root");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"password");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "export");
    	testRunner.setProperty(ExecuteSqoop.TABLE, "employees1");
    	testRunner.setProperty(ExecuteSqoop.HDFS_CONFIG_RESOURCES, "/Users/knarayanan/sehdp25/core-site.xml,/Users/knarayanan/sehdp25/hive-site.xml,"
    			+ "/Users/knarayanan/sehdp25/mapred/mapred-site.xml,/Users/knarayanan/sehdp25/yarn/yarn-site.xml");
    	testRunner.run();

        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }
    @Test
    public void testImportAllTables() {
    	
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://se-hdp251.field.hortonworks.com:3306/employees");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"root");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"password");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "import-all-tables");
    	testRunner.setProperty(ExecuteSqoop.HDFS_CONFIG_RESOURCES, "/Users/knarayanan/sehdp25/core-site.xml,/Users/knarayanan/sehdp25/hive-site.xml,"
    			+ "/Users/knarayanan/sehdp25/mapred/mapred-site.xml,/Users/knarayanan/sehdp25/yarn/yarn-site.xml");
    	testRunner.setProperty("--warehouse-dir", "/user/knarayanan/employees2");
    	testRunner.setValidateExpressionUsage(false);
    	testRunner.run();
        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }

}

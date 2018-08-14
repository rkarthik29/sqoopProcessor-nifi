package org.apache.nifi.processors;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.nifi.hadoop.KerberosProperties;
import org.apache.nifi.sqoop.processors.ExecuteSqoop;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;


public class ExecuteSqoopTest {

    private TestRunner testRunner;
    private String base_dir="/tmp/kn-demo";
    private String config_path=base_dir+"/core-site.xml,"+base_dir+"/hdfs-site.xml,"
    		+base_dir+"/mapred-site.xml,"+base_dir+"/yarn-site.xml";

    @Before
    public void init() {
        testRunner = TestRunners.newTestRunner(ExecuteSqoop.class);
    }

    @Test
    public void testImport() {
    	testRunner.setProperty("nifi.kerberos.krb5.file", "/tmp/krb5.conf");
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://kn-demo2.field.hortonworks.com:3306/employees");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"test");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"Fresh$1234");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "import");
    	testRunner.setProperty(ExecuteSqoop.TABLE, "employees");
    	testRunner.setProperty(ExecuteSqoop.HADOOP_CONFIGURATION_RESOURCES, config_path);
    	KerberosProperties krbProps = new KerberosProperties(new File("/tmp/krb5.conf"));
    	testRunner.setProperty(krbProps.getKerberosKeytab(),"/tmp/smokeuser.headless.keytab");
    	testRunner.setProperty(krbProps.getKerberosPrincipal(),"ambari-qa-hdp@HWX.COM");
    	testRunner.run();

        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }
    @Test
    public void testImportAllTables() {
    	
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://kn-demo2.field.hortonworks.com:3306/employees");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"test");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"Fresh$1234");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "import-all-tables");
    	testRunner.setProperty(ExecuteSqoop.HADOOP_CONFIGURATION_RESOURCES, config_path);
    	testRunner.setProperty("--warehouse-dir", "/tmp/employees2");
    	testRunner.setProperty("-Dorg.apache.sqoop.splitter.allow_text_splitter", "true");
    	
    	testRunner.setValidateExpressionUsage(false);
    	testRunner.run();
        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }
    @Test
    public void testExport() {
    	
    	testRunner.setProperty(ExecuteSqoop.DB_URL,"jdbc:mysql://kn-demo2.field.hortonworks.com:3306/employees2");
    	testRunner.setProperty(ExecuteSqoop.DB_DRIVER, "com.mysql.jdbc.Driver");
    	testRunner.setProperty(ExecuteSqoop.DB_USER,"test");
    	testRunner.setProperty(ExecuteSqoop.DB_PASS,"Fresh$1234");
    	testRunner.setProperty(ExecuteSqoop.SQOOP_COMMAND, "export");
    	testRunner.setProperty(ExecuteSqoop.TABLE, "employees1");
    	testRunner.setProperty(ExecuteSqoop.HADOOP_CONFIGURATION_RESOURCES, config_path);
    	testRunner.setProperty("--export-dir", "/user/knarayanan/employees2");
    	testRunner.setValidateExpressionUsage(false);
    	testRunner.run();

        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(ExecuteSqoop.SUCCESS);
        assertEquals(1, flowFiles.size());
        //MockFlowFile flowFile = flowFiles.get(0);
        //assertEquals("randombytes-1", flowFile.getAttribute(CoreAttributes.FILENAME.key()));
        //assertEquals("target/test-classes", flowFile.getAttribute(PutHDFS.ABSOLUTE_HDFS_PATH_ATTRIBUTE));

    }

}

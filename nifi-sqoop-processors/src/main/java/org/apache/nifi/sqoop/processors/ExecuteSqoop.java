/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.sqoop.processors;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.InputRequirement.Requirement;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.processors.hadoop.AbstractHadoopProcessor;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.SqoopOptions;
import org.apache.sqoop.tool.SqoopTool;
import org.apache.sqoop.util.Jars;





@EventDriven
@InputRequirement(Requirement.INPUT_ALLOWED)
@Tags({"example"})
@CapabilityDescription("Provide a description")
@SeeAlso({})
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
@WritesAttributes({@WritesAttribute(attribute="", description="")})
public class ExecuteSqoop extends AbstractHadoopProcessor {
	
	static Logger logger = Logger.getLogger(ExecuteSqoop.class);
	
    public static final PropertyDescriptor SQOOP_COMMAND = new PropertyDescriptor
            .Builder().name("SQOOP_COMMAND")
            .displayName("Sqoop command")
            .description("Sqoop command to perform")
            .allowableValues("import","import-all-tables","export")
            .required(true)
            .defaultValue("import")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    
    public static final PropertyDescriptor DB_URL = new PropertyDescriptor
            .Builder().name("DB_URL")
            .displayName("jdbc connection string")
            .description("jdbc connection string")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor DB_DRIVER = new PropertyDescriptor
            .Builder().name("DB_DRIVER")
            .displayName("jdbc driver")
            .description("jdbc driver")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor DB_USER = new PropertyDescriptor
            .Builder().name("DB_USER")
            .displayName("username")
            .description("Database username")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    
    public static final PropertyDescriptor DB_PASS = new PropertyDescriptor
            .Builder().name("DB_PASS")
            .displayName("password")
            .description("Database password")
            .sensitive(true)
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    
    public static final PropertyDescriptor TABLE = new PropertyDescriptor
            .Builder().name("TABLE")
            .displayName("DB Table Name")
            .description("DB Table Name")
            .required(false)
            .addValidator(StandardValidators.ATTRIBUTE_EXPRESSION_LANGUAGE_VALIDATOR)
            .build();

    public static final Relationship SUCCESS = new Relationship.Builder()
            .name("Success")
            .description("Success relationship")
            .build();
    public static final Relationship FAILURE = new Relationship.Builder()
            .name("Failure")
            .description("Failure relationship")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;
    private Sqoop sqoop;

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        final List<PropertyDescriptor> descriptors = new ArrayList<>(properties);
        descriptors.add(SQOOP_COMMAND);
        descriptors.add(DB_URL);
        descriptors.add(DB_USER);
        descriptors.add(DB_PASS);
        descriptors.add(TABLE);
        descriptors.add(DB_DRIVER);
        return descriptors;
        
    }

    @Override
    public Set<Relationship> getRelationships() {
    	final Set<Relationship> relationships = new HashSet<Relationship>();
        relationships.add(SUCCESS);
        relationships.add(FAILURE);
        this.relationships = Collections.unmodifiableSet(relationships);
        return this.relationships;
    }
    
    
    @Override
    protected PropertyDescriptor getSupportedDynamicPropertyDescriptor(final String propertyDescriptorName) {
        return new PropertyDescriptor.Builder()
                .required(false)
                .name(propertyDescriptorName)
                .dynamic(true)
                .addValidator(StandardValidators.ATTRIBUTE_EXPRESSION_LANGUAGE_VALIDATOR)
                .expressionLanguageSupported(true)
                .build();
    }
    @OnScheduled
    public void onScheduled(final ProcessContext context) throws IOException,ProcessException{
    	super.abstractOnScheduled(context);
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();
        if(flowFile==null){
        	flowFile=session.create();
        }
    	try{
    		
            //final FileSystem hdfs = getFileSystem();
            final Configuration configuration = getConfiguration();
            configuration.set("hdp.version", "2.6.5.0-292");
           SqoopOptions options = new SqoopOptions();
            String sqoopPath = Jars.getSqoopJarPath();
            options.setHadoopMapRedHome(sqoopPath.substring(0,sqoopPath.lastIndexOf('/')));
            SqoopTool tool = SqoopTool.getTool(context.getProperty(SQOOP_COMMAND).getValue());
            sqoop = new Sqoop(tool,configuration,options);
            
    		final UserGroupInformation ugi = getUserGroupInformation();
    		ArrayList<String> arguments = new ArrayList<String>();
            //arguments.add(context.getProperty(SQOOP_COMMAND).getValue() );
            for (final PropertyDescriptor descriptor : context.getProperties().keySet()) {
                if (descriptor.isDynamic()) {
                	if(descriptor.getName().startsWith("-D") ){
                        arguments.add(descriptor.getName()+"="+context.getProperty(descriptor).getValue());
                    	}
                }
            }
            arguments.add("--driver");
            arguments.add(context.getProperty(DB_DRIVER).getValue());
            arguments.add("--connect");
            arguments.add(context.getProperty(DB_URL).getValue());
            arguments.add("--username");
            arguments.add(context.getProperty(DB_USER).getValue());
            arguments.add("--password");
            arguments.add(context.getProperty(DB_PASS).getValue());
            arguments.add("--verbose");
            if(!"import-all-tables".equals(context.getProperty(SQOOP_COMMAND).getValue())){
          	  arguments.add("--table");
          	  arguments.add(context.getProperty(TABLE).getValue());
            }
            for (final PropertyDescriptor descriptor : context.getProperties().keySet()) {
                if (descriptor.isDynamic()) {
                	if(descriptor.getName().startsWith("--") ){
                    arguments.add(descriptor.getName());
                    arguments.add(context.getProperty(descriptor).getValue());
                	}
                }
            }
            String[] sqoopArguments = arguments.toArray(new String[0]);
            System.setProperty("HADOOP_HOME","/tmp/kn_demo");
    		System.setProperty(Sqoop.SQOOP_RETHROW_PROPERTY, "true");
    		System.setProperty("hdp.version", "2.6.5.0-292");
    		//System.out.println(Jars.getSqoopJarPath()+"--"+Jars.getJarPathForClass(Sqoop.class));
    		//int status = Sqoop.runTool(sqoopArguments, config);
    		Integer status = ugi.doAs(new PrivilegedAction<Integer>(){
    			@Override
       			 public Integer run() {
				 return Sqoop.runSqoop(sqoop, sqoopArguments);
				         }});
    		//Integer status = Sqoop.runSqoop(sqoop, sqoopArguments);
    		flowFile = session.putAttribute(flowFile, "status", ""+status);
    		session.transfer(flowFile,SUCCESS);
            }catch(RuntimeException ex){
            	session.transfer(flowFile,FAILURE);
            	System.out.println(ex.getMessage());
            	ex.printStackTrace();
            }
        // TODO implement
    }

    
    /*private class SqoopAction implements PrivilegedAction<Object>{
    			 public int status;
    			 public String[] sqoopArguments;
    			 public SqoopAction(String[] sqoopArguments){
    				 this.sqoopArguments=sqoopArguments;
    			 }
    			 @Override
    			 public Object run() {
    				 this.status = Sqoop.runSqoop(sqoop, sqoopArguments);
    				 return this.status;
    				         }
    }*/
}

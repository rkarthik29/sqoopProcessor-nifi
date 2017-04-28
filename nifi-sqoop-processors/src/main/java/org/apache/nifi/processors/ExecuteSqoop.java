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
package org.apache.nifi.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.AttributeExpression.ResultType;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.sqoop.Sqoop;



@Tags({"example"})
@CapabilityDescription("Provide a description")
@SeeAlso({})
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
@WritesAttributes({@WritesAttribute(attribute="", description="")})
public class ExecuteSqoop extends AbstractProcessor {
	
	static Logger logger = Logger.getLogger(ExecuteSqoop.class);
	
	public static final PropertyDescriptor HDFS_CONFIG_RESOURCES = new PropertyDescriptor
            .Builder().name("HDFS_CONFIG_RESOURCES")
            .displayName("comma seperated list of hdfs config resources")
            .description("core-site,hdfs-site,mapred-site,yarn-site configuration files.")
            .required(false)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
	
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

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;
    
    private String[] sqoopArguments;
    private Configuration config;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
        descriptors.add(SQOOP_COMMAND);
        descriptors.add(DB_URL);
        descriptors.add(DB_USER);
        descriptors.add(DB_PASS);
        descriptors.add(TABLE);
        descriptors.add(HDFS_CONFIG_RESOURCES);
        descriptors.add(DB_DRIVER);
        this.descriptors = Collections.unmodifiableList(descriptors);

        final Set<Relationship> relationships = new HashSet<Relationship>();
        relationships.add(SUCCESS);
        this.relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors;
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
    public void onScheduled(final ProcessContext context) {
          ArrayList<String> arguments = new ArrayList<String>();
          arguments.add(context.getProperty(SQOOP_COMMAND).getValue() );
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
          config = new Configuration();
          if(context.getProperty(HDFS_CONFIG_RESOURCES).isSet()){
        	  String configResources=context.getProperty(HDFS_CONFIG_RESOURCES).getValue();
        	  String[] resources = configResources.split(",");
	          for (String resource : resources) {
	          	config.addResource(new Path(resource.trim()));
	          }
          }
          
          for (final PropertyDescriptor descriptor : context.getProperties().keySet()) {
              if (descriptor.isDynamic()) {
                  arguments.add(descriptor.getName());
                  arguments.add(context.getProperty(descriptor).getValue());
              }
          }
          try{
        	  sqoopArguments = arguments.toArray(new String[0]);
          }catch(RuntimeException ex){
        	  throw ex;
          }
    }
    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();
        if(flowFile==null){
        	flowFile=session.create();
        }
    	try{
    		System.setProperty(Sqoop.SQOOP_RETHROW_PROPERTY, "true");
    		int status = Sqoop.runTool(sqoopArguments, config);
    		flowFile = session.putAttribute(flowFile, "status", ""+status);
    		session.transfer(flowFile,SUCCESS);
            }catch(RuntimeException ex){
            	System.out.println(ex.getMessage());
            	ex.printStackTrace();
            }
        // TODO implement
    }
}

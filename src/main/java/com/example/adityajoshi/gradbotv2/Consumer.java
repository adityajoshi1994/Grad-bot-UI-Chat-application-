package com.example.adityajoshi.gradbotv2;

import android.util.Log;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {
	//public static void main(String args[]){
		public void consume_data(){
		Properties props = new Properties();
	     props.put("bootstrap.servers", "169.234.48.186:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
		//	String a[] = {"foo","test"};
	     consumer.subscribe(Arrays.asList("foo","test"));
			//consumer.subscribe("Test");
	     
	     try {

	    		/**** Connect to MongoDB ****/
	    		// Since 2.10.0, uses MongoClient
	    		//MongoClient mongo = new MongoClient("localhost", 27017);

	    		/**** Get database ****/
	    		// if database doesn't exists, MongoDB will create it for you
	    		//DB db = mongo.getDB("testdb");

	    		/**** Get collection / table from 'testdb' ****/
	    		// if collection doesn't exists, MongoDB will create it for you
	    		//DBCollection table = db.getCollection("user");

	    		/**** Find and display ****/
	    		//DBCursor cursor = table.find();
				/*
	    		while (cursor.hasNext()) {
	    			System.out.println(cursor.next());
	    		}
	    		*/
	   	     	while (true) {
		         //ConsumerRecords<String, String> records = consumer.poll(100);
		         ConsumerRecords<String, String> records = consumer.poll(1000);
					for (ConsumerRecord<String, String> record : records){
		        	 Log.i("Consumer Data",record.key() +" "+record.value());
		        	 //String json = "{'"+record.key() +"':'"+record.value()+"'}";
			    		//String json = record
						//DBObject dbObject = (DBObject)JSON.parse(json);
						//table.insert(dbObject);
		             //System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
						 //DBCursor cursor2 = table.find();
				    		//while (cursor2.hasNext()) {
				    		//	System.out.println(cursor2.next());
				    		//}
		         }
		     }	
	   	     /**** Find and display ****/
	   	

	    	    } catch (Exception e) {
	    		e.printStackTrace();
	    	    }
	     

	}
}

package com.yalatin.mongodb;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;


public class mongodb extends Thread {
    public Mongo mongo_conn;
    public String dbname;
    public String operation;
    public long len;
    public int threadnum;

    public mongodb(String operation,long len,int threadnum){
        this.mongo_conn = null;
        this.dbname = null;
        this.operation = operation;
        this.len = len;
        this.threadnum = threadnum;
    }

    public mongodb(String operation,long len,String host,String dbname,int threadnum){
        this.mongo_conn = null;
        this.dbname = dbname;
        this.operation = operation;
        this.len = len;
        this.threadnum = threadnum;
        this.mongodb_connect(host,27017);
    }

    public mongodb(String operation,long len,String host,int port,String dbname,int threadnum){
        this.mongo_conn = null;
        this.dbname = dbname;
        this.operation = operation;
        this.len = len;
        this.threadnum = threadnum;
        this.mongodb_connect(host,port);
    }

    public Mongo mongodb_connect(String host,int port){

        try {
            this.mongo_conn = new Mongo(host, port);
        } catch (UnknownHostException ex) {
            System.out.println("UnknownHostException:" + ex.getMessage());
        } catch (MongoException ex) {
            System.out.println("Mongo Exception:" + ex.getMessage());
            System.out.println("Mongo error code:" + ex.getCode());
        }

        return this.mongo_conn;
    }

    @Override
    public void run(){

        if ( this.operation.equals("insert") ) {
            this.mongodb_insert();
        } else if ( this.operation.equals("update") ) {
            this.mongodb_update();
        } else {
            this.mongodb_select();
        }

    }

    public void mongodb_insert(){

        DB db = this.mongo_conn.getDB( this.dbname );

        //DBCollection dbcoll = db.getCollection("test" + String.valueOf(this.threadnum) );
        DBCollection dbcoll = db.getCollection("test" );
        String str = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456";
        for (long j = 0; j < this.len; j++) {
            DBObject dblist = new BasicDBObject();
            dblist.put("_id", j);
            dblist.put("count", j);
            dblist.put("test1", str);
            dblist.put("test2", str);
            dblist.put("test3", str);
            dblist.put("test4", str);
            try {
                dbcoll.insert(dblist);
            } catch (MongoException ex) {
                System.out.println("Mongo Exception:" + ex.getMessage());
                System.out.println("Mongo error code:" + ex.getCode());
            }
        }

    }

    public void mongodb_update(){

        DB db = this.mongo_conn.getDB( this.dbname );

        //DBCollection dbcoll = db.getCollection("test" + String.valueOf(this.threadnum) );
        DBCollection dbcoll = db.getCollection("test" );
        String str = "UPDATE7890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456";
        for (long j = 0; j < this.len; j++) {
            DBObject dblist = new BasicDBObject();
            DBObject qlist = new BasicDBObject();
            qlist.put("_id", j);
            dblist.put("count", j);
            dblist.put("test1", str);
            dblist.put("test2", str);
            dblist.put("test3", str);
            dblist.put("test4", str);
            try {
                dbcoll.update(qlist,dblist);
            } catch (MongoException ex) {
                System.out.println("Mongo Exception:" + ex.getMessage());
                System.out.println("Mongo error code:" + ex.getCode());
            }
        }
    }

    @SuppressWarnings("unused")
  public void mongodb_select(){

        DB db = this.mongo_conn.getDB( this.dbname );

        //DBCollection dbcoll = db.getCollection("test" + String.valueOf(this.threadnum) );
        DBCollection dbcoll = db.getCollection("test" );

        for (long j = 0; j < this.len; j++) {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", j);
            try {
                List<DBObject> objre =  dbcoll.find(query).toArray();
                
//                for ( Object x : objre ) {
//                     System.out.println(x);
//                }
            } catch (MongoException ex) {
                System.out.println("Mongo Exception:" + ex.getMessage());
                System.out.println("Mongo error code:" + ex.getCode());
            }
        }
    }
}

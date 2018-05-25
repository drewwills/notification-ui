CREATE TABLE NOTIFY.TOPIC_DETAILS(	
    TOPIC_ID VARCHAR2(60 BYTE), 
    TOPIC_DESCRIPTION NVARCHAR2(255), 
    PUBLISHER_ID VARCHAR2(10 BYTE),
    STATUS VARCHAR2(1 BYTE), 
    LAST_UPDATED DATE DEFAULT SYSDATE,
    CONSTRAINT TOPIC_PK PRIMARY KEY (TOPIC_ID),

    CONSTRAINT TOPIC_FK
    FOREIGN KEY (PUBLISHER_ID)
    REFERENCES PUBLISHER_DETAILS (PUBLISHER_ID)  
);
  
COMMENT ON COLUMN NOTIFY.TOPIC_DETAILS.TOPIC_ID IS 'The unique id of topic';
COMMENT ON COLUMN NOTIFY.TOPIC_DETAILS.TOPIC_DESCRIPTION IS 'The description of the topic';
COMMENT ON COLUMN NOTIFY.TOPIC_DETAILS.PUBLISHER_ID IS 'The unique id of publisher that publishes this topic';  
COMMENT ON COLUMN NOTIFY.TOPIC_DETAILS.STATUS IS 'Status of topic, (A)ctive or (I)nactive';
COMMENT ON COLUMN NOTIFY.TOPIC_DETAILS.LAST_UPDATED IS 'Last updated date';
  
CREATE OR REPLACE PUBLIC SYNONYM TOPIC_DETAILS FOR NOTIFY.TOPIC_DETAILS;
GRANT SELECT,INSERT,UPDATE,DELETE ON TOPIC_DETAILS TO NOTIFY_USER;



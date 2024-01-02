package com.lambda.user.collection;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable( tableName = "usuarios")
public class User {

    @DynamoDBHashKey
    private String id;
    @DynamoDBAttribute(attributeName = "nombre")
    private String name;
    @DynamoDBAttribute(attributeName = "cedula")
    private String identificationDocument;

    public User() {
    }

    public User(String id, String name, String identificationDocument) {
        this.id = id;
        this.name = name;
        this.identificationDocument = identificationDocument;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationDocument() {
        return identificationDocument;
    }

    public void setIdentificationDocument(String identificationDocument) {
        this.identificationDocument = identificationDocument;
    }
}

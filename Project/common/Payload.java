package Project.common;

import java.io.Serializable;

public class Payload implements Serializable {
    // read https://www.baeldung.com/java-serial-version-uid
    private static final long serialVersionUID = 1L;// change this if the class changes

    /**
     * Determines how to process the data on the receiver's side
     */
    private PayloadType payloadType;

    public PayloadType getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
    }

    /**
     * Who the payload is from
     */
    private String clientName;

    /*Get and return the vaule of clientName.
    This will be a String that represent the name of 
    client connect to each payload.
    */
    public String getClientName() {
        return clientName;
    }
/*Assign the name of the clients (clientName)
 to the Payload objects */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
/*Store individual ID connected with client */
    private long clientId;
/* /*Get and return the vaule of clientID.
    This will be a String that represent the ID of 
    client connect to each payload.
     */

    public long getClientId() {
        return clientId;
    }

    /*Assign the ID of the clients */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Generic text based message
     */
    private String message;
   //get and return the message variable
    public String getMessage() {
        return message;
    }
    //Assign new value to the message variable
    public void setMessage(String message) {
        this.message = message;
    }
/*override the toString method 
and concatenate getClientId, 
getClientName,and getMessage*/
    @Override
    public String toString() {
        return String.format("Type[%s],ClientId[%s,] ClientName[%s], Message[%s]", getPayloadType().toString(),
                getClientId(), getClientName(),
                getMessage());
    }
}

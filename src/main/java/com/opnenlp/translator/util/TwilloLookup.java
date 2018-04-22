package main.java.com.opnenlp.translator.util;

import com.twilio.Twilio;
import com.twilio.rest.lookups.v1.PhoneNumber;

public class TwilloLookup {

    private String TWILIO_SID = "AC13e2387250f7541fd853103c1be39ec0";
    private String TWILIO_AUTH_TOKEN = "e8e5fbc92f6f388ed1290ae01bbc1f0d";

    public void initTwillo() {
        Twilio.init(TWILIO_SID, TWILIO_AUTH_TOKEN);
    }

    public static String phoneNumberLookup(String phoneNumber) {

        PhoneNumber number = PhoneNumber
                .fetcher(new com.twilio.type.PhoneNumber(phoneNumber))
                .setType("carrier")
                .fetch();

        return number.getCarrier().get("name");
    }
}

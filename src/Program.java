import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.conf.check.DefaultValidator;
import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;
import  ca.uhn.hl7v2.model.v251.message.*;
import ca.uhn.hl7v2.parser.PipeParser;


import java.nio.file.*;



public class Program
{


    public static void main(String[] args) {

        try {


            ProfileParser parser = new ProfileParser(false);
            RuntimeProfile profile = parser.parseClasspath("net\\HL7Conformance\\" + "PATIENTADMITSPEC-ADT_A01_20200607.xml");

            String message = readHl7FileDataAsString("C:\\Hanuma\\POCs\\HL7Conformance\\src\\net\\HL7Conformance\\InputMessage.txt");

            //Parsing it to A01 event.
            //TOD0: Make this generic by reading the message type in the MSH segment
            ADT_A01 msg = (ADT_A01) (new PipeParser()).parse(message);

            HL7Exception[] errors = new DefaultValidator().validate(msg, profile.getMessage());

            for (HL7Exception hl7Exception : errors) {

                System.err.println(hl7Exception);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //This method will read the file content
    public static String readHl7FileDataAsString(String fileName) throws Exception
    {
       return new String(Files.readAllBytes(Paths.get(fileName)));

    }

}

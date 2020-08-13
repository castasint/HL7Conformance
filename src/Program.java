import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.conf.check.DefaultValidator;
import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;
import ca.uhn.hl7v2.conf.store.ProfileCodeStore;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.KHIEUtil;
import ca.uhn.hl7v2.validation.ValidationContext;
import ca.uhn.hl7v2.validation.impl.ValidationContextFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) {
        try {
            ProfileParser parser = new ProfileParser ( false );
            RuntimeProfile profile = parser.parseClasspath ( "net\\HL7Conformance\\" + "PATIENTADMITSPEC-ADT_A01_20200607.xml" );
            String message = readHl7FileDataAsString ( "C:\\Hanuma\\POCs\\HL7Conformance\\src\\net\\HL7Conformance\\InputMessage.txt" );
            System.err.println ( "PARSING START" );
            PipeParser pipeParser = new PipeParser ();
            pipeParser.setValidationContext ( ValidationContextFactory.defaultValidation () );
            Message msg = pipeParser.parse ( message );
            var validator = new DefaultValidator ();
            ProfileCodeStore profileCodeStore = new ProfileCodeStore ( "C:\\Hanuma\\POCs\\HL7Conformance\\profiles\\999.xml" );
            validator.setCodeStore ( profileCodeStore );
            HL7Exception[] errors = validator.validate ( msg, profile.getMessage () );
            for (HL7Exception hl7Exception : errors) {
                System.err.println (
                        hl7Exception.getLocation () + "\t" + hl7Exception.getMessage () + "\t"
                );
            }
            System.err.println (
                    "KHIE UTIL EXCEPTIONS"
            );
            for (HL7Exception exception : KHIEUtil.HL7Errors) {
                System.err.println (
                        exception.getLocation () + "\t" + exception.getMessage () + "\t"
                );
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static String readHl7FileDataAsString(String fileName) throws Exception {
        return new String ( Files.readAllBytes ( Paths.get ( fileName ) ) );
    }
}

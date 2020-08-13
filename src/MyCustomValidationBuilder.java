import ca.uhn.hl7v2.validation.builder.Predicate;
import ca.uhn.hl7v2.validation.builder.PrimitiveRuleBuilder;
import ca.uhn.hl7v2.validation.builder.ValidationRuleBuilder;
import ca.uhn.hl7v2.validation.builder.support.DefaultValidationWithoutTNBuilder;

public class MyCustomValidationBuilder extends DefaultValidationWithoutTNBuilder {
    public MyCustomValidationBuilder() {
        System.out.println ( "Creating an Instance of MyCustomValidationBuilder" );
    }

    protected void configure() {
        forAllVersions ()
                .message ().all ().choiceElementsRespected ()
                .message ().all ().onlyAllowableSegmentsInSuperStructure ()
                .primitive ( "FT" ).leftTrim ( maxLength ( 32000 ) )
                .primitive ( "ST" ).leftTrim ()
                .primitive ( "TX" ).rightTrim ()
                .primitive ( "ID", "IS" ).is ( maxLength ( 200 ) )
                .primitive ( "SI" ).is ( emptyOr ( nonNegativeInteger () ) )
                .primitive ( "NM" ).is ( emptyOr ( number () ) )
                .primitive ( "DT" )
                .refersToSection ( "Version 2.5 Section 2.A.21" )
                .is ( emptyOr ( date () ) )
                .primitive ( "TM" )
                .refersToSection ( "Version 2.5 Section 2.A.75" )
                .is ( emptyOr ( time () ) )
                .primitive ( "NULLDT" ).is ( withdrawn () );
        this.forAllVersions ().
                primitive ( "TSComponentOne", "DTM" )
                .refersToSection ( "Version 2.3.1" )
                .is ( emptyOr ( customDateFormat () ) );
    }

    public Predicate customDateFormat() {
        return matches ( "^\\d{50}",
                "Value Should be a Date" );
    }
}
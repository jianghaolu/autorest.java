package fixtures.bodycomplex.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The MyBaseType model.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind", defaultImpl = MyBaseType.class)
@JsonTypeName("MyBaseType")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "Kind1", value = MyDerivedType.class)
})
@JsonFlatten
@Fluent
public class MyBaseType {
    /*
     * MISSING·SCHEMA-DESCRIPTION-STRING
     */
    @JsonProperty(value = "propB1")
    private String propB1;

    /*
     * MISSING·SCHEMA-DESCRIPTION-STRING
     */
    @JsonProperty(value = "propBH1")
    private String propBH1;

    /**
     * Get the propB1 property: MISSING·SCHEMA-DESCRIPTION-STRING.
     * 
     * @return the propB1 value.
     */
    public String getPropB1() {
        return this.propB1;
    }

    /**
     * Set the propB1 property.
     * 
     * @param propB1 the propB1 value to set.
     * @return the MyBaseType object itself.
     */
    public MyBaseType setPropB1(String propB1) {
        this.propB1 = propB1;
        return this;
    }

    /**
     * Get the propBH1 property: MISSING·SCHEMA-DESCRIPTION-STRING.
     * 
     * @return the propBH1 value.
     */
    public String getPropBH1() {
        return this.propBH1;
    }

    /**
     * Set the propBH1 property.
     * 
     * @param propBH1 the propBH1 value to set.
     * @return the MyBaseType object itself.
     */
    public MyBaseType setPropBH1(String propBH1) {
        this.propBH1 = propBH1;
        return this;
    }

    public void validate() {
    }
}

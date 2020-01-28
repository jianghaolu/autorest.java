package fixtures.xmlservice.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Map;

/**
 * The Blob model.
 */
@JacksonXmlRootElement(localName = "Blob")
@Fluent
public final class Blob {
    /*
     * MISSING·SCHEMA-DESCRIPTION-STRING
     */
    @JsonProperty(value = "Name", required = true)
    private String name;

    /*
     * MISSING·SCHEMA-DESCRIPTION-BOOLEAN
     */
    @JsonProperty(value = "Deleted", required = true)
    private boolean deleted;

    /*
     * MISSING·SCHEMA-DESCRIPTION-STRING
     */
    @JsonProperty(value = "Snapshot", required = true)
    private String snapshot;

    /*
     * Properties of a blob
     */
    @JsonProperty(value = "Properties", required = true)
    private BlobProperties properties;

    /*
     * Dictionary of
     * <paths·xml-headers·get·responses·200·headers·custom_header·schema>
     */
    @JsonProperty(value = "Metadata")
    private Map<String, String> metadata;

    /**
     * Get the name property: MISSING·SCHEMA-DESCRIPTION-STRING.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property.
     * 
     * @param name the name value to set.
     * @return the Blob object itself.
     */
    public Blob setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the deleted property: MISSING·SCHEMA-DESCRIPTION-BOOLEAN.
     * 
     * @return the deleted value.
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Set the deleted property.
     * 
     * @param deleted the deleted value to set.
     * @return the Blob object itself.
     */
    public Blob setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    /**
     * Get the snapshot property: MISSING·SCHEMA-DESCRIPTION-STRING.
     * 
     * @return the snapshot value.
     */
    public String getSnapshot() {
        return this.snapshot;
    }

    /**
     * Set the snapshot property.
     * 
     * @param snapshot the snapshot value to set.
     * @return the Blob object itself.
     */
    public Blob setSnapshot(String snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    /**
     * Get the properties property: Properties of a blob.
     * 
     * @return the properties value.
     */
    public BlobProperties getProperties() {
        return this.properties;
    }

    /**
     * Set the properties property: Properties of a blob.
     * 
     * @param properties the properties value to set.
     * @return the Blob object itself.
     */
    public Blob setProperties(BlobProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the metadata property: Dictionary of
     * &lt;paths·xml-headers·get·responses·200·headers·custom_header·schema&gt;.
     * 
     * @return the metadata value.
     */
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    /**
     * Set the metadata property: Dictionary of
     * &lt;paths·xml-headers·get·responses·200·headers·custom_header·schema&gt;.
     * 
     * @param metadata the metadata value to set.
     * @return the Blob object itself.
     */
    public Blob setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public void validate() {
        if (getName() == null) {
            throw new IllegalArgumentException("Missing required property name in model Blob");
        }
        if (getSnapshot() == null) {
            throw new IllegalArgumentException("Missing required property snapshot in model Blob");
        }
        if (getProperties() == null) {
            throw new IllegalArgumentException("Missing required property properties in model Blob");
        } else {
            getProperties().validate();
        }
    }
}
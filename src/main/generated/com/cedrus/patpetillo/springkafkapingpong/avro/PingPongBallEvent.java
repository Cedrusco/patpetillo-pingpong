/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.cedrus.patpetillo.springkafkapingpong.avro;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
/** Represents a Ping Pong Ball Event */
@org.apache.avro.specific.AvroGenerated
public class PingPongBallEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3656764923809371054L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PingPongBallEvent\",\"namespace\":\"com.cedrus.patpetillo.springkafkapingpong.avro\",\"doc\":\"Represents a Ping Pong Ball Event\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"pingPongTeam\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"color\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String id;
  @Deprecated public java.lang.String pingPongTeam;
  @Deprecated public java.lang.String color;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PingPongBallEvent() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param pingPongTeam The new value for pingPongTeam
   * @param color The new value for color
   */
  public PingPongBallEvent(java.lang.String id, java.lang.String pingPongTeam, java.lang.String color) {
    this.id = id;
    this.pingPongTeam = pingPongTeam;
    this.color = color;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return pingPongTeam;
    case 2: return color;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.String)value$; break;
    case 1: pingPongTeam = (java.lang.String)value$; break;
    case 2: color = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'pingPongTeam' field.
   * @return The value of the 'pingPongTeam' field.
   */
  public java.lang.String getPingPongTeam() {
    return pingPongTeam;
  }

  /**
   * Sets the value of the 'pingPongTeam' field.
   * @param value the value to set.
   */
  public void setPingPongTeam(java.lang.String value) {
    this.pingPongTeam = value;
  }

  /**
   * Gets the value of the 'color' field.
   * @return The value of the 'color' field.
   */
  public java.lang.String getColor() {
    return color;
  }

  /**
   * Sets the value of the 'color' field.
   * @param value the value to set.
   */
  public void setColor(java.lang.String value) {
    this.color = value;
  }

  /**
   * Creates a new PingPongBallEvent RecordBuilder.
   * @return A new PingPongBallEvent RecordBuilder
   */
  public static com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder newBuilder() {
    return new com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder();
  }

  /**
   * Creates a new PingPongBallEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PingPongBallEvent RecordBuilder
   */
  public static com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder newBuilder(com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder other) {
    return new com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder(other);
  }

  /**
   * Creates a new PingPongBallEvent RecordBuilder by copying an existing PingPongBallEvent instance.
   * @param other The existing instance to copy.
   * @return A new PingPongBallEvent RecordBuilder
   */
  public static com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder newBuilder(com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent other) {
    return new com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder(other);
  }

  /**
   * RecordBuilder for PingPongBallEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PingPongBallEvent>
    implements org.apache.avro.data.RecordBuilder<PingPongBallEvent> {

    private java.lang.String id;
    private java.lang.String pingPongTeam;
    private java.lang.String color;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.pingPongTeam)) {
        this.pingPongTeam = data().deepCopy(fields()[1].schema(), other.pingPongTeam);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.color)) {
        this.color = data().deepCopy(fields()[2].schema(), other.color);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing PingPongBallEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.pingPongTeam)) {
        this.pingPongTeam = data().deepCopy(fields()[1].schema(), other.pingPongTeam);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.color)) {
        this.color = data().deepCopy(fields()[2].schema(), other.color);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'pingPongTeam' field.
      * @return The value.
      */
    public java.lang.String getPingPongTeam() {
      return pingPongTeam;
    }

    /**
      * Sets the value of the 'pingPongTeam' field.
      * @param value The value of 'pingPongTeam'.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder setPingPongTeam(java.lang.String value) {
      validate(fields()[1], value);
      this.pingPongTeam = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'pingPongTeam' field has been set.
      * @return True if the 'pingPongTeam' field has been set, false otherwise.
      */
    public boolean hasPingPongTeam() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'pingPongTeam' field.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder clearPingPongTeam() {
      pingPongTeam = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'color' field.
      * @return The value.
      */
    public java.lang.String getColor() {
      return color;
    }

    /**
      * Sets the value of the 'color' field.
      * @param value The value of 'color'.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder setColor(java.lang.String value) {
      validate(fields()[2], value);
      this.color = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'color' field has been set.
      * @return True if the 'color' field has been set, false otherwise.
      */
    public boolean hasColor() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'color' field.
      * @return This builder.
      */
    public com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent.Builder clearColor() {
      color = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public PingPongBallEvent build() {
      try {
        PingPongBallEvent record = new PingPongBallEvent();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.pingPongTeam = fieldSetFlags()[1] ? this.pingPongTeam : (java.lang.String) defaultValue(fields()[1]);
        record.color = fieldSetFlags()[2] ? this.color : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}

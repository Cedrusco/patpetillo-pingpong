/**
 * Autogenerated by Avro
 *
 * <p>DO NOT EDIT DIRECTLY
 */
package com.cedrus.patpetillo.springkafkapingpong.avro;

@SuppressWarnings("all")
/** Represents the teams playing Ping Pong */
@org.apache.avro.specific.AvroGenerated
public enum TeamType {
  BLUE_TEAM,
  RED_TEAM;
  public static final org.apache.avro.Schema SCHEMA$ =
      new org.apache.avro.Schema.Parser()
          .parse(
              "{\"type\":\"enum\",\"name\":\"TeamType\",\"namespace\":\"com.cedrus.patpetillo.springkafkapingpong.avro\",\"doc\":\"Represents the teams playing Ping Pong\",\"symbols\":[\"BLUE_TEAM\",\"RED_TEAM\"]}");

  public static org.apache.avro.Schema getClassSchema() {
    return SCHEMA$;
  }
}

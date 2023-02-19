// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: broker/address.proto

package org.doodle.broker;

public interface BrokerAddressOrBuilder extends
    // @@protoc_insertion_point(interface_extends:doodle.broker.BrokerAddress)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.doodle.broker.BrokerRouteId route_id = 1;</code>
   * @return Whether the routeId field is set.
   */
  boolean hasRouteId();
  /**
   * <code>.doodle.broker.BrokerRouteId route_id = 1;</code>
   * @return The routeId.
   */
  org.doodle.broker.BrokerRouteId getRouteId();
  /**
   * <code>.doodle.broker.BrokerRouteId route_id = 1;</code>
   */
  org.doodle.broker.BrokerRouteIdOrBuilder getRouteIdOrBuilder();

  /**
   * <code>int32 flag = 2;</code>
   * @return The flag.
   */
  int getFlag();

  /**
   * <code>map&lt;string, string&gt; tags = 3;</code>
   */
  int getTagsCount();
  /**
   * <code>map&lt;string, string&gt; tags = 3;</code>
   */
  boolean containsTags(
      java.lang.String key);
  /**
   * Use {@link #getTagsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getTags();
  /**
   * <code>map&lt;string, string&gt; tags = 3;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getTagsMap();
  /**
   * <code>map&lt;string, string&gt; tags = 3;</code>
   */

  /* nullable */
java.lang.String getTagsOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; tags = 3;</code>
   */

  java.lang.String getTagsOrThrow(
      java.lang.String key);
}

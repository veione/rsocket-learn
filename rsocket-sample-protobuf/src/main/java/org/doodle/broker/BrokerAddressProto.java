// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: broker/address.proto

package org.doodle.broker;

public final class BrokerAddressProto {
  private BrokerAddressProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_doodle_broker_BrokerAddress_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_doodle_broker_BrokerAddress_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_doodle_broker_BrokerAddress_TagsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_doodle_broker_BrokerAddress_TagsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024broker/address.proto\022\rdoodle.broker\032\025b" +
      "roker/route_id.proto\"\260\001\n\rBrokerAddress\022." +
      "\n\010route_id\030\001 \001(\0132\034.doodle.broker.BrokerR" +
      "outeId\022\014\n\004flag\030\002 \001(\005\0224\n\004tags\030\003 \003(\0132&.doo" +
      "dle.broker.BrokerAddress.TagsEntry\032+\n\tTa" +
      "gsEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001B" +
      ")\n\021org.doodle.brokerB\022BrokerAddressProto" +
      "P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.doodle.broker.BrokerRouteIdProto.getDescriptor(),
        });
    internal_static_doodle_broker_BrokerAddress_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_doodle_broker_BrokerAddress_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_doodle_broker_BrokerAddress_descriptor,
        new java.lang.String[] { "RouteId", "Flag", "Tags", });
    internal_static_doodle_broker_BrokerAddress_TagsEntry_descriptor =
      internal_static_doodle_broker_BrokerAddress_descriptor.getNestedTypes().get(0);
    internal_static_doodle_broker_BrokerAddress_TagsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_doodle_broker_BrokerAddress_TagsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    org.doodle.broker.BrokerRouteIdProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
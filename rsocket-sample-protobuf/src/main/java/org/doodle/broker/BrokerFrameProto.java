// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: broker/frame.proto

package org.doodle.broker;

public final class BrokerFrameProto {
  private BrokerFrameProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_doodle_broker_BrokerFrame_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_doodle_broker_BrokerFrame_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022broker/frame.proto\022\rdoodle.broker\032\024bro" +
      "ker/address.proto\032\030broker/route_setup.pr" +
      "oto\032\027broker/route_join.proto\032\030broker/bro" +
      "ker_info.proto\"\347\001\n\013BrokerFrame\022/\n\007addres" +
      "s\030\001 \001(\0132\034.doodle.broker.BrokerAddressH\000\022" +
      "6\n\013route_setup\030\002 \001(\0132\037.doodle.broker.Bro" +
      "kerRouteSetupH\000\0224\n\nroute_join\030\003 \001(\0132\036.do" +
      "odle.broker.BrokerRouteJoinH\000\0220\n\013broker_" +
      "info\030\004 \001(\0132\031.doodle.broker.BrokerInfoH\000B" +
      "\007\n\005frameB\'\n\021org.doodle.brokerB\020BrokerFra" +
      "meProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.doodle.broker.BrokerAddressProto.getDescriptor(),
          org.doodle.broker.BrokerRouteSetupProto.getDescriptor(),
          org.doodle.broker.BrokerRouteJoinProto.getDescriptor(),
          org.doodle.broker.BrokerInfoProto.getDescriptor(),
        });
    internal_static_doodle_broker_BrokerFrame_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_doodle_broker_BrokerFrame_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_doodle_broker_BrokerFrame_descriptor,
        new java.lang.String[] { "Address", "RouteSetup", "RouteJoin", "BrokerInfo", "Frame", });
    org.doodle.broker.BrokerAddressProto.getDescriptor();
    org.doodle.broker.BrokerRouteSetupProto.getDescriptor();
    org.doodle.broker.BrokerRouteJoinProto.getDescriptor();
    org.doodle.broker.BrokerInfoProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

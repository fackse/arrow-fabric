// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ArrowFabric.proto

package org.example.ArrowFabric;

public interface ServerStatusOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.example.ArrowFabric.ServerStatus)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.org.example.ArrowFabric.Code code = 1;</code>
   * @return The enum numeric value on the wire for code.
   */
  int getCodeValue();
  /**
   * <code>.org.example.ArrowFabric.Code code = 1;</code>
   * @return The code.
   */
  org.example.ArrowFabric.Code getCode();

  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>.org.example.ArrowFabric.ServerInfo server_info = 3;</code>
   * @return Whether the serverInfo field is set.
   */
  boolean hasServerInfo();
  /**
   * <code>.org.example.ArrowFabric.ServerInfo server_info = 3;</code>
   * @return The serverInfo.
   */
  org.example.ArrowFabric.ServerInfo getServerInfo();
  /**
   * <code>.org.example.ArrowFabric.ServerInfo server_info = 3;</code>
   */
  org.example.ArrowFabric.ServerInfoOrBuilder getServerInfoOrBuilder();
}

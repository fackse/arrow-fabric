// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ArrowFabric.proto

package org.example.ArrowFabric;

/**
 * Protobuf type {@code org.example.ArrowFabric.IntVector}
 */
public final class IntVector extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.example.ArrowFabric.IntVector)
    IntVectorOrBuilder {
private static final long serialVersionUID = 0L;
  // Use IntVector.newBuilder() to construct.
  private IntVector(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private IntVector() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new IntVector();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private IntVector(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            org.example.ArrowFabric.Status.Builder subBuilder = null;
            if (status_ != null) {
              subBuilder = status_.toBuilder();
            }
            status_ = input.readMessage(org.example.ArrowFabric.Status.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(status_);
              status_ = subBuilder.buildPartial();
            }

            break;
          }
          case 16: {

            value_ = input.readInt64();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.example.ArrowFabric.ArrowFabric.internal_static_org_example_ArrowFabric_IntVector_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.example.ArrowFabric.ArrowFabric.internal_static_org_example_ArrowFabric_IntVector_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.example.ArrowFabric.IntVector.class, org.example.ArrowFabric.IntVector.Builder.class);
  }

  public static final int STATUS_FIELD_NUMBER = 1;
  private org.example.ArrowFabric.Status status_;
  /**
   * <code>.org.example.ArrowFabric.Status status = 1;</code>
   * @return Whether the status field is set.
   */
  @java.lang.Override
  public boolean hasStatus() {
    return status_ != null;
  }
  /**
   * <code>.org.example.ArrowFabric.Status status = 1;</code>
   * @return The status.
   */
  @java.lang.Override
  public org.example.ArrowFabric.Status getStatus() {
    return status_ == null ? org.example.ArrowFabric.Status.getDefaultInstance() : status_;
  }
  /**
   * <code>.org.example.ArrowFabric.Status status = 1;</code>
   */
  @java.lang.Override
  public org.example.ArrowFabric.StatusOrBuilder getStatusOrBuilder() {
    return getStatus();
  }

  public static final int VALUE_FIELD_NUMBER = 2;
  private long value_;
  /**
   * <code>int64 value = 2;</code>
   * @return The value.
   */
  @java.lang.Override
  public long getValue() {
    return value_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (status_ != null) {
      output.writeMessage(1, getStatus());
    }
    if (value_ != 0L) {
      output.writeInt64(2, value_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getStatus());
    }
    if (value_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, value_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.example.ArrowFabric.IntVector)) {
      return super.equals(obj);
    }
    org.example.ArrowFabric.IntVector other = (org.example.ArrowFabric.IntVector) obj;

    if (hasStatus() != other.hasStatus()) return false;
    if (hasStatus()) {
      if (!getStatus()
          .equals(other.getStatus())) return false;
    }
    if (getValue()
        != other.getValue()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasStatus()) {
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus().hashCode();
    }
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getValue());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.example.ArrowFabric.IntVector parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.example.ArrowFabric.IntVector parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.example.ArrowFabric.IntVector parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.example.ArrowFabric.IntVector parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.example.ArrowFabric.IntVector prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code org.example.ArrowFabric.IntVector}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.example.ArrowFabric.IntVector)
      org.example.ArrowFabric.IntVectorOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.example.ArrowFabric.ArrowFabric.internal_static_org_example_ArrowFabric_IntVector_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.example.ArrowFabric.ArrowFabric.internal_static_org_example_ArrowFabric_IntVector_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.example.ArrowFabric.IntVector.class, org.example.ArrowFabric.IntVector.Builder.class);
    }

    // Construct using org.example.ArrowFabric.IntVector.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (statusBuilder_ == null) {
        status_ = null;
      } else {
        status_ = null;
        statusBuilder_ = null;
      }
      value_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.example.ArrowFabric.ArrowFabric.internal_static_org_example_ArrowFabric_IntVector_descriptor;
    }

    @java.lang.Override
    public org.example.ArrowFabric.IntVector getDefaultInstanceForType() {
      return org.example.ArrowFabric.IntVector.getDefaultInstance();
    }

    @java.lang.Override
    public org.example.ArrowFabric.IntVector build() {
      org.example.ArrowFabric.IntVector result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.example.ArrowFabric.IntVector buildPartial() {
      org.example.ArrowFabric.IntVector result = new org.example.ArrowFabric.IntVector(this);
      if (statusBuilder_ == null) {
        result.status_ = status_;
      } else {
        result.status_ = statusBuilder_.build();
      }
      result.value_ = value_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.example.ArrowFabric.IntVector) {
        return mergeFrom((org.example.ArrowFabric.IntVector)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.example.ArrowFabric.IntVector other) {
      if (other == org.example.ArrowFabric.IntVector.getDefaultInstance()) return this;
      if (other.hasStatus()) {
        mergeStatus(other.getStatus());
      }
      if (other.getValue() != 0L) {
        setValue(other.getValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.example.ArrowFabric.IntVector parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.example.ArrowFabric.IntVector) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private org.example.ArrowFabric.Status status_;
    private com.google.protobuf.SingleFieldBuilderV3<
        org.example.ArrowFabric.Status, org.example.ArrowFabric.Status.Builder, org.example.ArrowFabric.StatusOrBuilder> statusBuilder_;
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     * @return Whether the status field is set.
     */
    public boolean hasStatus() {
      return statusBuilder_ != null || status_ != null;
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     * @return The status.
     */
    public org.example.ArrowFabric.Status getStatus() {
      if (statusBuilder_ == null) {
        return status_ == null ? org.example.ArrowFabric.Status.getDefaultInstance() : status_;
      } else {
        return statusBuilder_.getMessage();
      }
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public Builder setStatus(org.example.ArrowFabric.Status value) {
      if (statusBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        status_ = value;
        onChanged();
      } else {
        statusBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public Builder setStatus(
        org.example.ArrowFabric.Status.Builder builderForValue) {
      if (statusBuilder_ == null) {
        status_ = builderForValue.build();
        onChanged();
      } else {
        statusBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public Builder mergeStatus(org.example.ArrowFabric.Status value) {
      if (statusBuilder_ == null) {
        if (status_ != null) {
          status_ =
            org.example.ArrowFabric.Status.newBuilder(status_).mergeFrom(value).buildPartial();
        } else {
          status_ = value;
        }
        onChanged();
      } else {
        statusBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public Builder clearStatus() {
      if (statusBuilder_ == null) {
        status_ = null;
        onChanged();
      } else {
        status_ = null;
        statusBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public org.example.ArrowFabric.Status.Builder getStatusBuilder() {
      
      onChanged();
      return getStatusFieldBuilder().getBuilder();
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    public org.example.ArrowFabric.StatusOrBuilder getStatusOrBuilder() {
      if (statusBuilder_ != null) {
        return statusBuilder_.getMessageOrBuilder();
      } else {
        return status_ == null ?
            org.example.ArrowFabric.Status.getDefaultInstance() : status_;
      }
    }
    /**
     * <code>.org.example.ArrowFabric.Status status = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.example.ArrowFabric.Status, org.example.ArrowFabric.Status.Builder, org.example.ArrowFabric.StatusOrBuilder> 
        getStatusFieldBuilder() {
      if (statusBuilder_ == null) {
        statusBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.example.ArrowFabric.Status, org.example.ArrowFabric.Status.Builder, org.example.ArrowFabric.StatusOrBuilder>(
                getStatus(),
                getParentForChildren(),
                isClean());
        status_ = null;
      }
      return statusBuilder_;
    }

    private long value_ ;
    /**
     * <code>int64 value = 2;</code>
     * @return The value.
     */
    @java.lang.Override
    public long getValue() {
      return value_;
    }
    /**
     * <code>int64 value = 2;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(long value) {
      
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 value = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      
      value_ = 0L;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:org.example.ArrowFabric.IntVector)
  }

  // @@protoc_insertion_point(class_scope:org.example.ArrowFabric.IntVector)
  private static final org.example.ArrowFabric.IntVector DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.example.ArrowFabric.IntVector();
  }

  public static org.example.ArrowFabric.IntVector getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<IntVector>
      PARSER = new com.google.protobuf.AbstractParser<IntVector>() {
    @java.lang.Override
    public IntVector parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new IntVector(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<IntVector> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<IntVector> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.example.ArrowFabric.IntVector getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


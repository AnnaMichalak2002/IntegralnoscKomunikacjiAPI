package edu.zut.pbalab4.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RequestHeader
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-04T09:21:24.989664100+02:00[Europe/Warsaw]", comments = "Generator version: 7.5.0")
public class RequestHeader {

  private UUID requestId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime sendDate;

  public RequestHeader() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RequestHeader(UUID requestId, OffsetDateTime sendDate) {
    this.requestId = requestId;
    this.sendDate = sendDate;
  }

  public RequestHeader requestId(UUID requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   * @return requestId
  */
  @NotNull @Valid 
  @Schema(name = "requestId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requestId")
  public UUID getRequestId() {
    return requestId;
  }

  public void setRequestId(UUID requestId) {
    this.requestId = requestId;
  }

  public RequestHeader sendDate(OffsetDateTime sendDate) {
    this.sendDate = sendDate;
    return this;
  }

  /**
   * Date format according to ISO_8601 for example: yyyy-MM-dd'T'HH:mm:ss.SSSZ
   * @return sendDate
  */
  @NotNull @Valid 
  @Schema(name = "sendDate", description = "Date format according to ISO_8601 for example: yyyy-MM-dd'T'HH:mm:ss.SSSZ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sendDate")
  public OffsetDateTime getSendDate() {
    return sendDate;
  }

  public void setSendDate(OffsetDateTime sendDate) {
    this.sendDate = sendDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestHeader requestHeader = (RequestHeader) o;
    return Objects.equals(this.requestId, requestHeader.requestId) &&
        Objects.equals(this.sendDate, requestHeader.sendDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, sendDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestHeader {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    sendDate: ").append(toIndentedString(sendDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


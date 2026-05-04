package edu.zut.pbalab4.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import edu.zut.pbalab4.model.RequestHeader;
import edu.zut.pbalab4.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UserListResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-04T09:21:24.989664100+02:00[Europe/Warsaw]", comments = "Generator version: 7.5.0")
public class UserListResponse {

  private RequestHeader responseHeader;

  @Valid
  private List<@Valid User> usersList = new ArrayList<>();

  public UserListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserListResponse(RequestHeader responseHeader, List<@Valid User> usersList) {
    this.responseHeader = responseHeader;
    this.usersList = usersList;
  }

  public UserListResponse responseHeader(RequestHeader responseHeader) {
    this.responseHeader = responseHeader;
    return this;
  }

  /**
   * Get responseHeader
   * @return responseHeader
  */
  @NotNull @Valid 
  @Schema(name = "responseHeader", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("responseHeader")
  public RequestHeader getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(RequestHeader responseHeader) {
    this.responseHeader = responseHeader;
  }

  public UserListResponse usersList(List<@Valid User> usersList) {
    this.usersList = usersList;
    return this;
  }

  public UserListResponse addUsersListItem(User usersListItem) {
    if (this.usersList == null) {
      this.usersList = new ArrayList<>();
    }
    this.usersList.add(usersListItem);
    return this;
  }

  /**
   * Get usersList
   * @return usersList
  */
  @NotNull @Valid 
  @Schema(name = "usersList", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("usersList")
  public List<@Valid User> getUsersList() {
    return usersList;
  }

  public void setUsersList(List<@Valid User> usersList) {
    this.usersList = usersList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserListResponse userListResponse = (UserListResponse) o;
    return Objects.equals(this.responseHeader, userListResponse.responseHeader) &&
        Objects.equals(this.usersList, userListResponse.usersList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseHeader, usersList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserListResponse {\n");
    sb.append("    responseHeader: ").append(toIndentedString(responseHeader)).append("\n");
    sb.append("    usersList: ").append(toIndentedString(usersList)).append("\n");
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


package cn.edu.sju.ws.userservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("uid")
    private Long id;

    @JsonProperty("name")
    private String username;

    private String address;

    // 显式定义getter方法确保序列化正确
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', address='" + address + "'}";
    }
}
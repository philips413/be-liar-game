package psnl.liar.payload.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String name;

    private String cookie;

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "name='" + name + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}

package geon.hee.tobyspring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {

    private String id;

    private String name;

    private String password;

    private Level level;

    private int login;

    private int recommend;
}

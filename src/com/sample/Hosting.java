package com.sample;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Hosting {
    private int Id;
    private String name;
    private String url;

    public Hosting(int id, String name, String url) {
        Id = id;
        this.name = name;
        this.url = url;
    }
}

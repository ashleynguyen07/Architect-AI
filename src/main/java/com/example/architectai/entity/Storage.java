package com.example.architectai.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
public class Storage {

    private String path;
    private String fileName;
    private InputStream inputStream;
}

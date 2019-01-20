package com.marcin.dropbox.upload;

import com.dropbox.core.DbxException;

import java.io.IOException;

public interface Uploader {
    void upload(String path, String name) throws IOException, DbxException;
}

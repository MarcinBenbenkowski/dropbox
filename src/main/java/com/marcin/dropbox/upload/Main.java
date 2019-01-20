package com.marcin.dropbox.upload;

import com.dropbox.core.DbxException;
import com.marcin.dropbox.listener.DirectoryListener;

import java.io.IOException;

public class Main {
    private static final int PROPS_INDEX = 0;


    public static void main(String[] args) throws IOException, InterruptedException, DbxException {
        String propsPath = args[PROPS_INDEX];
        ConfigService cfg = new ConfigService(propsPath).load();
        DropBoxUpload uploader = new DropBoxUpload(cfg);
        DirectoryListener listener = new DirectoryListener(uploader, cfg);
        listener.listen();

    }
}

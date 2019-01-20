package com.marcin.dropbox.upload;


import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.marcin.dropbox.upload.Keys.DROPBOX_KEY;

public class DropBoxUpload implements Uploader {
    private final ConfigService cfg;

    public DropBoxUpload(ConfigService cfg){
        this.cfg = cfg;
    }

    @Override
    public void upload(String path, String name)  {
        String token = cfg.get(DROPBOX_KEY);
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, token);

        try (InputStream in = new FileInputStream(path)) {
            FileMetadata metadata = client.files().uploadBuilder("/" + name)
                    .uploadAndFinish(in);
        }catch (IOException | DbxException e){
            throw new UploadException("Can not upload file" + path, e);
        }
    }
}


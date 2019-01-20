package com.marcin.dropbox.listener;

import com.dropbox.core.DbxException;
import com.marcin.dropbox.upload.ConfigService;
import com.marcin.dropbox.upload.DropBoxUpload;

import java.io.IOException;
import java.nio.file.*;

import static com.marcin.dropbox.upload.Keys.DIRECTORY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class DirectoryListener {

    private DropBoxUpload uploader;
    private String dir;


    public DirectoryListener(DropBoxUpload uploader, ConfigService cfg) throws IOException, DbxException, InterruptedException {
        this.uploader = uploader;
        this.dir = cfg.get(DIRECTORY);

    }


    public void listen() throws IOException, InterruptedException, DbxException {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(dir);
            path.register(watchService, ENTRY_CREATE);
            WatchKey key;

            while ((key = watchService.take()) != null) {
                String name = key.pollEvents().get(0).context().toString();
                System.out.println(name);
//            while (!name.renameTo(name)){
//                Thread.sleep(10);

                uploader.upload(dir + name, name);

                key.reset();
            }
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }
}



package com.instructure.canvasapi.api;

import android.content.Context;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.FileFolder;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class FileFolderAPI {

    public static String getFirstPageFoldersCacheFilename(long folderID) {
        return "/folders/" + folderID + "/folders";
    }

    public static String getFirstPageFilesCacheFilename(long folderID) {
        return "/folders/" + folderID + "/files";
    }

    interface FilesFoldersInterface {
        @GET("/{context_id}/folders/root")
        void getRootFolderForContext(@Path("context_id") long context_id, Callback<FileFolder> callback);

        @GET("/self/folders/root")
        void getRootUserFolder(Callback<FileFolder> callback);

        @GET("/folders/{folderid}/folders")
        void getFirstPageFolders(@Path("folderid") long folder_id, Callback<FileFolder[]> callback);

        @GET("/folders/{folderid}/files")
        void getFirstPageFiles(@Path("folderid") long folder_id, Callback<FileFolder[]> callback);

        @GET("/{fileurl}")
        void getFileFolderFromURL(@EncodedPath("fileurl") String fileURL, Callback<FileFolder> callback);

        @GET("/{next}")
        void getNextPageFileFoldersList(@EncodedPath("next") String nextURL, Callback<FileFolder[]> callback);

        @DELETE("/files/{fileid}")
        void deleteFile(@Path("fileid")long fileId, Callback<Response> callback);
    }

    private static FilesFoldersInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        return buildInterface(callback.getContext(), canvasContext);
    }

    private static FilesFoldersInterface buildInterface(Context context, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, canvasContext);
        return restAdapter.create(FilesFoldersInterface.class);
    }

    public static void getFirstPageFoldersRoot(CanvasContext canvasContext, final CanvasCallback<FileFolder[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) {
            return;
        }

        //Build a callback bridge.
        Callback<FileFolder> bridgeCallback = new Callback<FileFolder>() {
            @Override
            public void success(FileFolder fileFolder, Response response) {
                callback.readFromCache(getFirstPageFoldersCacheFilename(fileFolder.getId()));
                buildInterface(callback, null).getFirstPageFolders(fileFolder.getId(), callback);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callback.failure(retrofitError);
            }
        };

        getRootFolder(canvasContext, callback.getContext(), bridgeCallback);
    }

    public static void getFirstPageFilesRoot(CanvasContext canvasContext, final CanvasCallback<FileFolder[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) {
            return;
        }

        //Build a callback bridge.
        Callback<FileFolder> bridgeCallback = new Callback<FileFolder>() {
            @Override
            public void success(FileFolder fileFolder, Response response) {
                callback.readFromCache(getFirstPageFilesCacheFilename(fileFolder.getId()));
                buildInterface(callback, null).getFirstPageFiles(fileFolder.getId(), callback);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callback.failure(retrofitError);
            }
        };

        getRootFolder(canvasContext, callback.getContext(), bridgeCallback);
    }

    private static void getRootFolder(CanvasContext canvasContext, Context context, Callback<FileFolder> bridgeCallback) {
        FilesFoldersInterface foldersInterface = buildInterface(context, canvasContext);

        if (canvasContext.getType() == CanvasContext.Type.USER) {
            foldersInterface.getRootUserFolder(bridgeCallback);
        } else {
            foldersInterface.getRootFolderForContext(canvasContext.getId(), bridgeCallback);
        }
    }

    public static void getFirstPageFolders(long folderid, CanvasCallback<FileFolder[]> callback) {
        if (APIHelpers.paramIsNull(callback) || folderid <= 0) {
            return;
        }

        callback.readFromCache(getFirstPageFoldersCacheFilename(folderid));
        buildInterface(callback, null).getFirstPageFolders(folderid, callback);
    }

    public static void getFirstPageFiles(long folderid, CanvasCallback<FileFolder[]> callback) {
        if (APIHelpers.paramIsNull(callback) || folderid <= 0) {
            return;
        }

        callback.readFromCache(getFirstPageFilesCacheFilename(folderid));
        buildInterface(callback, null).getFirstPageFiles(folderid, callback);
    }


    public static void getNextPageFileFolders(String nextURL, CanvasCallback<FileFolder[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextURL)) {
            return;
        }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageFileFoldersList(nextURL, callback);
    }

    public static void getFileFolderFromURL(String url, CanvasCallback<FileFolder> callback) {
        if (APIHelpers.paramIsNull(callback, url)) {
            return;
        }

        buildInterface(callback, null).getFileFolderFromURL(url, callback);
    }

    public static void deleteFile(long fileId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) {
            return;
        }

        buildInterface(callback, null).deleteFile(fileId,callback);
    }
}

package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Bookmark;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public class BookmarkAPI {

    public static String getBookmarksCacheFilename(){
        return "/users/self/bookmarks";
    }

    interface BookmarkInterface {

        @GET("/users/self/bookmarks")
        void getBookmarks(Callback<Bookmark[]> callback);

        @GET("/users/self/bookmarks/{id}")
        void getBookmark(@Path("id") long bookmarkId, Callback<Bookmark> callback);

        @POST("/users/self/bookmarks")
        void createBookmark(
                @Query("name") String name,
                @Query(value = "url", encodeValue = true) String url,
                @Query("position") int position,
                Callback<Bookmark> callback);

        @PUT("/users/self/bookmarks/{id}")
        void updateBookmark(@Path("id") long bookmarkId,
                            @Query("name") String name,
                            @Query(value = "url", encodeValue = false) String url,
                            @Query("position") int position,
                            Callback<Bookmark> callback);

        @DELETE("/users/self/bookmarks/{id}")
        void deleteBookmark(@Path("id") long bookmarkId, Callback<Bookmark> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static BookmarkInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, false);
        return restAdapter.create(BookmarkInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getBookmarks(CanvasCallback<Bookmark[]> callback) {
        callback.readFromCache(getBookmarksCacheFilename());
        buildInterface(callback).getBookmarks(callback);
    }

    public static void getBookmark(long bookmarkId, CanvasCallback<Bookmark> callback) {
        callback.readFromCache(getBookmarksCacheFilename());
        buildInterface(callback).getBookmark(bookmarkId, callback);
    }

    public static void createBookmark(Bookmark bookmark, CanvasCallback<Bookmark> callback) {
        buildInterface(callback).createBookmark(bookmark.getName(), bookmark.getUrl(), bookmark.getPosition(), callback);
    }

    public static void deleteBookmark(Bookmark bookmark,  CanvasCallback<Bookmark> callback) {
        deleteBookmark(bookmark.getId(), callback);
    }

    public static void deleteBookmark(long bookmarkId, CanvasCallback<Bookmark> callback) {
        buildInterface(callback).deleteBookmark(bookmarkId, callback);
    }

    public static void update(Bookmark bookmark, CanvasCallback<Bookmark> callback) {
        buildInterface(callback).updateBookmark(bookmark.getId(), bookmark.getName(), bookmark.getUrl(), bookmark.getPosition(), callback);
    }
}

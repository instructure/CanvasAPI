## CanvasKit by Instructure Android

CanvasKit is a library that will help you integrate your own third party app with [Canvas by Instructure](https://instructure.com/).

CanvasKit is built on the [Canvas API](https://canvas.instructure.com/doc/api/index.html). CanvasKit is designed to allow for great flexibility while providing an easy to use interface. You can use CanvasKit to build apps for open source versions of Canvas as well as instances hosted by Instructure.

## How To Get Started

- [Download CanvasKit](https://github.com/instructure/linkhere) 
- Become familiar with [Retrofit by Square](http://square.github.io/retrofit/)
- Take a look at the [Canvas API](https://canvas.instructure.com/doc/api/index.html) for a complete list of endpoints

##Usage
### Setup

In order to use CanvasKit with Instructure managed instances of [Canvas LMS](https://github.com/instructure/canvas-lms) you must obtain a Client ID and Shared Secret. CanvasKit uses [OAuth 2](https://canvas.instructure.com/doc/api/file.oauth.html) for authentication. Request your Client ID and Shared Secret by sending an email to <mike@instructure.com>. Make sure to give us your name, email, and what you are hoping to do with the CanvasKit.

###Initializing CanvasKit
Now that you have your Client ID and Shared Secret you can start using CanvasKit. You then can begin the OAuth native flow outlined [here](https://canvas.instructure.com/doc/api/file.oauth.html). Once you've received the OAuth Request String, you can begin by calling `OAuth.getToken(String clientId, String clientSecret, String oAuthRequest, CanvasCallback<OAuthToken> callback))`.
This will return the OAuth token for the user. You then need to initialization the CanvasRestAdapter by calling `CanvasRestAdapter.setupInstance(Context context, String token, String domain)`

###Using the API
CanvasKit is mainly structured around the notion of CanvasContexts. We currently support courses, groups, and users as CanvasContexts. For the most part, when you make an API call, you'll provide the API with a CanvasContext object. `Course.java` and `Group.java` both extend from `CanvasContext.java` so those can be used in these cases. You can also call `CanvasContext.getGenericContext(Type type, long id, String name)` to generate one on the fly.

###Tips and Tricks
* `APIHelpers.java` provides a lot of useful methods for CanvasKit. These include getters/setters for the current OAuth Token, user-agent, protocol, domain, signed-in user, etc.

* `CanvasCallback.java` is our extension of [Retrofit's Callback class](https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit/Callback.java). It provides automatic caching and pagination support. 

* The cache callback occurs on whichever thread you launched the API call from.


###Error Handling
First and foremost, all logging that our library does uses the tag _canvas-kit_, which can be found in `APIHelpers.java` 

We also provided an interface entitled `ErrorDelegate` that gives you callbacks when an error occurs during an API call. We specify which type of error occurred, so you can handle each of the cases however it makes the most sense for your project. You can set up a _default_ ErrorDelegate inside of `CanvasCallback.java` which will be used whenever one isn't provided as a constructor argument when creating a new CanvasCallback.

You can also handle errors manually by overriding `public boolean onFailure(RetrofitError RetrofitError)`. This callback allows you to handle errors in API calls before the error delegate methods are called. Returning true from this method means you have successfully handled the error and the error delegate will NOT be called; return false otherwise.

##Example
    
    //Set up callback and errorDelegate
    //Prerequisite to making the API calls.
    public void setUpCallbackAndErrorDelegate(){
        //Create the error delegate. This will override the defaultDelegate if one has been set.
        ErrorDelegate errorDelegate = new ErrorDelegate() {
            @Override
            public void noNetworkError(RetrofitError error, Context context) {
                //No network available.
            }

            @Override
            public void notAuthorizedError(RetrofitError error, Context context) {
                //HTTP 401 error.
            }

            @Override
            public void invalidUrlError(RetrofitError error, Context context) {
                //HTTP 404 error.
            }

            @Override
            public void serverError(RetrofitError error, Context context) {
                //HTTP 500 error
            }

            @Override
            public void generalError(RetrofitError error, Context context) {
                //Every other type of error.
            }
        };
        
        //Create the Canvas Callback.
        CanvasCallback<Conversation[]> conversationCanvasCallback = new CanvasCallback<Conversation[]>(this,errorDelegate) {
            @Override
            public void cache(Conversation[] conversations) {
                //The items from cache come here.
            }

            @Override
            public void firstPage(Conversation[] conversations, LinkHeaders linkHeaders, Response response) {
                //The first page of items will come here.
                nextURL = linkHeaders.nextURL;
            }

            @Override
            public void nextPage(Conversation[] conversations, LinkHeaders linkHeaders, Response response){
                //All Subsequent pages will come here.
            }

        };
    }

    //Actually fetch data from the API.
    public void getFirstPageFromAPI(){
        //Notice we can use the same Callback for both API calls.   
        Conversations.getFirstPageConversations(conversationCanvasCallback, Conversations.ConversationScope.ALL, this);
    }

    public void getNextPagesFromAPI(){
        //Notice we can use the same Callback for both API calls.   
        Conversations.getNextPageConversations(conversationCanvasCallback,nextURL);
    }

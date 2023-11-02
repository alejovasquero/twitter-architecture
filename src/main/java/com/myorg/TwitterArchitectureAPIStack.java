package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.IResource;
import software.amazon.awscdk.services.apigateway.Method;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.constructs.Construct;

public class TwitterArchitectureAPIStack extends Stack {

    public TwitterArchitectureAPIStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TwitterArchitectureAPIStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        // example resource
        // final Queue queue = Queue.Builder.create(this, "TwitterArchitectureQueue")
        // .visibilityTimeout(Duration.seconds(300))
        // .build();

        RestApi api = RestApi.Builder.create(this, "TwitterRestAPI")
                .restApiName("TwitterRestAPI")
                .description("The twitter rest API :)")
                .build();

        IResource resource = api.getRoot();
        IResource apiResource = resource.addResource("api");
        IResource v1Resource = apiResource.addResource("v1");

        this.createUserResource(v1Resource);
        this.createTweetsResource(v1Resource);
    }

    private void createUserResource(IResource resource) {
        IResource usersResource = resource.addResource("users");
        IResource idResource = usersResource.addResource("{id}");

        IResource getTweets = idResource.addResource("tweets");
        getTweets.addMethod("GET");
        IResource addFollower = idResource.addResource("followers");
        addFollower.addMethod("POST");
    }

    private void createTweetsResource(IResource resource) {
        IResource tweetsResource = resource.addResource("tweets");
        Method createTweet = tweetsResource.addMethod("POST");

        IResource tweetId = tweetsResource.addResource("{id}");

        IResource comment = tweetId.addResource("comments");
        comment.addMethod("POST");
        IResource likes = tweetId.addResource("likes");
        likes.addMethod("POST");
    }

}

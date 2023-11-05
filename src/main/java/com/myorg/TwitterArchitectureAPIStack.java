package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.IResource;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.Method;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.iam.IRole;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.IBucket;
import software.constructs.Construct;

public class TwitterArchitectureAPIStack extends Stack {

    private Construct scope;
    private static final String JAR = "twitter-architecture.jar";
    private IRole existingRole = Role.fromRoleArn(this, "MyExistingRole", "arn:aws:iam::866956573632:role/LabRole");
    private IBucket codeBucket = Bucket.fromBucketArn(this, "ExistingBucket", "arn:aws:s3:::codebuckettwitter");

    public TwitterArchitectureAPIStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TwitterArchitectureAPIStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        this.scope = scope;
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
        getTweets.addMethod("GET", new LambdaIntegration(
                createNewLambda(this.scope, "GetTweetLambda", JAR, "com.myorg.handlers.ObtainTweetsHandler::handler")));
        IResource addFollower = idResource.addResource("followers");
        addFollower.addMethod("POST",  new LambdaIntegration(
                createNewLambda(this.scope, "AddFollower", JAR, "com.myorg.handlers.AddFollowerHandler::handler")));
    }

    private void createTweetsResource(IResource resource) {
        IResource tweetsResource = resource.addResource("tweets");
        Method createTweet = tweetsResource.addMethod("POST", new LambdaIntegration(
                createNewLambda(this.scope, "CreateWeet", JAR, "com.myorg.handlers.CreateTweet::handler")));

        IResource tweetId = tweetsResource.addResource("{id}");

        IResource comment = tweetId.addResource("comments");
        comment.addMethod("POST", new LambdaIntegration(
                createNewLambda(this.scope, "AddComment", JAR, "com.myorg.handlers.AddTweetComment::handler")));
        IResource likes = tweetId.addResource("likes");
        likes.addMethod("POST", new LambdaIntegration(
                createNewLambda(this.scope, "LikeTweet", JAR, "com.myorg.handlers.AddLike::handler")));
    }

    public Function createNewLambda(final Construct scope, final String name, final String asset,
            final String handler) {
        
        Function lambda = Function.Builder.create(this, name)
                .runtime(Runtime.JAVA_17)
                .code(Code.fromBucket(codeBucket, asset))
                .handler(handler)
                .role(existingRole)
                .functionName(name)
                .build();

        return lambda;
    }

}

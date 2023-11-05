package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class TwitterArchitectureApp {
        public static void main(final String[] args) {
                App app = new App();

                new TwitterArchitectureStack(app, "TwitterArchitectureStack", StackProps
                                .builder()
                                .env(Environment.builder()
                                                .account("866956573632")
                                                .region("us-east-1")
                                                .build())
                                .build());
                new TwitterArchitectureAPIStack(app, "TwitterArchitectureAPIStack", StackProps
                                .builder()
                                .env(Environment.builder()
                                                .account("866956573632")
                                                .region("us-east-1")
                                                .build())
                                .build());

                app.synth();
        }
}

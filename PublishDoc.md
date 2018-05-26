This doc is used to clarify how to publish Lego library to jCenter.

### What to do before publish
1. change versionName and versionCode in base `build.gradle` -> `ext.publish.legoVersion(Code)`;
2. execute `./gradlew install; ./gradlew bintrayUpload`

### Some explain
First we have three library to publish: 2 java libraries(jar) + 1 android library(aar). And I write two gradle scripts for publish jar&aar separating library.

##### Use jarPublish.gradle to publish `lego-annotation` and `lego-processor` library
Let's use `lego-annotation` as example. Write two lines in `lego-annotation/build.gradle` as below:
```
ext.jarPublishName='lego-annotation';
apply from: '../jarPublish.gradle'
``` 

If you need to update library version, go to base build.gradle and change `ext.publish.legoVersion` field.

That's all.

##### Use aarPublish.gradle to publish `lego-library`
Put this line in `lego-library/build.gradle` as below:
```
apply from: '../aarPublish.gradle'
```

##### Publish command
`./gradlew install;` this will generate jar/aar file in ~/.m2/repo...
`./gradlew bintrayUpload` this will upload library to bintray


##### Refer to
https://bintray.com/wingjay
https://www.jianshu.com/p/1502674152bd





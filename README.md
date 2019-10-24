# shiori-android

![](https://user-images.githubusercontent.com/1221346/67259163-6f0c5e80-f4cf-11e9-998e-ad60562d0e52.png)

"shiori" is a simple bookmark android app.

One of the outputs of the [bouzuya/w010][] project.

## How to build

### debug build

```
$ ./gradlew assembleDebug
```

### release build

```
$ # Set environment variables (See `_env`)
$ echo $ENCODED_KEY_STORE | base64 --decode > shiori.jks
$ ./gradlew -PpropertyKeyPassword=$KEY_PASSWORD -PpropertyStorePassword=$STORE_PASSWORD assembleRelease
```

## Upload the apk to DeployGate

```
$ # Set environment variable `DEPLOYGATE_API_TOKEN` (See `_env`)
$ # build (See "How to build" > "release build" section)
$ ./gradlew uploadDeployGateRelease
```

## Note

- Kotlin
- Single-Activity Application
- Data Binding
- Binding Adapter
- ViewModel
- LiveData
- Repository
- Room
- Room Database Migration

[bouzuya/w010]: https://github.com/bouzuya/w010

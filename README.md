# Minion for Android
[![Build Status](https://travis-ci.org/solkin/minion-android.svg?branch=master)](https://travis-ci.org/solkin/minion-android)

Minion is a handy group-key-value data storage library, powered by INI format. Let you parse and store INI format. Designed with modern fluent interface.

![Minion icon](/minion_icon.png)

### Create anync Minion for file
```java
FileStorage storage = FileStorage.create(file);
Minion minion = Minion.lets()
        .load(storage)
        .and()
        .store(storage)
        .async(new ResultCallback() {

            void onReady(Minion minion) {
            	// Data successfully loaded.
            }

            void onFailure(Exception ex) {
            	// Something went wrond.
            }
        });
```

### Save
Save data to specified storage.

```java
minion.store();
```

Also, if you created Minion `async`, you may listen for operation result with callback.

```java
minion.store(new ResultCallback() {

    void onReady(Minion minion) {
    	// Data successfully saved.
    }

    void onFailure(Exception ex) {
    	// Something went wrond.
    }
});
```

### Parse INI from string
Parses INI from specified string synchronously.

```java
Readable storage = StringStorage.create("[group]\nkey=value\nkey2=value2");
Minion minion = Minion.lets()
        .load(storage)
        .sync();
```

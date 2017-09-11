# Minion for Android [![Build Status](https://travis-ci.org/solkin/minion-android.svg?branch=master)](https://travis-ci.org/solkin/minion-android)
Minion is an INI parser and builder library

Example:
```
Readable storage = StringStorage.create("[group]\nkey=value\nkey2=value2");
Minion minion = Minion.lets()
        .load(storage)
        .sync();
```

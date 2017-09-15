# Minion for Android
[![Build Status](https://travis-ci.org/solkin/minion-android.svg?branch=master)](https://travis-ci.org/solkin/minion-android) [![Download](https://api.bintray.com/packages/solkin/minion/minion-android/images/download.svg) ](https://bintray.com/solkin/minion/minion-android/_latestVersion) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/74c7e0e1018b470eb11b01600e570474)](https://www.codacy.com/app/solkin/minion-android?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=solkin/minion-android&amp;utm_campaign=Badge_Grade)

Minion is a handy group-key-value data storage library, powered by INI format. Let you parse and store INI format. Designed with modern fluent interface.

![Minion icon](/minion_icon.png)

### Add dependency
```groovy
compile 'com.tomclaw.minion:minion:1.0.1'
```

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

### Set values for specified group and key
If no such group or key exist, they will be created.

```java
minion.setValue("user", "name", "Michael");
```

It's also extremely easy to set an array of values.

```java
minion.setValue("music", "genres", "Classical", "Lounge", "Dance", "Pop");
```

### Get values for specified group and key
If no such group exist, it will be created.

```java
String name = minion.getValue("user", "name");
```

Or you can obtain array of values.

```java
String[] genres = genresminion.getValues("music", "genres");
```

### Get all groups
Returns all group names.

```java
Set<String> names = minion.getGroupNames();
```

Returns all groups with records.

```java
List<IniGroup> groups = minion.getGroups();
```

### Remove record
Of course, you may just simply remove key and value. Function `removeRecord` will return removed record. If it doesn't exist, method will return `null`.

```java
IniRecord record = minion.removeRecord("user", "name");
```

### Remove group
Minion allows to remove whole group of records too. Function `removeGroup` will return removed group with removed records. If it doesn't exist, method will return `null`.

```java
IniGroup group = minion.removeGroup("music");
```

### How it works
Minion based on stupidly simple plain-text INI format, it parses, compiles and deliver access blazingly fast. 
Library is extra-lightweight (~25Kb) and contain no any dependencies.
You can use Minion to store some prefs or form-specific data. Simply than ever.


### License
    Copyright 2017 Igor Solkin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

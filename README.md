# AndroidArcMenu

An easy-to-use arc Menu library for Android.

![Demo][demo_gif]

# Sample

[![Get it on Google Play][googleplay_store_badge]][demo_app]

or

[demo.apk](demo_apk)

# Including in Your Project

```groovy
repositories {
    jcenter()
}
```

```groovy
compile 'com.hackplan.androidarcmenu:library:1.1.0'
```

# Usage
## Basic
```java
arcMenu = new ArcMenu.Builder(activity)
        .setId(ARC_MENU_ID_1)
        .addBtn(R.drawable.a, id1)
        .addBtn(R.drawable.b, id2)
        .setListener(listener)
        .showOnTouch(btn2)
        .hideOnTouchUp(false)
        .build();
```

## More things

![Desc][png_1]

```java
arcMenu = new ArcMenu.Builder(activity)
        .addBtns(new ArcButton.Builder(menuBtn, 2),
                new ArcButton.Builder(new SimpleCirView(this)
                        .setText("2")
                        .setCirColor(Color.parseColor("#03A9F4"))
                        .setTextColor(Color.WHITE)
                        .setTextSizeInSp(22)
                        .setBackgroundRadiusInPx(22),
                        3))
        .setListener(MainActivity.this)
        .showOnLongClick(btn1)
        .setDegree(160)
        .setRadius(222)
        .build();
        
arcMenu.showOn(view); //Show Manually
```

# LICENSE

```
Copyright (C) 2016 Ding Wenhao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo_gif]: https://github.com/HackPlan/AndroidArcMenu/raw/master/art/demo.gif
[png_1]: https://github.com/HackPlan/AndroidArcMenu/raw/master/art/1.png
[demo_app]: https://play.google.com/store/apps/details?id=com.hackplan.androidarcmenu.demo
[googleplay_store_badge]: http://www.android.com/images/brand/get_it_on_play_logo_large.png
[demo_apk]:https://github.com/HackPlan/AndroidArcMenu/raw/master/library/demo.apk

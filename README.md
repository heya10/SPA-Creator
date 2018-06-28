# SPA-Creator
Simple Single Page Application Creator

It should simplify a little bit creating and managing SPA.

Its using:
Java | Spring Boot | MongoDB | Bootstrap | some more

Right now you can define basic info (title, description, icon) and you can use editor to create our web page (some parameters need specific values, there is no documentation yet so it might be hard to change some value). Backend is not best looking but my main concern was to get working app.

Important:

In this application you can change icon which is displayed in Web Browser, for that I defined path where such icons will be saved
You can change path in "MyController" class, line 33, variable called "ICON_SAVE_PATH"
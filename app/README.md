# Chris' Cat App

Hi Kraken Tech! This is my attempt at the Cat App challenge that I was assigned. It has been made in Jetpack Compose (As per the brief), and makes use of the following libraries:

- Retrofit & OkHTTP3 - for API Calls
- Gson, for taking JSON responses and parsing them into a class
- Coil - For image handling

The app will firstly retrieve a response from the cat API, via Retrofit (Asynchronously enqueued). Once this has been retrieved, the data is transferred into a `MutableLiveData`  variable of a `Cat` class, which has been made with Gson. The class is then populated with the information from the API response. This is then returned back from the function. This `Cat` class is then monitored with the `observeAsState()` function. Data is then simply retrieved through the `Cat` class when needed throughout the app.

Coil is used to load images of cats, as it allows for stateful loading of the images, and also includes an animation before they appear.

Overall, I absolutely loved this challenge! I have been through a few job hiring processes by now and the one thing I always dread is the programming test as it usually has nothing to do with the company. Being given an in-house challenge that directly challenges me in the technologies that you use yourselves at Kraken is refreshing, and something I really enjoyed!

Learning Jetpack Compose with no prior experience whilst also trying to make sure I submitted the app in time was a bit of a trial-by-fire, but I found this to be an amazing opportunity to tinker with it and hone my Android development skills in general. You may find that some things may not be completely perfect, but I hope I still showed you some of my programming ability, specifically in Kotlin as I do have prior experience to this.

Finally, I want to say thank you so much for giving me the chance to work at Octopus. I have absolutely fallen in love with Android Development since learning it at university, and having the opportunity to work with yourselves on such a modern app that will be used long into the future would be amazing.

Thank you for taking the time to look at my app.

Chris :)

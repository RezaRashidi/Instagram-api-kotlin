# Instagram API Kotlin

A Kotlin library for interacting with Instagram's private API. This library provides a clean interface to programmatically interact with Instagram's features that are otherwise only available through the official app.

## Features

- **Authentication**: Login, logout, and session management
- **Media Operations**: Upload, download, edit and delete media
- **Direct Messaging**: Send and receive direct messages
- **Feed Interactions**: Like, comment, and fetch various feeds (timeline, user, hashtag)
- **Story Features**: Upload and interact with stories
- **Account Management**: Edit profile, change password
- **Search Functionality**: Search users, hashtags, and locations
- **Real-time Updates**: Push notifications and real-time messaging
- **Business Features**: Access to business account features and insights

## Installation

Add the following to your `build.gradle`:

```gradle
repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.1"
    implementation 'com.github.kittinunf.fuel:fuel:2.1.0'
    implementation 'com.github.kittinunf.fuel:fuel-kotlinx-serialization:2.1.0'
}
```

## Basic Usage

```kotlin
import instagramAPI.Instagram

fun main() {
    val instagram = Instagram()
    
    // Login to Instagram
    instagram.login("username", "password")
    
    // Get user info
    val userInfo = instagram.account.getCurrentUser()
    
    // Upload photo
    instagram.timeline.uploadPhoto("path/to/photo.jpg", "Photo caption")
    
    // Send direct message
    instagram.direct.sendText(userId, "Hello!")
    
    // Logout
    instagram.logout()
}
```

## Features in Detail

### Media Handling
- Upload photos and videos
- Edit media captions
- Delete media
- Like/unlike media
- Comment on media
- Fetch media by user, hashtag, or location

### Direct Messaging
- Send text messages
- Send media messages
- Manage conversations
- Real-time updates

### Account Management
- Edit profile information
- Change password
- Manage two-factor authentication
- Handle security challenges

### Story Features
- Upload story media
- Fetch user stories
- React to stories
- Create story highlights

### Business Features
- Access insights
- Promote posts
- Manage business settings

## Error Handling

The library includes comprehensive error handling for various Instagram API responses:

```kotlin
try {
    instagram.login("username", "password")
} catch (e: IncorrectPasswordException) {
    println("Incorrect password")
} catch (e: ChallengeRequiredException) {
    println("Need to complete challenge")
} catch (e: InstagramException) {
    println("Generic error: ${e.message}")
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Disclaimer

This library is not affiliated with, authorized, maintained, sponsored or endorsed by Instagram or any of its affiliates or subsidiaries. This is an independent and unofficial API. Use at your own risk.

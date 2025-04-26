# Online Voting System

A secure and user-friendly Android application for conducting electronic voting with multi-factor authentication.

## Project Overview

The Online Voting System is a modern Android application that enables secure electronic voting with multiple layers of security, including biometric authentication and OTP verification. The system supports both voter and administrator interfaces, making it suitable for various voting scenarios from institutional elections to large-scale voting events.

### Key Features

- 🔐 Multi-factor Authentication
  - Biometric verification
  - OTP validation
  - Traditional login
- 👥 Dual Interface
  - Voter portal
  - Admin dashboard
- 📊 Real-time Results
- 🔒 Secure Vote Storage
- 📱 Modern Material Design UI
- ✅ Vote Confirmation System

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Client (Android App)                     │
├────────────┬────────────────────┬────────────┬─────────────┤
│  UI Layer  │  Business Layer    │ Data Layer │   Services  │
├────────────┴────────────────────┴────────────┴─────────────┤
│                                                             │
│ ┌──────────┐    ┌──────────┐    ┌──────────┐  ┌─────────┐ │
│ │Activities│←→│ViewModels │←→│Repository│←→│Firebase │ │
│ └──────────┘    └──────────┘    └──────────┘  └─────────┘ │
│                                                             │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Firebase Backend                         │
├───────────────┬─────────────────────┬─────────────────────┤
│ Authentication│    Firestore DB     │   Analytics         │
└───────────────┴─────────────────────┴─────────────────────┘
```

## Setup Instructions

### Prerequisites

1. Android Studio Arctic Fox or later
2. JDK 8 or higher
3. Android device/emulator (minimum API 21 - Android 5.0)
4. Firebase account
5. Git

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/OnlineVotingSystem.git
   cd OnlineVotingSystem
   ```

2. **Firebase Setup**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project
   - Add an Android app with package name: `com.college.onlinevotingsystem`
   - Download `google-services.json` and place it in the `app` directory
   - Enable Authentication (Email/Password and Biometric)
   - Set up Cloud Firestore

3. **Project Configuration**
   - Open the project in Android Studio
   - Sync project with Gradle files
   - Update `local.properties` with your SDK path
   - Ensure all dependencies are downloaded

4. **Build and Run**
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

### Running the App

1. Connect an Android device or start an emulator
2. Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## Development Setup

### Gradle Configuration
The project uses the following major dependencies:
- compileSdk: 33
- minSdk: 21
- targetSdk: 33
- Gradle version: 7.5
- Android Gradle Plugin: 7.4.2

### Key Dependencies
```gradle
// Firebase
implementation platform('com.google.firebase:firebase-bom:32.2.0')
implementation 'com.google.firebase:firebase-analytics'
implementation 'com.google.firebase:firebase-auth'
implementation 'com.google.firebase:firebase-firestore'

// UI Components
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.biometric:biometric:1.2.0-alpha05'
```

## Production Release Checklist

1. **Firebase Configuration**
   - Verify all Firebase services are properly configured
   - Test authentication flows
   - Set up proper security rules

2. **App Signing**
   - Create a release keystore
   - Configure signing in `build.gradle`
   - Update ProGuard rules if needed

3. **Testing**
   - Run unit tests
   - Perform UI testing
   - Test on multiple Android versions
   - Verify all authentication methods

## Security Features

- Biometric authentication
- OTP verification
- Firebase Authentication
- Secure data storage
- ProGuard enabled for release builds
- Network security configuration

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Contact

Your Name - your.email@example.com
Project Link: [https://github.com/yourusername/OnlineVotingSystem](https://github.com/yourusername/OnlineVotingSystem) 

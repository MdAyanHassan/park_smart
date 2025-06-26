# 🚗 Park Smart

**Smart Parking Solutions | Find, Navigate, Pay**

![Frontend](https://img.shields.io/badge/Android-Jetpack%20Compose-blue)
![Backend](https://img.shields.io/badge/Backend-Firebase-FFCA28)
![API](https://img.shields.io/badge/API-Google%20Maps-4285F4)
![Payment](https://img.shields.io/badge/Payment-Stripe-008CDD)

A mobile app that helps users find the nearest parking spots, navigate to them, and pay seamlessly via Stripe. Built with Jetpack Compose (MVVM) and powered by Firebase (Functions, Firestore, Auth).

## ✨ Key Features

- 📍 **Real-time Parking Spot Detection**: Uses Google Maps API to find nearby parking
- 🧭 **Turn-by-Turn Navigation**: Directs users to selected parking spots
- 💳 **In-App Payments**: Secure Stripe integration for hassle-free payments
- 🔐 **Preconfigured Firebase**: No manual setup needed—just clone and run!

## 🛠️ Tech Stack

- **Frontend**: Jetpack Compose (Kotlin), MVVM Architecture
- **Backend**: Firebase (Functions, Firestore, Authentication)
- **APIs**: Google Maps SDK (Places API), Stripe Payment
- **Tools**: Android Studio, Gradle

## ⚙️ Installation

### Prerequisites

**Google Places API Key:**
1. Obtain it from [Google Cloud Console](https://console.cloud.google.com/)
2. Enable the Places SDK for Android

### Steps

1. **Clone the repo:**
   ```bash
   git clone https://github.com/MdAyanHassan/park_smart.git
   ```

2. **Add API Key:**
   Create a `secrets.properties` file in the root directory with:
   ```properties
   PLACES_API_KEY="your_api_key_here"
   ```

3. **Build & Run:**
   - **Via Android Studio:** Open the project, sync Gradle, and click "Run"
   - **Via Command Line:**
     ```bash
     ./gradlew build
     ./gradlew installDebug
     ```  
## 🔌 Firebase Setup (Already Done!)

The repo includes `google-services.json`, so:
- ✅ Firebase Auth
- ✅ Firestore Database
- ✅ Cloud Functions
- ✅ Stripe Payments

...are preconfigured. No extra steps needed!

## ❓ FAQ

**Q: Why do I need a Places API key?**  
A: The app uses Google Maps to fetch nearby parking spots. Without it, location features won't work.

**Q: Can I use my own Firebase project?**  
A: Yes! Replace `google-services.json` with your own (but your Stripe/Firebase configs must match).

## 🤝 Contributing

1. Fork the project
2. Create a branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push (`git push origin feature/AmazingFeature`)
5. Open a Pull Request.

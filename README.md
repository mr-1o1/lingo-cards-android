# LingoCards - Language Learning Flashcard App

A modern Android application built with Jetpack Compose that helps users learn Finnish through interactive flashcards. The app features a beautiful, intuitive interface with dynamic topic management and automatic translation capabilities.

## 🌟 Features

### Core Functionality
- **Topic Management**: Create, edit, and delete custom topics (e.g., Grocery, Casual, Business)
- **Dynamic Flashcards**: Add, edit, and remove flashcards within each topic
- **Automatic Translation**: English text is automatically translated to Finnish using LibreTranslate API
- **Persistent Storage**: All data is stored locally using SQLite database with Room
- **Modern UI**: Built with Material Design 3 and Jetpack Compose

### User Interface
- **Horizontal Scrolling Topics**: Browse through topics with smooth horizontal scrolling
- **Card Layout**: Each flashcard displays English text on top and Finnish translation below, separated by a visual divider
- **Interactive Elements**: Edit and delete buttons on each card and topic
- **Responsive Design**: Adapts to different screen sizes and orientations
- **Loading States**: Visual feedback during data operations and API calls

### Data Management
- **Local Database**: SQLite database with Room for data persistence
- **Real-time Updates**: UI updates automatically when data changes
- **Sample Data**: Pre-loaded with example topics and cards for immediate use
- **Error Handling**: Graceful error handling with user-friendly messages

## 🏗️ Architecture

### Technology Stack
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite abstraction)
- **Networking**: Retrofit with OkHttp
- **Asynchronous Programming**: Kotlin Coroutines and Flow
- **Dependency Injection**: Manual dependency injection

### Project Structure
```
app/src/main/java/com/example/lingocards/
├── data/
│   ├── api/
│   │   ├── ApiClient.kt          # Retrofit configuration
│   │   └── TranslationApi.kt     # Translation API interface
│   ├── dao/
│   │   ├── CardDao.kt            # Database operations for cards
│   │   └── TopicDao.kt           # Database operations for topics
│   ├── entity/
│   │   ├── Card.kt               # Card database entity
│   │   └── Topic.kt              # Topic database entity
│   ├── repository/
│   │   └── LingoRepository.kt    # Data access layer
│   ├── AppDatabase.kt            # Room database configuration
│   └── DatabaseInitializer.kt    # Sample data initialization
├── ui/
│   ├── components/
│   │   ├── CardComponent.kt      # Flashcard UI component
│   │   └── TopicComponent.kt     # Topic selection UI component
│   ├── screen/
│   │   └── MainScreen.kt         # Main application screen
│   └── viewmodel/
│       ├── LingoViewModel.kt     # Main ViewModel
│       └── LingoViewModelFactory.kt # ViewModel factory
├── LingoApplication.kt           # Application class
└── MainActivity.kt               # Main activity
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 35 (API level 35)
- Java 17 or later
- Android device or emulator running API level 24 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd LingoCards
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the LingoCards directory and select it

3. **Sync the project**
   - Android Studio will automatically sync the project
   - Wait for all dependencies to download

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - Select your target device and click "OK"

### Build Configuration

The project uses the following key configurations:

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35
- **Kotlin Version**: 2.0.21
- **Compose BOM**: 2024.09.00

## 📱 Usage Guide

### Adding Topics
1. Tap the "+" button next to "Topics" in the top section
2. Enter a topic name (e.g., "Restaurant", "Travel", "Shopping")
3. Tap "Add" to create the topic

### Managing Topics
- **Select a topic**: Tap on any topic chip to view its cards
- **Edit a topic**: Tap the edit icon (pencil) on a topic chip
- **Delete a topic**: Tap the delete icon (trash) on a topic chip
- **Note**: Deleting a topic will also delete all its associated cards

### Adding Cards
1. Select a topic from the horizontal list
2. Tap the "Add Card" button
3. Enter English text in the dialog
4. Tap "Add" - the app will automatically translate it to Finnish

### Managing Cards
- **View cards**: Cards are displayed in a horizontal scrollable list
- **Edit a card**: Tap the edit icon (pencil) on a card
- **Delete a card**: Tap the delete icon (trash) on a card
- **Card layout**: English text on top, Finnish translation below

### Sample Data
The app comes pre-loaded with three sample topics:
- **Grocery**: Common phrases for shopping
- **Casual**: Everyday conversation phrases
- **Business**: Professional and work-related phrases

## 🔧 Technical Details

### Dependencies

#### Core Android
- `androidx.core:core-ktx` - Kotlin extensions for Android
- `androidx.lifecycle:lifecycle-runtime-ktx` - Lifecycle components
- `androidx.activity:activity-compose` - Compose activity integration

#### UI Components
- `androidx.compose:compose-bom` - Compose Bill of Materials
- `androidx.compose.ui:ui` - Compose UI core
- `androidx.compose.material3:material3` - Material Design 3
- `androidx.compose.ui:ui-tooling` - Compose tooling support

#### Database
- `androidx.room:room-runtime` - Room database
- `androidx.room:room-ktx` - Room Kotlin extensions
- `androidx.room:room-compiler` - Room annotation processor

#### Networking
- `com.squareup.retrofit2:retrofit` - HTTP client
- `com.squareup.retrofit2:converter-gson` - JSON converter
- `com.squareup.okhttp3:okhttp` - HTTP client implementation
- `com.squareup.okhttp3:logging-interceptor` - HTTP logging

#### Asynchronous Programming
- `org.jetbrains.kotlinx:kotlinx-coroutines-android` - Android coroutines
- `org.jetbrains.kotlinx:kotlinx-coroutines-core` - Core coroutines

#### Architecture Components
- `androidx.lifecycle:lifecycle-viewmodel-compose` - ViewModel for Compose
- `androidx.navigation:navigation-compose` - Navigation for Compose

### API Integration

The app uses LibreTranslate (https://libretranslate.de/) for English to Finnish translation:
- **Base URL**: https://libretranslate.de/
- **Endpoint**: POST /translate
- **Request Format**: JSON with source, target, and text parameters
- **Response Format**: JSON with translated text

### Database Schema

#### Topics Table
```sql
CREATE TABLE topics (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    createdAt INTEGER NOT NULL
);
```

#### Cards Table
```sql
CREATE TABLE cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    topicId INTEGER NOT NULL,
    englishText TEXT NOT NULL,
    finnishText TEXT NOT NULL,
    createdAt INTEGER NOT NULL,
    FOREIGN KEY (topicId) REFERENCES topics (id) ON DELETE CASCADE
);
```

## 🎨 UI/UX Design

### Design Principles
- **Material Design 3**: Following Google's latest design guidelines
- **Accessibility**: Proper content descriptions and touch targets
- **Responsive**: Adapts to different screen sizes
- **Intuitive**: Clear visual hierarchy and familiar patterns

### Color Scheme
- Uses Material Design 3 dynamic color system
- Adapts to light/dark themes automatically
- Primary colors for interactive elements
- Error colors for destructive actions

### Layout Structure
1. **Top App Bar**: App title and branding
2. **Topics Section**: Horizontal scrollable topic chips
3. **Cards Section**: Horizontal scrollable flashcards
4. **Action Buttons**: Add, edit, and delete functionality

## 🔒 Permissions

The app requires the following permissions:
- **Internet**: For translation API calls

## 🐛 Troubleshooting

### Common Issues

1. **Translation not working**
   - Check internet connection
   - Verify the LibreTranslate service is available
   - Check app logs for API errors

2. **App crashes on startup**
   - Ensure Java 17 is installed
   - Clean and rebuild the project
   - Check Android Studio logs

3. **Database issues**
   - Clear app data and restart
   - Check Room migration logs
   - Verify database file permissions

### Debug Information

To enable debug logging:
1. Open Android Studio
2. Go to Run → Edit Configurations
3. Add `-Dorg.gradle.debug=true` to VM options

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Write unit tests for business logic
- Test on multiple device configurations

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **LibreTranslate**: Free and open-source translation service
- **Jetpack Compose**: Modern Android UI toolkit
- **Material Design**: Design system by Google
- **Room**: Database abstraction layer
- **Retrofit**: Type-safe HTTP client

## 📞 Support

For support, please open an issue on the GitHub repository or contact the development team.

---

**LingoCards** - Making language learning simple and enjoyable! 🇫🇮 